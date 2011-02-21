package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import utilities.Constantes;
import model.Passager;
import model.Position;
import model.Route;
import model.User;
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
	 *            : name of the departure position
	 * @param address_arrive
	 *            : name of the arrival position
	 * @param date_depart
	 *            : departure time
	 * @param date_arrive
	 *            : arrival time
	 * @param comt
	 *            : user comment
	 * @param user_ID
	 *            : user id
	 * @param seat
	 *            : number of seat available
	 * @param car_ID
	 *            : car id
	 * @return the created {@link model.Route}; null if there is no information
	 *         related to the specified address (depart or arrive)
	 * @throws Exception
	 */
	public static Route createRoute(int type, String address_depart,
			String address_arrive, Date date_depart, Date date_arrive,
			String comt, int user_ID, int seat, int car_ID) throws Exception {
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
	 *            : depart position id
	 * @param pos_arrive_ID
	 *            : arrive position id
	 * @param date_depart
	 *            : departure time
	 * @param date_arrive
	 *            : arrival time
	 * @param comt
	 *            : user comment
	 * @param user_ID
	 *            : user id
	 * @param seat
	 *            : number of seat available
	 * @param car_ID
	 *            : car id
	 * @return The created object {@link model.Route}; null if SQL query returns
	 *         no result
	 * @throws Exception
	 */
	public static Route createRoute(int type, int pos_depart_ID,
			int pos_arrive_ID, Date date_depart, Date date_arrive, String comt,
			int user_ID, int seat, int car_ID) throws Exception {

		con = null;
		String messageErr = null;
		ResultSet res;
		Route route = null;

		long date_depart_INT = 0;
		long date_arrive_INT = 0;

		// Converting date
		date_arrive_INT = date_arrive.getTime() / 1000;
		date_depart_INT = date_depart.getTime() / 1000;

		// Inserting
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call route_create(" + type + ", " + pos_depart_ID
					+ ", " + pos_arrive_ID + ", " + date_depart_INT + ", "
					+ date_arrive_INT + ", '" + comt + "', " + user_ID + ", "
					+ seat + ", ";

			// Car id patch
			if (car_ID == 0) {
				query += "NULL)";
			} else {
				query += car_ID + ")";
			}

			try {
				res = con.execute(query);
				if (res.first())
					route = new Route(res);
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

		if (con != null)
			con.close();
		return route;
	}
	
	

	public static Route getRoute(int rte_id) {
		Route rte = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

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
		Hashtable<Integer, Passager> list = new Hashtable<Integer, Passager>();
		Passager psg = null;
		User usr = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call route_get_passagers("+rte_id+")";
			ResultSet curseur = con.execute(query);
			
			while(curseur.next()) {
				psg = new Passager(curseur);
				usr = new User(curseur);
				psg.setUserObj(usr);
				list.put(psg.getId(), psg);
			}
		} catch (Exception e) {
			return list;
		}
		return list;
	}
	
	public static void route_add_passager(int rte_id, int passager_user_id) throws Exception {
		Hashtable<Integer, Passager> current_list = new Hashtable<Integer, Passager>();
		Passager psg = null;
		
		current_list = DaoRoute.getPassagers(rte_id);
		Enumeration<Passager> en = current_list.elements();
		while ( en.hasMoreElements()) {
			psg = (Passager)en.nextElement();
			if(psg.getUser() == passager_user_id) {
				//is already a passager of this route
				throw new Exception(Constantes.ROUTE_ALREADY_PASSSAGER);
			}
		}
		
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call route_join("+rte_id+", "+passager_user_id+")";
			@SuppressWarnings("unused")
			ResultSet curseur = con.execute(query);
			
		} catch (Exception e) {
		}
	}
	
}
