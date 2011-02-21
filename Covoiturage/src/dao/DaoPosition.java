package dao;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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
		ResultSet res;
		Position pos = null;

		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);
			if ((latitude > 180 || latitude < -180)
					|| (longitude > 90 || longitude < -90)) {
				messageErr = Constantes.DATA_FORM_NOT_CORRECT;
				System.err.println(messageErr + " : Latitude(" + latitude
						+ ");Longitude(" + longitude + ")");
				throw new Exception(messageErr);
			}
			String query = "call position_create_or_update('" + address + "', "
					+ latitude + ", " + longitude + ")";
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

		if (con != null)
			con.close();

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
		// TODO: Errors in SQL procedure sims.v2.procedure.position
		con = null;
		String messageErr = null;
		ResultSet res;
		Position pos = null;

		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);
			String query = "call position_get_by_address('" + address + "')";
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

		if (con != null)
			con.close();

		return pos;

	}
	


	public static Position getPosition(int pos_id) {
		Position pos = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

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
			return null;
		}
	}

	public static Position getPosition(Position pos) {
		return DaoPosition.getPosition(pos.getId());
	}
	
}
