package beans;

import google_api.GoogleGeoApiCached;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import dao.DaoRoute;
import dao.DaoPosition;
import dao.DaoUser_fav_position;

import utilities.Constantes;
import utilities.DateUtils;
import utilities.FacesUtil;

import model.Passager;
import model.Position;
import model.Route;
import model.Segment;
import model.User;
import model.Route_type;
import model.User_fav_position;

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
	private String distance_radius = null;

	protected Boolean is_my_route = false;
	protected Boolean is_created_route = false;
	protected ArrayList<User_fav_position> user_fav_pos = null;
	private List<Route> route_list = new ArrayList<Route>();
	protected String messageErr;

	private String parameter;

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

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
	
	String depart_date_choice=null;
	
	public String getDepart_date_choice() {
		return this.depart_date_choice;
	}
	
	public void setDepart_date_choice(String ch) {
		this.depart_date_choice=ch;
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
		if (parameter != null && parameter.length() != 0) {
			int rte_id = Integer.parseInt(parameter);
			route = DaoRoute.getRoute(rte_id);
			parameter = null;
		}

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
		// Route createdRoute = null;
		route_type = Route_type.PROVIDE_CAR;
		if (minutes_to_depart == null || pos_depart == null
				|| pos_arrive == null || route_type == null) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
		}

		if (route_type == 2) {
			if (seat_number <= 0) {
				messageErr = "Seat number must be greater than 0";
				return "actuel";
			}
		} else {
			seat_number = null;
		}
		
		Date full_date_depart = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) + minutes_to_depart * 60);

		User currentUser = FacesUtil.getUser();
		
		if(currentUser == null) return "home";
		
		try {
			
			Position posBegin = null;
			Position posEnd = null;
			
			posBegin = this.getSelectedPositionBegin();
			posEnd = this.getSelectedPositionEnd();
			
			
			
			route = DaoRoute.createRoute(Route_type.PROVIDE_CAR, posBegin.getId(), posEnd.getId(),
					full_date_depart, null, null, currentUser.getId(), seat_number, null);
			if (route != null){
				is_created_route = true;
				route.setPassagers(new Hashtable<Integer, Passager>());
				System.out.println("new route created");
				this.resetAllFields();
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
			if( coords == null ) {
				throw new Exception(Constantes.INVALID_ADDRESS);
			}
			posBegin = DaoPosition.createPosition(pos_depart_other, coords.get("latitude"), coords.get("longitude"));
		}else{
			Integer ufp_id = Integer.valueOf(pos_depart);
			User_fav_position ufp = DaoUser_fav_position.getUser_fav_position(ufp_id);
			if( ufp == null ) {
				throw new Exception(Constantes.INVALID_ADDRESS);
			}
			posBegin = ufp.getPositionObj();
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
			if( coords == null ) {
				throw new Exception(Constantes.INVALID_ADDRESS);
			}
			posEnd = DaoPosition.createPosition(pos_arrive_other, coords.get("latitude"), coords.get("longitude"));
		}else{
			Integer ufp_id = Integer.valueOf(pos_arrive);
			User_fav_position ufp = DaoUser_fav_position.getUser_fav_position(ufp_id);
			if( ufp == null ) {
				throw new Exception(Constantes.INVALID_ADDRESS);
			}
			posEnd = ufp.getPositionObj();
		}
		
		return posEnd;
	}

	public List<Passager> getAllPassagers() {
		return null;

	}

	public String search() throws Exception {
		if (pos_depart == null
				|| pos_arrive == null 
				|| depart_date_choice == null
				|| distance_radius == null
				|| distance_radius == null
				//|| time_delta == null
				//|| minutes_to_depart == null 
				
				) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
		}
		
		/*
		Position from = DaoPosition.getPositionByAddress(pos_depart);
		Position to = DaoPosition.getPositionByAddress(pos_arrive);
		*/
		
		Position posBegin = null;
		Position posEnd = null;
		
		try {
			posBegin = this.getSelectedPositionBegin();
			posEnd = this.getSelectedPositionEnd();
		} catch(Exception e) {
			messageErr = e.getMessage();
			return "actuel";
		}
		
		Integer precision_meters=1000;
		
		Double distance = posBegin.getDistanceInMeterWith(posEnd);
		
		if(distance_radius.equals("exact")) {
			precision_meters = (int) Math.round(Math.min(30000, Math.max(500, distance / 400 )));
		}else if(distance_radius.equals("high")) {
			precision_meters = (int) Math.round(Math.min(50000, Math.max(1000, distance / 200 )));
		}else if(distance_radius.equals("medium")) {
			precision_meters = (int) Math.round(Math.min(80000, Math.max(1500, distance / 20 )));
		}else if(distance_radius.equals("low")) {
			precision_meters = (int) Math.round(Math.min(100000, Math.max(2000, distance / 5 )));
		}
		/*
		Date date_departure_begin = new Date();
		date_departure_begin.setTime(date_departure_begin.getTime() + (minutes_to_depart - time_delta)*60*1000);
		
		Date date_departure_end = new Date();
		date_departure_end.setTime(date_departure_end.getTime() + (minutes_to_depart + time_delta)*60*1000);
		*/
		
		Date date_departure_begin = new Date();
		Date date_departure_end = new Date();
		
		if(depart_date_choice.equals("5min")) {
			date_departure_begin = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) - 5*60);
			date_departure_end = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) + 10*60);
		}else if(depart_date_choice.equals("thisHour")) {
			date_departure_begin = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) - 5*60);
			date_departure_end = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) + 70*60);
		}else if(depart_date_choice.equals("today")) {
			date_departure_begin = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) - 10*60);
			date_departure_end = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) + 24*60*60);
		}else if(depart_date_choice.equals("tomorow")) {
			date_departure_begin = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) - 5*60);
			date_departure_end = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) + 48*60*60);
		}else if(depart_date_choice.equals("aftertomorow")) {
			date_departure_begin = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) - 5*60);
			date_departure_end = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(new Date()) + 76*60*60);
		}
		
		Hashtable<Integer, Route> table = DaoRoute.route_search(posBegin, posEnd, date_departure_begin, date_departure_end,
				precision_meters, Route_type.PROVIDE_CAR);
		route_list.clear();
		route_list.addAll(table.values());
		
		this.resetAllFields();
		return "index";
	}

	public void setDistance_radius(String distance_radius) {
		this.distance_radius = distance_radius;
	}

	public String getDistance_radius() {
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
	
	public ArrayList<Position> getSegments() {
		ArrayList<Position> retour = new ArrayList<Position>();
		ArrayList<Segment> input = DaoRoute.getSegments(this.route);
		System.out.println("Numberelementssss : "+input.size());
		int i=0;
		int n=input.size();
		while(i < n) {
			Segment seg = input.get(i); 
			retour.add(seg.getPos_beginObj());
			retour.add(seg.getPos_endObj());
			i++;
		}
		return retour;
	}

	public Boolean getIs_my_route() {
		return route.getOwner() == FacesUtil.getUser().getId();
	}

	public void setIs_my_route(Boolean isMyRoute) {
		this.is_my_route = isMyRoute;
	}

	public Boolean getIs_created_route() {
		return is_created_route;
	}

	public void setIs_created_route(Boolean is_created_route) {
		this.is_created_route = is_created_route;
	}

	public ArrayList<User_fav_position> getUser_fav_pos() {
		Hashtable<Integer, User_fav_position> input = DaoUser_fav_position.getFavoritePositionsOfUser(FacesUtil.getUser());
		ArrayList<User_fav_position> retour = new ArrayList<User_fav_position>();
		retour.addAll(input.values());
		System.out.println(retour);
		return retour;
	}
	
	public Collection<SelectItem> getUser_fav_pos_select_items() {
		ArrayList<SelectItem> retour = new ArrayList<SelectItem>();
		Collection<User_fav_position> ufp_list = getUser_fav_pos();
		Iterator<User_fav_position> it = ufp_list.iterator();
		User_fav_position ufp = null;
		while (it.hasNext()) {
			ufp = it.next();
			retour.add(new SelectItem(ufp.getId(), ufp.getLabel()));
		}
		return retour;
	}
	
	public String toHome(){
		messageErr = "";
		System.out.println("toHome");
		this.resetAllFields();
		return "home";
	}
	
	public void resetAllFields() {
		//this.Route route = new Route();

		this.pos_depart = null;
		this.pos_depart_other = null;
		this.pos_depart_coords_lat = null;
		this.pos_depart_coords_lng = null;

		this.pos_arrive = null;
		this.pos_arrive_other = null;
		this.pos_arrive_coords_lat = null;
		this.pos_arrive_coords_lng = null;

		this.route_type = null;
		this.minutes_to_depart = null;
		this.seat_number = null;
		this.time_delta = null;
		this.distance_radius = null;

		this.is_my_route = false;
		this.is_created_route = false;
		this.user_fav_pos = null;
		this.route_list = new ArrayList<Route>();
		
		//this.messageErr;

		this.parameter="";;
	}
}
