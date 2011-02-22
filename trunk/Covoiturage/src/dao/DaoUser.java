package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import utilities.Constantes;
import model.Criterion;
import model.Position;
import model.User;
import model.User_fav_position;

public class DaoUser {

	public static ConnexionBD con;

	/**
	 * 
	 * @param email
	 * @param passWord
	 * @return User
	 * @throws Exception
	 */
	public static User createUser(String email, String password, String firstName, String lastName, String mobilePhone)
			throws Exception {

		con = null;
		String messageErr = null;
		ResultSet res;
		User user = null;

		try {

			con = ConnexionBD.getConnexion();

			String query;

			try {
				query = "call user_get_user_by_email('" + email + "')";

				res = con.execute(query);
				if (res.first())//There is result -> email is already used
					throw new Exception(Constantes.USER_ALREADY_SAVED);

				query = "call user_create_short('" + email + "', '" + password	+ "', '" + firstName +
				"', '" + lastName + "', '" + mobilePhone + "')";

				res = con.execute(query);
				if (res.first())
					user = new User(res);
			} catch (MySQLIntegrityConstraintViolationException ex) {
				messageErr = Constantes.USER_ALREADY_SAVED;
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

		return user;

	}
	
	/**
	 * 
	 * @param email
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	public static User authentification(String email, String passWord)
			throws Exception {
		con = null;

		String messageErr = null;
		User userLogged = null;

		try {

			con = ConnexionBD.getConnexion();

			String query = "call user_check_authentification('" + email
					+ "', '" + passWord + "')";
			ResultSet curseur = con.execute(query);

			if (curseur.first()) {
				userLogged = new User(curseur);
			} else {
				messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;
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
		} catch (Exception e) {
			messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;
			System.err.println(messageErr + " : " + e);
			throw new Exception(messageErr);

		}

		return userLogged;

	}
	
	
	public static User changeProfile(User userTemp) throws Exception {
		con = null;
		String messageErr = null;
		User userLogged = null;

		try {
			con = ConnexionBD.getConnexion();
			String query = "call user_update(" + userTemp.getId() + ", '"
					+ ConnexionBD.escape(userTemp.getEmail()) + "', '"
					+ ConnexionBD.escape(userTemp.getPassword()) + "', '"
					+ ConnexionBD.escape(userTemp.getFirstname()) + "', '"
					+ ConnexionBD.escape(userTemp.getLastname()) + "',"
					+ "'male'," + userTemp.getBirthdateAsInteger() + ", '"
					+ ConnexionBD.escape(userTemp.getDescription()) + "', '"
					+ ConnexionBD.escape(userTemp.getMobilphone()) + "')";
			con.execute(query);

		} catch (ClassNotFoundException ex) {
			messageErr = Constantes.CLASS_DB_NOT_FOUND;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		} catch (SQLException ex) {
			messageErr = Constantes.PROBLEME_CONNECTION_DB;
			System.err.println(messageErr + " : " + ex);
			throw new Exception(messageErr);
		} catch (Exception e) {
			messageErr = Constantes.UNEXPECTED_ERROR;
			System.err.println(messageErr + " : " + e);
			throw new Exception(messageErr);
		}
		return userLogged;
	}
	

	public static User getUser(int usr_id) {
		User usr = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM user_usr WHERE usr_id= " + usr_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				usr = new User(curseur);
				return usr;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static User getUser(User usr) {
		return DaoUser.getUser(usr.getId());
	}

	public static Hashtable<Integer, Criterion> getCriterionsOfUser(int usr_id) {
		return DaoCriterion.getCriterionsOfUser(usr_id);
	}

	public static Hashtable<Integer, User_fav_position> getFavoritePositionsOfUser(
			User usr) {
		return DaoUser.getFavoritePositionsOfUser(usr.getId());
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
	
	
	
//	public static void main(String[] args) {
//		
//		User utilisateur  = new User(2, "test", "kk", "kk@gmail.com", "kk", 5, "male", new Date(), "", "", 1, new Date(), new Date());
//		
//		
//		try {
//			DaoUser.changeProfile(utilisateur);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			DaoUser.authentification("kk@gmail.com", "kk");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
}