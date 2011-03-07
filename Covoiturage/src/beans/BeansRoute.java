package beans;

import google_api.GoogleGeoApiCached;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import dao.DaoRoute;
import dao.DaoPosition;

import utilities.Constantes;
import utilities.FacesUtil;

import model.Passager;
import model.Position;
import model.Route;
import model.User;
import model.Route_type;

public class BeansRoute {

	protected Route route = new Route();

	protected String pos_depart = null;
	protected String pos_depart_other = null;
	protected String pos_depart_coords_lat = null;
	protected String pos_depart_coords_lng = null;
	
	
	protected String pos_arrive = null;
	protected String pos_arrive_other = null;
	protected String pos_arrive_coords_lat = null;
	protected String pos_arrive_coords_lng = null;
	
	
	protected Integer route_type = null;
	protected Integer minutes_to_depart = null;
	protected Integer seat_number = null;
	protected Integer time_delta = null;
	private Integer distance_radius = null;
	
	
	
	private List<Route> route_list = null;
	protected String messageErr;

	public Integer getSeat_number() {
		return seat_number;
	}

	public void setSeat_number(Integer seat_number) {
		this.seat_number = seat_number;
	}

	public Integer getRoute_type() {
		return route_type;
	}

	public void setRoute_type(Integer route_type) {
		this.route_type = route_type;
	}

	public String getPos_depart() {
		return pos_depart;
	}

	public void setPos_depart(String pos_depart) {
		this.pos_depart = pos_depart;
	}

	public String getPos_arrive() {
		return pos_arrive;
	}

	public void setPos_arrive(String pos_arrive) {
		this.pos_arrive = pos_arrive;
	}

	public Integer getMinutes_to_depart() {
		return minutes_to_depart;
	}

	public void setMinutes_to_depart(Integer minutes_to_depart) {
		this.minutes_to_depart = minutes_to_depart;
	}

	/**
	 * @return
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * @param route
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * @return the error message
	 */
	public String getMessageErr() {
		return messageErr;
	}

	/**
	 * @param messageErr
	 */
	public void setMessageErr(String messageErr) {
		this.messageErr = messageErr;
	}

	/**
	 * Create new route
	 */
	public String createRoute() {
		Route createdRoute = null;
		route_type = Route_type.PROVIDE_CAR;
		if (minutes_to_depart == null 
				|| pos_depart == null
				|| pos_arrive == null 
				|| route_type == null) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
		}
		
		if (route_type == 2){
			if (seat_number <= 0){
				messageErr = "Seat number must be greater than 0";
				return "actuel";
			}
		}else{
			seat_number = null;
		}

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((c.getTimeInMillis() / 1000 + minutes_to_depart * 60) * 1000);
		Date full_date_depart = c.getTime();

		User currentUser = FacesUtil.getUser();
		
		try {
			
			Position posBegin = null;
			Position posEnd = null;
			
			posBegin = this.getSelectedPositionBegin();
			posEnd = this.getSelectedPositionEnd();
			
			
			
			route = DaoRoute.createRoute(Route_type.PROVIDE_CAR, posBegin.getId(), posEnd.getId(),
					full_date_depart, null, null, currentUser.getId(), seat_number, null);
			if (route != null){
				route.setPassagers(new Hashtable<Integer, Passager>());
				System.out.println("new route created");
				return "show";
			}else{
				this.messageErr = Constantes.UNEXPECTED_ERROR;
				return "actuel";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.messageErr = e.getMessage();
			return "actuel";
		}
	}
	
