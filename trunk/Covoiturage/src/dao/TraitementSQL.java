package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import utilities.Constantes;
import model.User;

public class TraitementSQL {

	public static ConnexionBD con;
	public static String url = "jdbc:mysql://127.0.0.1:3306/sims?user=root&passwod="; // @jve:decl-index=0:
	public static String nomDriver = "com.mysql.jdbc.Driver"; // @jve:decl-index=0:

	
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

			con = new ConnexionBD(url, nomDriver);

			String query = "call user_create_short('" + email + "', '"
					+ password + "')";

			try {
				res = con.execute(query);				
				if(res.next())  user = new User(res);				
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
	 * cette fonction retourne une list qui contient (messageErr, email,
	 * firstName, lastName, genre)
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

			con = new ConnexionBD(url, nomDriver);

			String query = "call user_check_authentification('" + email + "', '"
					+ passWord + "')";
			ResultSet curseur = con.execute(query);

			if(curseur.next()) {
				userLogged = new User(curseur);
			}else{
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
			messageErr = Constantes.OTHER_PROBLEME_IN_CONNECTION_DB;
			System.err.println(messageErr + " : " + e);
			throw new Exception(messageErr);

		}

		if (con != null) {
			con.close();
		}

		return userLogged;

	}

	public static void main(String[] args) {

		// User test =
		// TraitementSQL.authentification("othman.bentria@gmail.com", "root");
		//
		// System.out.println(test.getFirstName());

	}

}
