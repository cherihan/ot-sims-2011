package beans;


import java.util.Date;

import utilities.Constantes;

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
	protected String birthdate;
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
	 * @param id the id to set
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
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
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
	public String getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(String birthdate) {
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
	public String getRegistrationdate() {
		return registrationdate;
	}

	/**
	 * @param registrationdate the registrationdate to set
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
	 * @param lastlogindate the lastlogindate to set
	 */
	public void setLastlogindate(String lastlogindate) {
		this.lastlogindate = lastlogindate;
	}

	
	public BeansUser() {
		// TODO Auto-generated constructor stub
	}

	public BeansUser(String email, String password, String firstName,
			String lastName, String genre) {
		super();
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.genre = genre;
	}
	
	//� afficher dans notre page web lors d'un erreur
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
	
	
	public String toLogin()
	{
		messageErr = "";
		return "login";
	}
	
	public String toCreatAccount()
	{
		messageErr = "";
		return "creat_account";
	}
	
	

	/**
	 * 
	 */
	public String creatUser()
	{
				
		if(email.equals("") || firstname.equals("") || lastname.equals("") || genre.equals("") ){
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
			
		}else if(!password.equals(confirmPassword) || password.equals("") || confirmPassword.equals("") || email.equals("") ){
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}
		
		String lien = "";
		
		try {
			lien = TraitementSQL.creatUser(email, password, firstname, lastname, genre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}		
		
		
		return lien;
	}
	
	
	
	/**
	 * 
	 */
	public String authentication(){		
					
		messageErr = "";
			
		if(!password.equals(confirmPassword) || password.equals("") || confirmPassword.equals("") || email.equals("") ){
			
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			
			return "actuel";
		}		
		
		BeansUser utilisateur = null ;
		try {
			utilisateur = TraitementSQL.authentification(email, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}			
	
			if(utilisateur != null && utilisateur.getFirstname() != null){			
				email = utilisateur.getEmail();
				firstname = utilisateur.getFirstname();
				lastname = utilisateur.getLastname();
				genre = utilisateur.getGenre();				
				messageErr = "";
				return "ok";
			}
		
		messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;
		
		return "actuel";
	}
	
	
}
