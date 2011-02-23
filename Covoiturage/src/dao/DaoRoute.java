package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import utilities.Constantes;
import utilities.DateUtils;
import model.Passager;
import model.Position;
import model.Route;
import google_api.GoogleGeoApi;
import google_api.GoogleGeoApiCached;

public class DaoRoute {

	public static ConnexionBD con;

	/**
	 * Insert new journey into the data base, this method will search
	 * automatically the position id in the data base according to the position
	 * string. If the position is not present in the local data base a new one
	 * will be created by calling the Google Geo API to get the necessary
	 * information associated with this position. The departure time and the
	 * arrival time will be converted to the number of <b>seconds</b> since
	 * January 1, 1970, 00:00:00 GMT before inserting into the data base
	 * 
	 * @param address_depart
	 *            : name of the departure position (not null)
	 * @param address_arrive
	 *            : name of the arrival position (not null)
	 * @param date_depart
	 *            : departure time (not null)
	 * @param date_arrive
	 *            : arrival time
	 * @param comt
	 *            : user comment (replaced by '' if null)
	 * @param user_ID
	 *            : user id (not null)
	 * @param seat
	 *            : number of seat available
	 * @param car_ID
	 *            : car id
	 * @return the created {@link model.Route}; null if there is no information
	 *         related to the specified address (depart or arrive)
	 * @throws Exception
	 */
	public static Route createRoute(Integer type, String address_depart,
			String address_arrive, Date date_depart, Date date_arrive,
			String comt, Integer user_ID, Integer seat, Integer car_ID)
			throws Exception {
		Position pos_depart = null;
		Position pos_arrive = null;

		pos_depart = DaoPosition.getPositionByAddress(address_depart);
		if (pos_depart == null) {
			Hashtable<String, Double> result = null;
			result = GoogleGeoApiCached.getCoordOfAddress(address_depart);
			if (result != null) {
				pos_depart = DaoPosition.createPosition(address_depart,
						result.get("latitude"), result.get("longitude"));
			} else {
				return null;
			}
		}
		pos_arrive = DaoPosition.getPositionByAddress(address_arrive);
		if (pos_arrive == null) {
			Hashtable<String, Double> result = null;
			result = GoogleGeoApiCached.getCoordOfAddress(address_arrive);
			if (result != null) {
				pos_arrive = DaoPosition.createPosition(address_arrive,
						result.get("latitude"), result.get("longitude"));
			} else {
				return null;
			}
		}

		return createRoute(type, pos_depart.getId(), pos_arrive.getId(),
				date_depart, date_arrive, comt, user_ID, seat, car_ID);
	}

	/**
	 * Insert new journey into the data base, before calling this method make
	 * sure you have acquired all the necessary information, especially the
	 * <b>depart position id</b>, the <b>arrive position id</b> and the <b>user
	 * id</b>. The departure time and the arrival time will be converted to the
	 * number of seconds since January 1, 1970, 00:00:00 GMT before inserting
	 * into the data base
	 * 
	 * @param pos_depart_ID
	 *            : depart position id (not null)
	 * @param pos_arrive_ID
	 *            : arrive position id (not null)
	 * @param date_depart
	 *            : departure time (not null)
	 * @param date_arrive
	 *            : arrival time
	 * @param comt
	 *            : user comment (replaced by '' if null)
	 * @param user_ID
	 *            : user id (not null)
	 * @param seat
	 *            : number of seat available
	 * @param car_ID
	 *            : car id
	 * @return The created object {@link model.Route}; null if SQL query returns
	 *         no result
	 * @throws Exception
	 */

