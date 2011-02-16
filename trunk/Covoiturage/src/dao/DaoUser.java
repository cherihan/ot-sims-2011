package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import utilities.Constantes;
import beans.Annonce;
import model.User;

public class DaoUser {

	public static ConnexionBD con;
	/*
	public static String url = "jdbc:mysql://127.0.0.1:3306/sims?user=root&passwod="; // @jve:decl-index=0:
	public static String nomDriver = "com.mysql.jdbc.Driver"; // @jve:decl-index=0:
	*/
	
	/**
	 * 
	 * @param email
	 * @param passWord
	 * @return User
	 * @throws Exception
	 */
	public static User createUser(String email, String password)
			throws Exception {

		con = null;
		String messageErr = null;
		ResultSet res;
		User user = null;

		try {

			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call user_create_short('" + email + "', '"
					+ password + "')";

			try {
				res = con.execute(query);				
				if(res.first())  
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

		if (con != null)
			con.close();

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

			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call user_check_authentification('" + email + "', '"
					+ passWord + "')";
			ResultSet curseur = con.execute(query);

			if(curseur.next()) {
				userLogged = new User(curseur);
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

		if (con != null) {
			con.close();
		}

		return userLogged;

	}
	
	public static User getUser(int usr_id) {
		User usr=null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "SELECT * FROM user_usr WHERE usr_id= " + usr_id + "";

			ResultSet curseur = con.execute(query);
			if(curseur.first()){
				usr = new User(curseur);
				return usr;
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public static User getUser(User usr) {
		return DaoUser.getUser(usr.getId());
	}
}

