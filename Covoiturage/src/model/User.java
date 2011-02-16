package model;


import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;

public class User {

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
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
	
	/**
	 * @param Hashtable<String><String>
	 */
	public User(Hashtable<String, String> sqlrow) {
		super();
		
		this.id = Integer.parseInt(sqlrow.get("usr_id"));
		this.firstname = sqlrow.get("usr_firstname");
		this.lastname = sqlrow.get("usr_lastname");
		this.email = sqlrow.get("usr_email");
		this.password = sqlrow.get("usr_password");
		this.current_position = Integer.parseInt(sqlrow.get("usr_current_position"));
		this.genre = sqlrow.get("usr_genre");
		//this.birthdate = DateFormat.parse(sqlrow.get("usr_birthdate"));
		this.description = sqlrow.get("usr_description");
		this.mobilphone = sqlrow.get("usr_mobilphone");
		this.note = Integer.parseInt(sqlrow.get("usr_note"));
		//this.registrationdate = sqlrow.get("usr_registrationdate");
		//this.lastlogindate = sqlrow.get("usr_lastlogindate");
	}
	
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
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
	 * @param lastname the lastname to set
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
	 * @param email the email to set
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
	 * @param password the password to set
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
	/**
	 * @param current_position the current_position to set
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
	 * @param genre the genre to set
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
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
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
	 * @param mobilphone the mobilphone to set
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
	 * @param note the note to set
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
	 * @param registrationdate the registrationdate to set
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
	 * @param lastlogindate the lastlogindate to set
	 */
	public void setLastlogindate(Date lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	
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
}
