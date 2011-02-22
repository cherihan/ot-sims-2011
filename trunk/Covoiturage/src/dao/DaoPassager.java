package dao;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;


import utilities.Constantes;
import model.Passager;
import model.User;

public class DaoPassager {

	public static ConnexionBD con;
	
	public static Passager getPassager(int psg_id) {
		Passager psg = null;
		try {
			con = ConnexionBD.getConnexion();

			String query = "SELECT * FROM passager_psg WHERE psg_id= " + psg_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				psg = new Passager(curseur);
				return psg;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Passager getPassager(Passager psg) {
		return DaoPassager.getPassager(psg.getId());
	}
	
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
			con = ConnexionBD.getConnexion();

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
			con = ConnexionBD.getConnexion();

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

	public static void updatePassagerType(int rte_id, int usr_id, int pgt_id) {
		try {
			con = ConnexionBD.getConnexion();

			String query = "call route_passager_edit_type(" + rte_id + ", " + usr_id + ", "+pgt_id+")";
			@SuppressWarnings("unused")
			ResultSet curseur = con.execute(query);

		} catch (Exception e) {
		}
	}
	

	public static void updatePassagerType(int psg_id, int pgt_id) {
		Passager psg = DaoPassager.getPassager(psg_id);
		int rte_id=psg.getRoute();
		int usr_id=psg.getUser();
		try {
			con = ConnexionBD.getConnexion();

			String query = "call route_passager_edit_type(" + rte_id + ", " + usr_id + ", "+pgt_id+")";
			@SuppressWarnings("unused")
			ResultSet curseur = con.execute(query);

		} catch (Exception e) {
		}
	}
	
	public static void updatePassagerTypeAccept(int rte_id, int usr_id) {
		int pgt_id = Passager.PASSAGER_TYPE_ACCEPTED;
		DaoPassager.updatePassagerType(rte_id, usr_id, pgt_id);
	}
	
	public static void updatePassagerTypeReject(int rte_id, int usr_id) {
		int pgt_id = Passager.PASSAGER_TYPE_REJECTED;
		DaoPassager.updatePassagerType(rte_id, usr_id, pgt_id);
	}

}
