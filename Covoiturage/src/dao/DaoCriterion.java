package dao;

import java.sql.ResultSet;
import model.Criterion;
import model.Criterion_type;

public class DaoCriterion {

	public static ConnexionBD con;

	public static Criterion getCriterion(int crt_id) {
		Criterion crt = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "SELECT * FROM _criterion_crt WHERE crt_id= " + crt_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				crt = new Criterion(curseur);
				return crt;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static Criterion getCriterion(Criterion crt) {
		return DaoCriterion.getCriterion(crt.getId());
	}
	
	
	public static Criterion_type getCriterion_type(int ctt_id) {
		Criterion_type ctt = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

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
			return null;
		}
	}

	public static Criterion_type getCriterion_type(Criterion_type ctt) {
		return DaoCriterion.getCriterion_type(ctt.getId());
	}
}
