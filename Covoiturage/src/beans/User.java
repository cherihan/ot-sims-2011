package beans;


import java.util.Date;

import utilities.Constantes;

import dao.TraitementSQL;

public class User {

	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String email, String passWord, String firstName,
			String lastName, String genre) {
		super();
		this.email = email;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.genre = genre;		
	}




	String email;
	String passWord;	
	String confirmPassWord;
	String firstName;
	String lastName;
	String genre;
	Date birthDay;
	String description;
	String mobilePhone;
	String note;
	int currentPosition;
		
	//à afficher dans notre page web lors d'un erreur
	String messageErr;
	public String getMessageErr() {
		return messageErr;
	}
	public void setMessageErreur(String messageErreur) {
		this.messageErr = messageErreur;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	
	
	public String getConfirmPassWord() {
		return confirmPassWord;
	}

	public void setConfirmPassWord(String confirmPassWord) {
		this.confirmPassWord = confirmPassWord;
	}

	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
		
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setMessageErr(String messageErr) {
		this.messageErr = messageErr;
	}
	
	
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
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
				
		if(email.equals("") || firstName.equals("") || lastName.equals("") || genre.equals("") ){
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
			
		}else if(!passWord.equals(confirmPassWord) || passWord.equals("") || confirmPassWord.equals("") || email.equals("") ){
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}
		
		String lien = "";
		
		try {
			lien = TraitementSQL.creatUser(email, passWord, firstName, lastName, genre);
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
			
		if(!passWord.equals(confirmPassWord) || passWord.equals("") || confirmPassWord.equals("") || email.equals("") ){
			
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			
			return "actuel";
		}		
		
		User utilisateur = null ;
		try {
			utilisateur = TraitementSQL.authentification(email, passWord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}			
	
			if(utilisateur != null && utilisateur.getFirstName() != null){			
				email = utilisateur.getEmail();
				firstName = utilisateur.getFirstName();
				lastName = utilisateur.getLastName();
				genre = utilisateur.getGenre();				
				messageErr = "";
				return "ok";
			}
		
		messageErr = Constantes.PASSWORD_OR_USER_NOT_GOOD;
		
		return "actuel";
	}
	
	
}
