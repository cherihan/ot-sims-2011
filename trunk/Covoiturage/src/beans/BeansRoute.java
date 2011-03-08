package beans;

import google_api.GoogleGeoApiCached;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import dao.DaoRoute;
import dao.DaoPosition;
import dao.DaoUser_fav_position;

import utilities.Constantes;
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
	private Integer distance_radius = null;

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

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((c.getTimeInMillis() / 1000 + minutes_to_depart * 60) * 1000);
		Date full_date_depart = c.getTime();

		User currentUser = FacesUtil.getUser();

		try {

			Position posBegin = null;
			Position posEnd = null;

			posBegin = this.getSelectedPositionBegin();
			posEnd = this.getSelectedPositionEnd();

			route = DaoRoute.createRoute(Route_type.PROVIDE_CAR,
					posBegin.getId(), posEnd.getId(), full_date_depart, null,
					null, currentUser.getId(), seat_number, null);
			if (route != null) {
				is_created_route = true;
				route.setPassagers(new Hashtable<Integer, Passager>());
				System.out.println("new route created");
				return "show";
			} else {
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
		Position posBegin = null;
		if (pos_depart.equals("here")) {
			Double lat = Double.valueOf(pos_depart_coords_lat);
			Double lng = Double.valueOf(pos_depart_coords_lng);
			if (lat == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			if (lng == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			Hashtable<String, Double> coords = new Hashtable<String, Double>();
			coords.put("latitude", lat);
			coords.put("longitude", lng);
			String address = GoogleGeoApiCached.getNearAddressFromCoord(coords);
			posBegin = DaoPosition.createPosition(address, lat, lng);
		} else if (pos_depart.equals("other")) {
			Hashtable<String, Double> coords;
			coords = GoogleGeoApiCached.getCoordOfAddress(pos_depart_other);
			posBegin = DaoPosition.createPosition(pos_depart_other,
					coords.get("latitude"), coords.get("longitude"));
		} else {
			Integer ufp_id = Integer.valueOf(pos_depart);
			User_fav_position ufp = DaoUser_fav_position
					.getUser_fav_position(ufp_id);
			posBegin = ufp.getPositionObj();
		}
		return posBegin;
	}

	protected Position getSelectedPositionEnd() throws Exception {
		Position posEnd = null;

		if (pos_arrive.equals("here")) {
			Double lat = Double.valueOf(pos_arrive_coords_lat);
			Double lng = Double.valueOf(pos_arrive_coords_lng);
			if (lat == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			if (lng == 0)
				throw new Exception(Constantes.DATAS_NOT_FILL_IN);
			Hashtable<String, Double> coords = new Hashtable<String, Double>();
			coords.put("latitude", lat);
			coords.put("longitude", lng);
			String address = GoogleGeoApiCached.getNearAddressFromCoord(coords);
			posEnd = DaoPosition.createPosition(address, lat, lng);
		} else if (pos_arrive.equals("other")) {
			Hashtable<String, Double> coords;
			coords = GoogleGeoApiCached.getCoordOfAddress(pos_arrive_other);
			posEnd = DaoPosition.createPosition(pos_arrive_other,
					coords.get("latitude"), coords.get("longitude"));
		} else {
			Integer ufp_id = Integer.valueOf(pos_arrive);
			User_fav_position ufp = DaoUser_fav_position
					.getUser_fav_position(ufp_id);
			posEnd = ufp.getPositionObj();
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
		 * Position from = DaoPosition.getPositionByAddress(pos_depart);
		 * Position to = DaoPosition.getPositionByAddress(pos_arrive);
		 */

		Position posBegin = null;
		Position posEnd = null;

		posBegin = this.getSelectedPositionBegin();
		posEnd = this.getSelectedPositionEnd();

		Date date_departure_begin = new Date();
		date_departure_begin.setTime(date_departure_begin.getTime()
				+ (minutes_to_depart - time_delta) * 60 * 1000);

		Date date_departure_end = new Date();
		date_departure_end.setTime(date_departure_end.getTime()
				+ (minutes_to_depart + time_delta) * 60 * 1000);

		Hashtable<Integer, Route> table = DaoRoute.route_search(posBegin,
				posEnd, date_departure_begin, date_departure_end,
				distance_radius, Route_type.PROVIDE_CAR);
		route_list.clear();
		route_list.addAll(table.values());
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

	public ArrayList<Position> getSegments() {
		ArrayList<Position> retour = new ArrayList<Position>();
		ArrayList<Segment> input = DaoRoute.getSegments(this.route);
		System.out.println("Numberelementssss : " + input.size());
		int i = 0;
		int n = input.size();
		while (i < n) {
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
		ArrayList<User_fav_position> retour = new ArrayList<User_fav_position>();
		Hashtable<Integer, User_fav_position> input = DaoUser_fav_position
				.getFavoritePositionsOfUser(FacesUtil.getUser());
		Collection<User_fav_position> inpc = input.values();
		Iterator<User_fav_position> it = inpc.iterator();
		while (it.hasNext()) {
			User_fav_position ufp = it.next();
			retour.add(ufp);
		}
		return retour;
	}

	public void setUser_fav_pos(ArrayList<User_fav_position> user_fav_pos) {
		this.user_fav_pos = user_fav_pos;
	}

}
