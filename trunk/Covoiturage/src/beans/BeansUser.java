package beans;

import java.util.Date;

import model.User;

import utilities.Constantes;
import utilities.ValidatorOfData;

import dao.TraitementSQL;

public class BeansUser {

	protected int id;
	protected String firstname;
	protected String lastname;
	protected String email;
	protected String password;
	protected String confirmPassword;
	protected int current_position;
	protected String genre;
	protected Date birthdate;
	protected String description;
	protected String mobilphone;
	protected int note;
	protected String registrationdate;
	protected String lastlogindate;

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
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the current_position
	 */
	public int getCurrent_position() {
		return current_position;
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
	public String getRegistrationdate() {
		return registrationdate;
	}

	/**
	 * @param registrationdate
	 *            the registrationdate to set
	 */
	public void setRegistrationdate(String registrationdate) {
		this.registrationdate = registrationdate;
	}

	/**
	 * @return the lastlogindate
	 */
	public String getLastlogindate() {
		return lastlogindate;
	}

	/**
	 * @param lastlogindate
	 *            the lastlogindate to set
	 */
	public void setLastlogindate(String lastlogindate) {
		this.lastlogindate = lastlogindate;
	}

	public BeansUser() {
		// TODO Auto-generated constructor stub
	}

	public BeansUser(String email, String password, String firstname,
			String lastname, String genre) {
		super();
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.genre = genre;
	}

	// afficher dans notre page web lorsqu'il y a un erreur
	String messageErr;

	public String getMessageErr() {
		return messageErr;
	}

	public void setMessageErreur(String messageErreur) {
		this.messageErr = messageErreur;
	}

	public void setMessageErr(String messageErr) {
		this.messageErr = messageErr;
	}

	public String toLogin() {
		messageErr = "";
		return "login";
	}

	public String toCreatAccount() {
		messageErr = "";
		return "creat_account";
	}

	/**
	 * 
	 */
	public String creatUser() {
		User userCreated;
		if (!ValidatorOfData.validateEMail(email)) {
			messageErr = Constantes.EMAIL_FORM_NOT_CORRECT;
			return "actuel";
		}

		// /// TODO
		if (!ValidatorOfData.validateData(firstname)) {
			messageErr = Constantes.DATA_FORM_NOT_CORRECT;
			return "actuel";
		}
		// ///

		if (email.equals("") || firstname.equals("") || lastname.equals("")
				|| genre.equals("")) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";

		} else if (!password.equals(confirmPassword) || password.equals("")
				|| confirmPassword.equals("") || email.equals("")) {
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}

		String lien = "ok";

		User utilisateur = null;
		
		try {

			userCreated = TraitementSQL.createUser(email, password);

			// TODO sauvegarder en session


		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();

			lien = "actuel";
		}

		return "ok";
	}

	/**
	 * 
	 */
	public String authentication() {

		messageErr = "";

		User userLogged = null;
		try {
			userLogged = TraitementSQL.authentification(email, password);
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}

		messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;

		return "actuel";
	}
}
