package dao;

import java.sql.ResultSet;
import java.util.Hashtable;

import model.Criterion;
import model.Criterion_type;

public class DaoCriterion {

	public static ConnexionBD con;

	public static Criterion getCriterion(int crt_id) {
		Criterion crt = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM _criterion_crt WHERE crt_id= " + crt_id + "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				crt = new Criterion(curseur);
				return crt;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Criterion getCriterion(Criterion crt) {
		return DaoCriterion.getCriterion(crt.getId());
	}
	
	
	public static Criterion_type getCriterion_type(int ctt_id) {
		Criterion_type ctt = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM _criterion_type_ctt WHERE ctt_id= " + ctt_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				ctt = new Criterion_type(curseur);
				return ctt;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Criterion_type getCriterion_type(Criterion_type ctt) {
		return DaoCriterion.getCriterion_type(ctt.getId());
	}

	public static Hashtable<Integer, Criterion> getCriterionsOfUser(int usr_id) {
		
		Hashtable<Integer, Criterion> list = new Hashtable<Integer, Criterion>();
		
		try {
			con = ConnexionBD.getConnexion();

			String query = "call get_criterions_of_user(" + usr_id + ")";

			ResultSet curseur = con.execute(query);
			while(curseur.next()) {
				list.put(curseur.getInt("crt_id"), new Criterion(curseur));
			}
		} catch (Exception e) {
			return list;
		}
		return list;
	}
	

	public static Hashtable<Integer, Criterion> getCriterionsOfUserOfType(int usr_id, int ctt_id) {
		
		Hashtable<Integer, Criterion> list = new Hashtable<Integer, Criterion>();
		
		try {
			con = ConnexionBD.getConnexion();

			String query = "call get_criterions_of_user_of_type(" + usr_id + ", " + ctt_id + ")";

			ResultSet curseur = con.execute(query);
			while(curseur.next()) {
				list.put(curseur.getInt("crt_id"), new Criterion(curseur));
			}
		} catch (Exception e) {
			return list;
		}
		return list;
	}
}

