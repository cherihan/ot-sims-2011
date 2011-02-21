package beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.DaoRoute;

import utilities.Constantes;

import model.Passager;
import model.Route;

public class BeansRoute {

	protected Route route = new Route();

	protected String pos_depart = null;
	protected String pos_arrive = null;
	protected Integer minutes_to_depart = null;

	protected String messageErr;

	public String getPos_depart() {
		return pos_depart;
	}

	public void setPos_depart(String pos_depart) {
		this.pos_depart = pos_depart;
	}

	public String getPos_arrive() {
		return pos_arrive;
	}

	public void setPos_arrive(String pos_arrive) {
		this.pos_arrive = pos_arrive;
	}

	public Integer getMinutes_to_depart() {
		return minutes_to_depart;
	}

	public void setMinutes_to_depart(Integer minutes_to_depart) {
		this.minutes_to_depart = minutes_to_depart;
	}

	/**
	 * @return
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * @param route
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * @return the error message
	 */
	public String getMessageErr() {
		return messageErr;
	}

	/**
	 * @param messageErr
	 */
	public void setMessageErr(String messageErr) {
		this.messageErr = messageErr;
	}

	/**
	 * Create new route
	 */
	public String createRoute() {
		Route createdRoute = null;
		if (minutes_to_depart == null || pos_depart == null
				|| pos_arrive == null) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
		}

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((c.getTimeInMillis()/1000 + minutes_to_depart*60)*1000);
		Date full_date_depart = c.getTime();

		try {
			// Code Test
			route = DaoRoute.createRoute(1, pos_depart, pos_arrive,
					full_date_depart, null, null, 1, 3, 0);
		} catch (Exception e) {
			e.printStackTrace();
			this.messageErr = e.getMessage();
			return "actuel";
		}

		return null;
	}

	public List<Passager> getAllPassagers() {
		return null;

	}
}
