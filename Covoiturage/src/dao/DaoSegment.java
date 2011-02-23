package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


import utilities.Constantes;
import utilities.DateUtils;
import model.Position;
import model.Route;
import model.Segment;

public class DaoSegment {

	public static ConnexionBD con;

	public static void createSegment(Route rte, Position pos_begin, Position pos_end, int duration, Date date_begin, int order)
			throws Exception {

		con = null;
		String messageErr = null;
		@SuppressWarnings("unused")
		ResultSet res;
		try {

			con = ConnexionBD.getConnexion();

			String query;

			query = "call route_add_segment(" + rte.getId() + ", "
					+ pos_begin.getId() + ", " + pos_end.getId() + ", "+duration+", "+DateUtils.getDateAsInteger(date_begin)+", "+order+")";
			res = con.execute(query);
			
		} catch (ClassNotFoundException ex) {
			messageErr = Constantes.CLASS_DB_NOT_FOUND;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		} catch (SQLException ex) {
			messageErr = Constantes.PROBLEME_CONNECTION_DB;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		}
	}


	public static Segment getSegment(int seg_id) {
		Segment seg = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM segment_seg WHERE seg_id= " + seg_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				seg = new Segment(curseur);
				return seg;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Segment getSegment(Segment seg) {
		return DaoSegment.getSegment(seg.getId());
	}
}
