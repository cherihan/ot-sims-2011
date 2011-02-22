package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;


import utilities.Constantes;
import model.Position;
import model.User;
import model.User_fav_position;

public class DaoUser_fav_position {

	public static ConnexionBD con;

	/**
	 * 
	 * @param email
	 * @param passWord
	 * @return User
	 * @throws Exception
	 */
	public static User_fav_position createUser_fav_position(String label, int usr_id, int pos_id)
			throws Exception {

		con = null;
		String messageErr = null;
		ResultSet res;
		User_fav_position ufp = null;

		try {

			con = ConnexionBD.getConnexion();

			String query;

			query = "call user_add_pos_fav("+usr_id+", "+pos_id+", '" + label + "')";
			res = con.execute(query);
			if (res.first())
				ufp = new User_fav_position(res);
			
		} catch (ClassNotFoundException ex) {
			messageErr = Constantes.CLASS_DB_NOT_FOUND;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		} catch (SQLException ex) {
			messageErr = Constantes.PROBLEME_CONNECTION_DB;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		}

		return ufp;

	}

	public static User_fav_position getUser_fav_position(int ufp_id) {
		User_fav_position ufp = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM user_fav_pos_ufp WHERE ufp_id= " + ufp_id;

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				ufp = new User_fav_position(curseur);
				return ufp;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static User_fav_position getUser_fav_position(User_fav_position ufp) {
		return DaoUser_fav_position.getUser_fav_position(ufp.getId());
	}

	public static Hashtable<Integer, User_fav_position> getFavoritePositionsOfUser(
			User usr) {
		return DaoUser_fav_position.getFavoritePositionsOfUser(usr.getId());
	}

	public static Hashtable<Integer, User_fav_position> getFavoritePositionsOfUser(
			int usr_id) {
		Hashtable<Integer, User_fav_position> list = new Hashtable<Integer, User_fav_position>();
		User_fav_position ufp = null;
		Position pos = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "call user_get_pos_fav(" + usr_id + ")";

			ResultSet curseur = con.execute(query);
			while (curseur.next()) {
				ufp = new User_fav_position(curseur);
				pos = new Position(curseur);
				ufp.setPositionObj(pos);
				list.put(ufp.getId(), ufp);
			}
		} catch (Exception e) {
			return list;
		}
		return list;
	}
}
