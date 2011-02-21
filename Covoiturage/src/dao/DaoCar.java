package dao;

import java.sql.ResultSet;
import java.sql.SQLException;


import utilities.Constantes;
import model.Car;

public class DaoCar {

	public static ConnexionBD con;

	/**
	 * 
	 * @param email
	 * @param passWord
	 * @return User
	 * @throws Exception
	 */
	public static Car createCar(String name, Integer seat, Integer owner)
			throws Exception {

		con = null;
		String messageErr = null;
		ResultSet res;
		Car car = null;

		try {

			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query;

			query = "call car_create('" + ConnexionBD.escape(name) + "', "
					+ seat + ", '" + owner + "')";

			res = con.execute(query);
			if (res.first())
				car = new Car(res);
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

		return car;

	}


	public static Car getCar(int car_id) {
		Car car = null;
		try {
			con = new ConnexionBD(ConnexionBD.url, ConnexionBD.nomDriver);

			String query = "SELECT * FROM car_car WHERE car_id= " + car_id
					+ "";

			ResultSet curseur = con.execute(query);
			if (curseur.first()) {
				car = new Car(curseur);
				return car;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static Car getCar(Car car) {
		return DaoCar.getCar(car.getId());
	}
}
