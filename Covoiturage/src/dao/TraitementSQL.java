package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import utilities.Constantes;
import beans.Annonce;
import model.User;

public class TraitementSQL {

	public static ConnexionBD con;
	public static String url = "jdbc:mysql://127.0.0.1:3306/sims?user=root&passwod="; // @jve:decl-index=0:
	public static String nomDriver = "com.mysql.jdbc.Driver"; // @jve:decl-index=0:

	/**
	 * 
	 * @param depart
	 * @param arrive
	 * @param date
	 * @return
	 */
	public static String insert(String depart, String arrive, Date date) {
		con = null;
		String lien = "list";

		try {
			con = new ConnexionBD(url, nomDriver);

			long formatteur = date.getTime();

			String query = "INSERT INTO chemin (depart, arrivee, date) values ('"
					+ depart + "', '" + arrive + "', '" + formatteur + "');";
			con.insert(query);

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			System.out.println("Connection echouer.............");
			lien = "actuel";

		}

		con.close();
		return lien;

	}

	/**
	 * 
	 * @return
	 */
	public static ArrayList<Annonce> loadData() {

		ArrayList<Annonce> listofAnnonces = new ArrayList<Annonce>();
		con = null;

		try {

			con = new ConnexionBD(url, nomDriver);
			String query = "SELECT * FROM chemin";

			ResultSet curseur = con.search(query);

			while (curseur.next()) {
				String depart = curseur.getString(2);
				String arrive = curseur.getString(3);

				System.out.println("arrive :" + arrive);

				Date date = new Date(curseur.getLong(4));

				System.out.println("Daaaate :" + date);

				listofAnnonces.add(new Annonce(depart, arrive, date));

				System.out.println(depart + " # " + arrive + " # " + date);

			}

		} catch (Exception e) {
			System.err.println("Exception!: " + e.getMessage());
			System.out.println("Connection echouer.............");
		}

		con.close();
		return listofAnnonces;

	}

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
