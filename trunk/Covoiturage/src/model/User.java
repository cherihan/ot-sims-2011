package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import utilities.DateUtils;

import dao.DaoCriterion;
import dao.DaoPosition;
import dao.DaoRoute;
import dao.DaoUser_fav_position;

public class User {

	protected int id;
	protected String firstname;
	protected String lastname;
	protected String email;
	protected String password;
	protected int current_position;
	protected String genre;
	protected Date birthdate;
	protected String description;
	protected String mobilphone;
	protected int note;
	protected Date registrationdate;
	protected Date lastlogindate;
	protected String fullname;

	Position current_positionObj;

	protected Hashtable<Integer, Criterion> criterions;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param password
	 * @param current_position
	 * @param genre
	 * @param birthdate
	 * @param description
	 * @param mobilphone
	 * @param note
	 * @param registrationdate
	 * @param lastlogindate
	 */
	public User(int id, String firstname, String lastname, String email,
			String password, int current_position, String genre,
			Date birthdate, String description, String mobilphone, int note,
			Date registrationdate, Date lastlogindate) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.fullname = firstname + " " + lastname;
		this.email = email;
		this.password = password;
		this.current_position = current_position;
		this.genre = genre;
		this.birthdate = birthdate;
		this.description = description;
		this.mobilphone = mobilphone;
		this.note = note;
		this.registrationdate = registrationdate;
		this.lastlogindate = lastlogindate;
	}

	/**
	 * @param id
	 */
	public User(int id) {
		super();
		this.id = id;
	}

	public User(User user) {
		super();

		this.id = user.id;
		this.firstname = user.firstname;
		this.lastname = user.lastname;
		this.fullname = firstname + " " + lastname;
		this.email = user.email;
		this.password = user.password;
		this.current_position = user.current_position;
		this.genre = user.genre;
		this.birthdate = user.birthdate;
		this.description = user.description;
		this.mobilphone = user.mobilphone;
		this.note = user.note;
		this.registrationdate = user.registrationdate;
		this.lastlogindate = user.lastlogindate;

	}

	/**
	 * @param id
	 */
	public User() {
		super();
	}

	/**
	 * @param Hashtable
	 *            <String><String>
	 */
	public User(Hashtable<String, String> sqlrow) {
		super();

		this.id = Integer.parseInt(sqlrow.get("usr_id"));
		this.firstname = sqlrow.get("usr_firstname");
		this.lastname = sqlrow.get("usr_lastname");
		this.fullname = firstname + " " + lastname;
		this.email = sqlrow.get("usr_email");
		this.password = sqlrow.get("usr_password");
		this.current_position = Integer.parseInt(sqlrow
				.get("usr_current_position"));
		this.genre = sqlrow.get("usr_genre");
		this.birthdate = DateUtils.getTimestampAsDate(Integer.valueOf(sqlrow.get("usr_birthdate")));
		this.description = sqlrow.get("usr_description");
		this.mobilphone = sqlrow.get("usr_mobilphone");
		this.note = Integer.parseInt(sqlrow.get("usr_note"));
		this.registrationdate = DateUtils.getTimestampAsDate(Integer.valueOf(sqlrow.get("usr_registrationdate")));
		this.lastlogindate = DateUtils.getTimestampAsDate(Integer.valueOf(sqlrow.get("usr_lastlogindate")));
	}

	public User(ResultSet sqlrow) throws SQLException {
		super();

		this.id = (sqlrow.getInt("usr_id"));
		this.firstname = sqlrow.getString("usr_firstname");
		this.lastname = sqlrow.getString("usr_lastname");
		this.fullname = firstname + " " + lastname;
		this.email = sqlrow.getString("usr_email");
		this.password = sqlrow.getString("usr_password");
		this.current_position = (sqlrow.getInt("usr_current_position"));
		this.genre = sqlrow.getString("usr_genre");

		this.birthdate = DateUtils.getTimestampAsDate(sqlrow.getInt("usr_birthdate"));

		this.description = sqlrow.getString("usr_description");
		this.mobilphone = sqlrow.getString("usr_mobilphone");
		this.note = (sqlrow.getInt("usr_note"));
		this.registrationdate = DateUtils.getTimestampAsDate(sqlrow.getInt("usr_registrationdate"));
		this.lastlogindate = DateUtils.getTimestampAsDate(sqlrow.getInt("usr_lastlogindate"));
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the current_position
	 */
	public int getCurrent_position() {
		return current_position;
	}

	public Position getCurrent_positionObj() {
		if (this.current_positionObj == null) {
			if (this.getCurrent_position() > 0) {
				this.current_positionObj = DaoPosition.getPosition(this
						.getCurrent_position());
			} else {
				this.current_positionObj = null;
			}
		}
		return this.current_positionObj;
	}

	/**
	 * @param current_position
	 *            the current_position to set
	 */
	public void setCurrent_position(int current_position) {
		this.current_position = current_position;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate
	 *            the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @param birthdate
	 *            the year of the birthdate to set
	 * 
	 * exemple : user.setBirthdateByYear("1988");
	 *
	 */
	public String getBirthdateByYear() {
		DateFormat dfm = new SimpleDateFormat("yyyy");
		return dfm.format(this.birthdate);
	}
	
	public void setBirthdateByYear(String strYear) {
		DateFormat dfm = new SimpleDateFormat("yyyy");
		try {
			this.birthdate = dfm.parse(strYear);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the mobilphone
	 */
	public String getMobilphone() {
		return mobilphone;
	}

	/**
	 * @param mobilphone
	 *            the mobilphone to set
	 */
	public void setMobilphone(String mobilphone) {
		this.mobilphone = mobilphone;
	}

	/**
	 * @return the note
	 */
	public int getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(int note) {
		this.note = note;
	}

	/**
	 * @return the registrationdate
	 */
	public Date getRegistrationdate() {
		return registrationdate;
	}

	/**
	 * @param registrationdate
	 *            the registrationdate to set
	 */
	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	/**
	 * @return the lastlogindate
	 */
	public Date getLastlogindate() {
		return lastlogindate;
	}

	/**
	 * @param lastlogindate
	 *            the lastlogindate to set
	 */
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}

	public Hashtable<Integer, Criterion> getCriterionsOfUser() {
		return DaoCriterion.getCriterionsOfUser(this.getId());
	}

	public Hashtable<Integer, Criterion> getCriterionsOfUserOfType(int ctt_id) {
		return DaoCriterion.getCriterionsOfUserOfType(this.getId(), ctt_id);
	}

	public Integer getBirthdateAsInteger() {
		return Integer.valueOf(String
				.valueOf(this.getBirthdate().getTime() / 1000));
	}

	public Hashtable<Integer, Route> getRouteOfUser(Date date_departure_begin,
			Date date_departure_end, int rtp_id) {
		return DaoRoute.route_search_of_owner(this.getId(),
				date_departure_begin, date_departure_end, rtp_id);
	}
	
	
	/**
	 * Accès/modification des lieux "favoris"
	 */

	public void setFavoritePosition(String posLabel, String position) {
		//TODO: tests
		// - lorsqu'il n'y a pas d'adresse déjà remplie
		//   -> nouvelle adresse
		// - lorsqu'il y a déjà une adresse remplie
		//   -> l'adresse est mise à jour
		// - lorsque l'adresse est incorrecte
		//   -> l'action est sans effet
		try {
			Position fav_position = DaoPosition.getPositionByAddress(position);
			DaoUser_fav_position.createUser_fav_position(posLabel, this.getId(), fav_position.getId());
		} catch (Exception e) {
		
		}
	}
	
	public void setFavoritePositionHome(String position) {
		this.setFavoritePosition("Domicile", position);
	}
	
	public void setFavoritePositionWork(String position) {
		this.setFavoritePosition("Bureau", position);
	}
	
	public String getFavoritePosition(String label) {
		User_fav_position pos = DaoUser_fav_position.getFavoritePositionsOfUserForLabel(this.getId(), label);
		if(pos!=null && pos.getPositionObj()!=null)
			return pos.getPositionObj().getAddress();
		return "";
	}
	
	public String getFavoritePositionHome() {
		return this.getFavoritePosition("Domicile");
	}
	
	public String getFavoritePositionWork() {
		return this.getFavoritePosition("Bureau");
	}
	
	public List<Route> getAllRouteOfUser() {
		Calendar c = Calendar.getInstance();
		c.set(2000, 1, 1);
		Date d1 = c.getTime();
		c.set(2012, 1, 1);
		Date d2 = c.getTime();
		Hashtable<Integer, Route> routes = DaoRoute.route_search_of_owner(this.getId(), d1, d2, 0);
		return Arrays.asList(routes.values().toArray(new Route[0]));
	}
	
}
