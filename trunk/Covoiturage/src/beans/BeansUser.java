package beans;


import model.User;

import utilities.Constantes;
import utilities.ValidatorOfData;

import dao.DaoUser;

public class BeansUser {
	
	
	protected User user = new User();
	protected String messageErr;
	protected String confirmPassword;
	
	

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the messageErr
	 */
	public String getMessageErr() {
		return messageErr;
	}

	/**
	 * @param messageErr the messageErr to set
	 */
	public void setMessageErr(String messageErr) {
		this.messageErr = messageErr;
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
	

	public String toLogin() {
		messageErr = "";
		return "login";
	}

	public String toCreatAccount() {
		messageErr = "";
		return "create_account";
	}

	/**
	 * 
	 */
	public String creatUser() {
		User userCreated = null;
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

		} else if (!user.getPassword().equals(this.getConfirmPassword()) || user.getPassword().equals("")
				|| this.getConfirmPassword().equals("") || user.getEmail().equals("")) {
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}

		try {
			userCreated = DaoUser.createUser(user.getEmail(), user.getPassword().replaceAll("'", "''"), user.getFirstname(), user.getLastname(), user.getMobilphone());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();

			return "actuel";
		}
		
		this.user=userCreated;

		return "home";
	}

	/**
	 * 
	 */
	public String authentication() {

		messageErr = "";

		User userLogged = null;
		try {
			userLogged = DaoUser.authentification(user.getEmail(), user.getPassword().replaceAll("'", "''"));
			this.user=userLogged;			
			return "home";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}

		if(messageErr.equals("")) messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;

		return "actuel";
	}
	
	public User getLoggedUser() {
		return this.user;		
	}
	
	public String disconnect()
	{
		user = new User();
		
		return "disconnect";
	}
}
