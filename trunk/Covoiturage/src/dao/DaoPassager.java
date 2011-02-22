package dao;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;


import utilities.Constantes;
import model.Passager;
import model.User;

public class DaoPassager {

	public static ConnexionBD con;
	
	/*
	 * Effectue une demande d'invitation de la part du passager pour le trajet
	 */
	public static void route_add_passager(int rte_id, int passager_user_id)
			throws Exception {
		Hashtable<Integer, Passager> current_list = new Hashtable<Integer, Passager>();
		Passager psg = null;

		current_list = DaoRoute.getPassagers(rte_id);
		Enumeration<Passager> en = current_list.elements();
		while (en.hasMoreElements()) {
			psg = (Passager) en.nextElement();
			if (psg.getUser() == passager_user_id) {
				// is already a passager of this route
				throw new Exception(Constantes.ROUTE_ALREADY_PASSSAGER);
			}
		}

		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call route_join(" + rte_id + ", "
					+ passager_user_id + ")";
			@SuppressWarnings("unused")
			ResultSet curseur = con.execute(query);

		} catch (Exception e) {
		}
	}
	
	public static Hashtable<Integer, Passager> getPassagers(int rte_id) {
		return DaoPassager.getPassagersOfType(rte_id, 0);
	}
	
	public static Hashtable<Integer, Passager> getPassagersOfType(int rte_id, int pgt_id) {
		Hashtable<Integer, Passager> list = new Hashtable<Integer, Passager>();
		Passager psg = null;
		User usr = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "call route_get_passagers_of_type(" + rte_id + ", "+pgt_id+")";
			ResultSet curseur = con.execute(query);

			while (curseur.next()) {
				psg = new Passager(curseur);
				usr = new User(curseur);
				psg.setUserObj(usr);
				list.put(psg.getId(), psg);
			}
		} catch (Exception e) {
			return list;
		}
		return list;
	}

}
