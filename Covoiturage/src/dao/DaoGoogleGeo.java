package dao;

import java.sql.ResultSet;
import java.util.Hashtable;


import model.Google_cache;

public class DaoGoogleGeo {

	public static ConnexionBD con;

	public static void createOrUpdateGoogleGeo(String address,
			Hashtable<String, Double> coords)  {

		con = null;

		try {

			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query;

				query = "googlecache_create_or_update('"
						+ ConnexionBD.escape(address) + "', "
						+ coords.get("latitude") + ", "
						+ coords.get("longitude") + ")";

				con.execute(query);
				

		} catch (Exception ex) {
		}

		if (con != null)
			con.close();

	}

	public static Google_cache getByAddress(String address) {
		con = null;

		Google_cache gch = null;

		try {

			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call googlecache_get_by_address('"
					+ ConnexionBD.escape(address) + "')";
			ResultSet curseur = con.execute(query);

			if (curseur.first()) {
				gch = new Google_cache(curseur);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

		if (con != null) {
			con.close();
		}

		return gch;
	}

	public static Google_cache getByCoords(Hashtable<String, Double> coords) {
		con = null;

		Google_cache gch = null;

		try {

			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call googlecache_get_by_coords(" + coords.get("latitude") + ", "
			+ coords.get("longitude") + ")";
			ResultSet curseur = con.execute(query);

			if (curseur.first()) {
				gch = new Google_cache(curseur);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

		if (con != null) {
			con.close();
		}

		return gch;
	}
}
