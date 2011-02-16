package beans;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import model.User;

import utilities.Constantes;
import utilities.ValidatorOfData;

import dao.TraitementSQL;

public class BeansUser {
	
	
	protected User user = new User();
	
	protected String confirmPassword;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return this.user.getId();
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.user.setId(id);
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return this.user.getFirstname();
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.user.setFirstname(firstname);
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return this.user.getLastname();
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.user.setLastname(lastname);
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.user.getEmail();
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.setEmail(email);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return user.getPassword();
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.user.setPassword(password);
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
		return user.getCurrent_position();
	}

	/**
	 * @param current_position
	 *            the current_position to set
	 */
	public void setCurrent_position(int current_position) {
		this.user.setCurrent_position(current_position);
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return user.getGenre();
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(String genre) {
		this.setGenre(genre);
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return user.getBirthdate();
	}

	/**
	 * @param birthdate
	 *            the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.user.setBirthdate(birthdate);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return user.getDescription();
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.user.setDescription(description);
	}

	/**
	 * @return the mobilphone
	 */
	public String getMobilphone() {
		return user.getMobilphone();
	}

	/**
	 * @param mobilphone
	 *            the mobilphone to set
	 */
	public void setMobilphone(String mobilphone) {
		this.user.setMobilphone(mobilphone);
	}

	/**
	 * @return the note
	 */
	public int getNote() {
		return user.getNote();
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(int note) {
		this.user.setNote(note);
	}

	/**
	 * @return the registrationdate
	 */
	public String getRegistrationdate() {
		return user.getRegistrationdate().toString();
	}

	/**
	 * @param registrationdate
	 *            the registrationdate to set
	 */
	public void setRegistrationdate(String registrationdate) {
		//this.registrationdate = registrationdate;
	}

	/**
	 * @return the lastlogindate
	 */
	public String getLastlogindate() {
		return user.getLastlogindate().toString();
	}

	/**
	 * @param lastlogindate
	 *            the lastlogindate to set
	 */
	public void setLastlogindate(String lastlogindate) {
		//this.lastlogindate = lastlogindate;
	}

	public BeansUser() {
		// TODO Auto-generated constructor stub
	}

	public BeansUser(String email, String password, String firstname,
			String lastname, String genre) {
		super();
		this.setEmail(email);
		this.setPassword(password);
		this.setFirstname(firstname);
		this.setLastname(lastname);
		this.setGenre(genre);
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
		if (!ValidatorOfData.validateEMail(this.getEmail())) {
			messageErr = Constantes.EMAIL_FORM_NOT_CORRECT;
			return "actuel";
		}

		// /// TODO
		if (!ValidatorOfData.validateData(this.getFirstname())) {
			messageErr = Constantes.DATA_FORM_NOT_CORRECT;
			return "actuel";
		}
		// ///

		if (this.getEmail().equals("")) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";

		} else if (!this.getPassword().equals(this.getConfirmPassword()) || this.getPassword().equals("")
				|| this.getConfirmPassword().equals("") || this.getEmail().equals("")) {
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}

		String lien = "ok";

		User utilisateur = null;
		
		try {

			userCreated = TraitementSQL.createUser(this.getEmail(), this.getPassword());
			
			this.user=userCreated;
			//HttpSession session = Request.getSession(true);
			
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
			userLogged = TraitementSQL.authentification(this.getEmail(), this.getPassword());
			this.user=userLogged;
			
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}

		messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;

		return "actuel";
	}
	
	public User getLoggedUser() {
		return this.user;		
	}
}
