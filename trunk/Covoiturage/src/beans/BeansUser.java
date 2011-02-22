package beans;


import model.User;

import utilities.Constantes;
import utilities.ValidatorOfData;

import dao.DaoUser;

public class BeansUser {
	
	
	protected User user = new User();
	protected User userTemp = new User();
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
	 * @return the userTemp
	 */
	public User getUserTemp() {
		return userTemp;
	}

	/**
	 * @param userTemp the userTemp to set
	 */
	public void setUserTemp(User userTemp) {
		this.userTemp = userTemp;
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
		if (!ValidatorOfData.validateEMail(userTemp.getEmail())) {
			messageErr = Constantes.EMAIL_FORM_NOT_CORRECT;
			userTemp.setEmail("");
			return "actuel";
		}

		if (!ValidatorOfData.validatePhone(userTemp.getMobilphone())) {
			messageErr = Constantes.MOBILE_NUMBER_FORM_NOT_CORRECT;
			userTemp.setMobilphone("");
			return "actuel";
		}
		
		if (!ValidatorOfData.validatePassWord(userTemp.getPassword())) {
			messageErr = Constantes.PASSWORD_FORM_NOT_CORRECT;
			userTemp.setPassword("");
			return "actuel";
		}
		
		// /// TODO
		
		if (!ValidatorOfData.validateData(userTemp.getFirstname())) {
			messageErr = Constantes.DATA_FORM_NOT_CORRECT;
			userTemp.setFirstname("");
			return "actuel";
		}

		// ///

		if (userTemp.getEmail().equals("")) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;			
			return "actuel";

		} else if (!userTemp.getPassword().equals(this.getConfirmPassword()) || userTemp.getPassword().equals("")
				|| this.getConfirmPassword().equals("") || userTemp.getEmail().equals("")) {
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}

		try {
			userCreated = DaoUser.createUser(userTemp.getEmail(), userTemp.getPassword().replaceAll("'", "''"), userTemp.getFirstname(), userTemp.getLastname(), userTemp.getMobilphone());
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
			userLogged = DaoUser.authentification(userTemp.getEmail(), userTemp.getPassword().replaceAll("'", "''"));
			this.user=userLogged;			
			return "home";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}

		if(messageErr.equals("")) messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;
		return "actuel";
	}
	
	
	public String toEdit() {
		userTemp = DaoUser.getUser(this.user.getId());
		confirmPassword = new String();
		messageErr = "";
		System.out.println(userTemp.getPassword());
		System.out.println(this.getConfirmPassword());
		return "edit";
	}


public String changeProfile() {
		messageErr = "";
		if (!ValidatorOfData.validateEMail(userTemp.getEmail())) {
			messageErr = Constantes.EMAIL_FORM_NOT_CORRECT;
			userTemp.setEmail(null);
			return "actuel";
		}
		// /// TODO
		if (!ValidatorOfData.validateData(userTemp.getFirstname())) {
			messageErr = Constantes.DATA_FORM_NOT_CORRECT;
			userTemp.setEmail(null);
			return "actuel";
		}
		// ///
		if (userTemp.getEmail().equals("")) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			userTemp.setEmail(null);
			return "actuel";
		}
//		 else if (!userTemp.getPassword().equals(this.getConfirmPassword())
//				|| userTemp.getPassword().equals("")
//				|| this.getConfirmPassword().equals("")) {
//			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
//			return "actuel";
//		}
		System.out.println("edit connexion");
		try {
			DaoUser.changeProfile(userTemp);
			System.out.println("userTemp password : " + userTemp.getPassword());
			System.out.println("confirmpassword : " + this.getConfirmPassword());
			this.user = DaoUser.getUser(userTemp.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}
		System.out.println("avant return");
		return "profile";
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
