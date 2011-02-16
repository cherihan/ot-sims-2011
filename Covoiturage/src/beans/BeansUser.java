package beans;


import model.User;

import utilities.Constantes;
import utilities.ValidatorOfData;

import dao.TraitementSQL;

public class BeansUser {
	
	
	protected User user = new User();
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public BeansUser() {
		// TODO Auto-generated constructor stub
	}


	public BeansUser(String email, String password, String firstname,
			String lastname, String genre) {		
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setGenre(genre);
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
		if (!ValidatorOfData.validateEMail(user.getEmail())) {
			messageErr = Constantes.EMAIL_FORM_NOT_CORRECT;
			return "actuel";
		}

		// /// TODO
		if (!ValidatorOfData.validateData(user.getFirstname())) {
			messageErr = Constantes.DATA_FORM_NOT_CORRECT;
			return "actuel";
		}
		// ///

		if (user.getEmail().equals("")) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";

		} else if (!user.getPassword().equals(user.getConfirmPassword()) || user.getPassword().equals("")
				|| user.getConfirmPassword().equals("") || user.getEmail().equals("")) {
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}

		String lien = "ok";

		User utilisateur = null;
		
		try {

			userCreated = TraitementSQL.createUser(user.getEmail(), user.getPassword());
			
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
			userLogged = TraitementSQL.authentification(user.getEmail(), user.getPassword());
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
