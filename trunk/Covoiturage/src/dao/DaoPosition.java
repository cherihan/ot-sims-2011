package dao;

import google_api.GoogleGeoApiCached;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;


import utilities.Constantes;

import model.Position;

public class DaoPosition {

	public static ConnexionBD con;

	/**
	 * Insert a new position into the data base
	 * 
	 * @param address
	 * @param latitude
	 * @param longitude
	 * @return the created {@link model.Position}; null if SQL query returns no
	 *         result
	 * @throws Exception
	 */
	public static Position createPosition(String address, Double latitude,
			Double longitude) throws Exception {

		con = null;
		String messageErr = null;
		Position pos = null;

		try {
			con = ConnexionBD.getConnexion();
			if ((latitude > 180 || latitude < -180)
					|| (longitude > 90 || longitude < -90)) {
				messageErr = Constantes.DATA_FORM_NOT_CORRECT;
				System.err.println(messageErr + " : Latitude(" + latitude
						+ ");Longitude(" + longitude + ")");
				throw new Exception(messageErr);
			}
			String query = "call position_create_or_update(" + (address == null || address.equals("") ? "NULL" : "'"+address+"'") + ", "
					+ latitude + ", " + longitude + ")";
			ResultSet res = con.execute(query);
			if (res.first())
				pos = new Position(res);
			//pos = getPositionByAddress(address);

		} catch (ClassNotFoundException ex) {
			messageErr = Constantes.CLASS_DB_NOT_FOUND;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		} catch (SQLException ex) {
			messageErr = Constantes.PROBLEME_CONNECTION_DB;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		}

		return pos;

	}

	/**
	 * Get the position related to the given address
	 * 
	 * @param address
	 * @return the {@link model.Position}; null if there is no position
	 *         information related to the given address
	 * @throws Exception
	 */
	public static Position getPositionByAddress(String address)
			throws Exception {
		con = null;
		String messageErr = null;
		ResultSet res;
		Position pos = null;

		try {
			con = ConnexionBD.getConnexion();
			String query = "call position_get_by_address('" + (address == null || address.equals("") ? "NULL" : address) + "')";
			res = con.execute(query);
			if (res.first())
				pos = new Position(res);

		} catch (ClassNotFoundException ex) {
			messageErr = Constantes.CLASS_DB_NOT_FOUND;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		} catch (SQLException ex) {
			messageErr = Constantes.PROBLEME_CONNECTION_DB;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		}
		if(pos == null) {
			Hashtable<String, Double> gresult = GoogleGeoApiCached.getCoordOfAddress(address);
			pos = DaoPosition.createPosition(address, gresult.get("latitude"), gresult.get("longitude"));
		}

		return pos;

	}
	/*
	public static Position getPositionByCoords(Double latitude, Double longitude)
			throws Exception {
		con = null;
		String messageErr = null;
		ResultSet res;
		Position pos = null;

		try {
			con = ConnexionBD.getConnexion();
			String query = "call position_get_by_lat_lng(" + latitude + ", "+longitude+")";
			try {
				res = con.execute(query);
				if (res.first())
					pos = new Position(res);
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

		return pos;

	}
	*/

	public static Position getPosition(int pos_id) {
		Position pos = null;
		try {
			
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM position_pos WHERE pos_id= " + pos_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				pos = new Position(curseur);
				return pos;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Position getPosition(Position pos) {
		// Quelqu'un peut m'expliquer l'intérêt de cette fonction ? -V
		return DaoPosition.getPosition(pos.getId());
	}

}