	public static Route createRoute(Integer type, Integer pos_depart_ID,
			Integer pos_arrive_ID, Date date_depart, Date date_arrive,
			String comt, Integer user_ID, Integer seat, Integer car_ID)
			throws Exception {

		con = null;
		String messageErr = null;
		ResultSet res;
		Route route = null;

		Long date_depart_INT = null;
		Long date_arrive_INT = null;

		// Converting date
		date_depart_INT = date_depart.getTime() / 1000;
		if (date_arrive != null)
			date_arrive_INT = date_arrive.getTime() / 1000;

		Position pos_begin = DaoPosition.getPosition(pos_depart_ID);
		Position pos_end = DaoPosition.getPosition(pos_arrive_ID);
		
		Hashtable<Integer, Hashtable<Integer, Double>> waypoints = new Hashtable<Integer, Hashtable<Integer, Double>>();
		
		
		// Inserting
		try {
			con = ConnexionBD.getConnexion();

			String query = "call route_create(" + type + ", " + pos_depart_ID
					+ ", " + pos_arrive_ID + ", " + date_depart_INT + ", "
					+ (date_arrive_INT == null ? "NULL" : date_arrive_INT)
					+ ", '" + (comt == null ? "" : comt) + "', " + user_ID
					+ ", " + (seat == null ? "NULL" : seat) + ", "
					+ (car_ID == null || car_ID == 0 ? "NULL" : car_ID) + ")";

			try {
				res = con.execute(query);
				if (res.first())
					route = new Route(res);
				
				Boolean insertSegments = true;
				if(insertSegments) {
					
					System.out.println("Recuperation des segments");
					ArrayList<Hashtable<String, Object>> directionResult = GoogleGeoApi
							.getDirection(pos_begin.getCoords(), pos_end.getCoords(),
									"driving", waypoints);
					System.out.println("Fin Recuperation des segments");
					
					// translate(Ajout des segments composants le trajet)
					int directionResultSize = directionResult.size();
					int directionResultI;
					int segmentCounter=0;
					int global_duration_increment=0;
					System.out.println("Debut ajout des segments");
					for (directionResultI = 0; directionResultI < directionResultSize; directionResultI++) {
						// Character value = (Character)itValue.next();
	
						Hashtable<String, Object> step = (Hashtable<String, Object>) directionResult
								.get(directionResultI);
						
						ArrayList<Hashtable<String, Object>> segments = (ArrayList<Hashtable<String, Object>>) step
								.get("segments");
						
						
						int subdurationIncrement=0;
						for (int i2 = 0; i2 < segments.size(); i2++) {
							Hashtable<String, Double> sub_pos_begin = (Hashtable<String, Double>) segments.get(i2).get("begin");
							Hashtable<String, Double> sub_pos_end = (Hashtable<String, Double>) segments.get(i2).get("end");
							Integer subduration = (int) Math.round(((Double) segments.get(i2).get("duration")));
							
							Position sub_ppos_begin = DaoPosition.createPosition(null, sub_pos_begin.get("latitude"), sub_pos_begin.get("longitude"));
							Position sub_ppos_end = DaoPosition.createPosition(null, sub_pos_end.get("latitude"), sub_pos_end.get("longitude"));
							
							
							Date sub_date_begin = DateUtils.getTimestampAsDate(DateUtils.getDateAsInteger(route.getDate_begin()) + subduration + global_duration_increment + subdurationIncrement);
							
							DaoSegment.createSegment(route, sub_ppos_begin, sub_ppos_end, subduration, sub_date_begin, segmentCounter);
							
							subdurationIncrement+=subduration;
							segmentCounter++;
						}
						global_duration_increment+=(Double)step.get("duration");
	
					}
					System.out.println("Fin ajout de  "+segmentCounter+" segments");
				}

			} catch (MySQLIntegrityConstraintViolationException ex) {
				// ? Errors ?
				messageErr = Constantes.UNEXPECTED_ERROR;
				System.err.println(messageErr + " : " + ex);
				throw new Exception(messageErr);
			}

		} catch (ClassNotFoundException ex) {
			messageErr = Constantes.CLASS_DB_NOT_FOUND;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		} catch (SQLException ex) {
			messageErr = Constantes.PROBLEME_CONNECTION_DB;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		}

		return route;
	}

	public static Route getRoute(int rte_id) {
		Route rte = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM route_rte WHERE rte_id= " + rte_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				rte = new Route(curseur);
				return rte;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static Route getRoute(Route rte) {
		return DaoRoute.getRoute(rte.getId());
	}

	public static Hashtable<Integer, Passager> getPassagers(int rte_id) {
		return DaoPassager.getPassagers(rte_id);
	}

	public static Hashtable<Integer, Passager> getPassagersOfType(int rte_id,
			int pgt_id) {
		return DaoPassager.getPassagersOfType(rte_id, pgt_id);
	}

	public static void route_add_passager(int rte_id, int passager_user_id)
			throws Exception {
		DaoPassager.route_add_passager(rte_id, passager_user_id);
	}

	/**
	 * 
	 * @param pos_begin
	 * @param pos_end
	 * @param date_departure_begin
	 * @param date_departure_end
	 * @param location_appro
	 * @param rtp_id
	 *            appartient à (0,1,2), 0 <=> indifferent
	 * @return Les routes correspondantes aux criteres
	 */
	public static Hashtable<Integer, Route> route_search(Position pos_begin,
			Position pos_end, Date date_departure_begin,
			Date date_departure_end, Integer location_appro, int rtp_id) {
		Hashtable<Integer, Route> list = new Hashtable<Integer, Route>();
		Route rte = null;

		try {
			con = ConnexionBD.getConnexion();
			String query = "call route_search_with_date_and_delta("
					+ pos_begin.getId() + ", " + pos_end.getId() + ", "
					+ DateUtils.getDateAsInteger(date_departure_begin) + ", "
					+ DateUtils.getDateAsInteger(date_departure_end) + ", "
					+ location_appro + ", " + rtp_id + ")";
			ResultSet curseur = con.execute(query);
			while (curseur.next()) {
				rte = new Route(curseur);
				list.put(rte.getId(), rte);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param usr_id
	 * @param date_departure_begin
	 * @param date_departure_end
	 * @param rtp_id
	 *            appartient à (0,1,2), 0 <=> indifferent
	 * @return
	 */
	public static Hashtable<Integer, Route> route_search_of_owner(int usr_id,
			Date date_departure_begin, Date date_departure_end, int rtp_id) {
		Hashtable<Integer, Route> list = new Hashtable<Integer, Route>();
		Route rte = null;

		try {
			con = ConnexionBD.getConnexion();

			String query = "call route_search_of_owner(" + usr_id + ", "
					+ DateUtils.getDateAsInteger(date_departure_begin) + ", "
					+ DateUtils.getDateAsInteger(date_departure_end) + rtp_id
					+ ")";
			ResultSet curseur = con.execute(query);
			while (curseur.next()) {
				rte = new Route(curseur);
				list.put(rte.getId(), rte);
			}
		} catch (Exception e) {
		}
		return list;
	}

}