	protected Position getSelectedPositionBegin() throws Exception {
		Position posBegin=null;
		if(pos_depart.equals("here")) {
			Double lat = Double.valueOf(pos_depart_coords_lat);
			Double lng = Double.valueOf(pos_depart_coords_lng);
			if(lat == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			if(lng == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			Hashtable<String, Double> coords = new Hashtable<String, Double>();
			coords.put("latitude", lat);
			coords.put("longitude", lng);
			String address = GoogleGeoApiCached.getNearAddressFromCoord(coords);
			posBegin = DaoPosition.createPosition(address, lat, lng);
		}else if(pos_depart.equals("other")) {
			Hashtable<String, Double> coords;
			coords = GoogleGeoApiCached.getCoordOfAddress(pos_depart_other);
			posBegin = DaoPosition.createPosition(pos_depart_other, coords.get("latitude"), coords.get("longitude"));
		}else{
			//TODO UPDATE
			posBegin = DaoPosition.getPosition(1);
		}
		return posBegin;
	}
	
	protected Position getSelectedPositionEnd() throws Exception {
		Position posEnd=null;
		
		if(pos_arrive.equals("here")) {
			Double lat = Double.valueOf(pos_arrive_coords_lat);
			Double lng = Double.valueOf(pos_arrive_coords_lng);
			if(lat == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			if(lng == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			Hashtable<String, Double> coords = new Hashtable<String, Double>();
			coords.put("latitude", lat);
			coords.put("longitude", lng);
			String address = GoogleGeoApiCached.getNearAddressFromCoord(coords);
			posEnd = DaoPosition.createPosition(address, lat, lng);
		}else if(pos_arrive.equals("other")) {
			Hashtable<String, Double> coords;
			coords = GoogleGeoApiCached.getCoordOfAddress(pos_arrive_other);
			posEnd = DaoPosition.createPosition(pos_arrive_other, coords.get("latitude"), coords.get("longitude"));
		}else{
			//TODO UPDATE
			posEnd = DaoPosition.getPosition(1);
		}
		
		return posEnd;
	}

	public List<Passager> getAllPassagers() {
		return null;

	}

	public String search() throws Exception {
		if (minutes_to_depart == null || pos_depart == null
				|| pos_arrive == null || time_delta == null
				|| distance_radius == null) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
		}
		
		/*
		Position from = DaoPosition.getPositionByAddress(pos_depart);
		Position to = DaoPosition.getPositionByAddress(pos_arrive);
		*/
		
		Position posBegin = null;
		Position posEnd = null;
		
		posBegin = this.getSelectedPositionBegin();
		posEnd = this.getSelectedPositionEnd();
		

		Date date_departure_begin = new Date();
		date_departure_begin.setTime(date_departure_begin.getTime() + (minutes_to_depart - time_delta)*60*1000);
		
		Date date_departure_end = new Date();
		date_departure_end.setTime(date_departure_end.getTime() + (minutes_to_depart + time_delta)*60*1000);

		Hashtable<Integer, Route> table = DaoRoute.route_search(posBegin, posEnd, date_departure_begin, date_departure_end,
				distance_radius, Route_type.PROVIDE_CAR);
		route_list = new ArrayList<Route>(table.values());
		return "index";
	}

	public void setDistance_radius(Integer distance_radius) {
		this.distance_radius = distance_radius;
	}

	public Integer getDistance_radius() {
		return distance_radius;
	}

	public void setTime_delta(Integer time_delta) {
		this.time_delta = time_delta;
	}

	public Integer getTime_delta() {
		return time_delta;
	}

	public Collection<Route> getRoute_list() {
		return route_list;
	}

	public String getPos_depart_other() {
		return pos_depart_other;
	}

	public void setPos_depart_other(String pos_depart_other) {
		this.pos_depart_other = pos_depart_other;
	}

	public String getPos_depart_coords_lat() {
		return pos_depart_coords_lat;
	}

	public void setPos_depart_coords_lat(String pos_depart_coords_lat) {
		this.pos_depart_coords_lat = pos_depart_coords_lat;
	}

	public String getPos_depart_coords_lng() {
		return pos_depart_coords_lng;
	}

	public void setPos_depart_coords_lng(String pos_depart_coords_lng) {
		this.pos_depart_coords_lng = pos_depart_coords_lng;
	}

	public String getPos_arrive_other() {
		return pos_arrive_other;
	}

	public void setPos_arrive_other(String pos_arrive_other) {
		this.pos_arrive_other = pos_arrive_other;
	}

	public String getPos_arrive_coords_lat() {
		return pos_arrive_coords_lat;
	}

	public void setPos_arrive_coords_lat(String pos_arrive_coords_lat) {
		this.pos_arrive_coords_lat = pos_arrive_coords_lat;
	}

	public String getPos_arrive_coords_lng() {
		return pos_arrive_coords_lng;
	}

	public void setPos_arrive_coords_lng(String pos_arrive_coords_lng) {
		this.pos_arrive_coords_lng = pos_arrive_coords_lng;
	}

	public void setRoute_list(List<Route> route_list) {
		this.route_list = route_list;
	}
	
}
