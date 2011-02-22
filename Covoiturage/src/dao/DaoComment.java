package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;


import utilities.Constantes;
import model.Comment;

public class DaoComment {

	public static ConnexionBD con;

	/**
	 * 
	 * @param email
	 * @param passWord
	 * @return User
	 * @throws Exception
	 */
	public static Comment createCommentOrUpdate(Integer user_comment_from, Integer user_comment_to, String text, Integer note)
			throws Exception {

		con = null;
		String messageErr = null;
		ResultSet res;
		Comment cmn = null;

		try {

			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query;

			query = "call comment_create(" + user_comment_from + ", "+ user_comment_to + ", '"+ConnexionBD.escape(text)+"', "+note+")";

			res = con.execute(query);
			if (res.first())
				cmn = new Comment(res);
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

		return cmn;

	}


	public static Comment getComment(int cmn_id) {
		Comment cmn = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "SELECT * FROM comment_cmn WHERE cmn_id= " + cmn_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				cmn = new Comment(curseur);
				return cmn;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static Comment getComment(Comment cmn) {
		return DaoComment.getComment(cmn.getId());
	}
	
	public static Hashtable<Integer, Comment> getCommentPostedBy(int usr_id) {
		Hashtable<Integer, Comment> list = new Hashtable<Integer, Comment>();
		Comment cmn = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call comment_get_posted_by("+usr_id+")";

			ResultSet curseur = con.execute(query);
			while(curseur.next()) {
				cmn = new Comment(curseur);
				list.put(cmn.getId(), cmn);
			}
		} catch (Exception e) {
			return list;
		}
		return list;
	}
	
	public static Hashtable<Integer, Comment> getCommentPostedAbout(int usr_id) {
		Hashtable<Integer, Comment> list = new Hashtable<Integer, Comment>();
		Comment cmn = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call comment_get_posted_about("+usr_id+")";

			ResultSet curseur = con.execute(query);
			while(curseur.next()) {
				cmn = new Comment(curseur);
				list.put(cmn.getId(), cmn);
			}
		} catch (Exception e) {
			return list;
		}
		return list;
	}
	
	public static void deleteComment(int cmn_id) {
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call comment_delete("+cmn_id+")";

			@SuppressWarnings("unused")
			ResultSet curseur = con.execute(query);
			
		} catch (Exception e) {
		}
	}


	public static Comment getCommentFromAbout(int user_from_id, int user_to_id) {
		Comment cmn = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call comment_get_from_and_about("+user_from_id+", "+user_to_id+")";

			ResultSet curseur = con.execute(query);
			if(curseur.first()) {
				cmn = new Comment(curseur);
				return cmn;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
}
