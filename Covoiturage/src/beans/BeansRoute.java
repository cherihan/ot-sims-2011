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
	protected Date date_depart = null;
	protected Integer hour_depart = null;
	protected Integer minute_depart = null;

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

	public Date getDate_depart() {
		return date_depart;
	}

	public void setDate_depart(Date date_depart) {
		this.date_depart = date_depart;
	}

	public Integer getHour_depart() {
		return hour_depart;
	}

	public void setHour_depart(Integer hour_depart) {
		this.hour_depart = hour_depart;
	}

	public Integer getMinute_depart() {
		return minute_depart;
	}

	public void setMinute_depart(Integer minute_depart) {
		this.minute_depart = minute_depart;
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
		if (date_depart == null || minute_depart == null || hour_depart == null
				|| pos_depart == null || pos_arrive == null) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date_depart);
		c.set(Calendar.HOUR_OF_DAY, hour_depart);
		c.set(Calendar.MINUTE, minute_depart);
		Date full_date_depart = c.getTime();

		try {
			// Code Test
			route = DaoRoute.createRoute(0, pos_depart, pos_arrive,
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
