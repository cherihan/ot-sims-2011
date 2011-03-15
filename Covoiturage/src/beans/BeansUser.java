package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import model.Route;
import model.User;

import utilities.Constantes;
import utilities.FacesUtil;
import utilities.ValidatorOfData;

import dao.DaoUser;

public class BeansUser {

	protected User user;
	protected User userTemp;
	protected String messageErr;
	protected String confirmPassword;
	protected String birthdateString;
	
	protected String trancheAge;

	public BeansUser() {
		user = new User();
		userTemp = new User(user);
		birthdateString = "";
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
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
	 * @param userTemp
	 *            the userTemp to set
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
	 * @param messageErr
	 *            the messageErr to set
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
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the birthdateString
	 */
	public String getBirthdateString() {
		return birthdateString;
	}

	/**
	 * @param birthdateString
	 *            the birthdateString to set
	 */
	public void setBirthdateString(String birthdateString) {
		this.birthdateString = birthdateString;
	}

	public String toLogin() {
		messageErr = "";
		return "login";
	}

	public String toCreatAccount() {
		messageErr = "";
		return "create_account";
	}

	public String toEdit() {
		userTemp = new User(user);
		confirmPassword = new String();
		messageErr = "";
		return "edit";
	}
	
	public String toIndex() {
		messageErr = "";
		List<Route> list = FacesUtil.getRouteList();
		System.out.println(user.getAllRouteOfUser().size());
		list.clear();
		list.addAll(user.getAllRouteOfUser());

		return "index";
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

		} else if (!userTemp.getPassword().equals(this.getConfirmPassword())
				|| userTemp.getPassword().equals("")
				|| this.getConfirmPassword().equals("")
				|| userTemp.getEmail().equals("")) {
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}

		try {
			userCreated = DaoUser.createUser(userTemp.getEmail(), userTemp
					.getPassword(), userTemp
					.getFirstname(), userTemp.getLastname(), userTemp
					.getMobilphone());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
			return "actuel";
		}

		this.user = userCreated;

		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		birthdateString = dateFormat.format(user.getBirthdate());

		return "home";
	}

	/**
	 * 
	 */
	public String authentication() {

		messageErr = "";

		User userLogged = null;
		try {
			userLogged = DaoUser.authentification(userTemp.getEmail(), userTemp
					.getPassword().replaceAll("'", "''"));
			this.user = userLogged;

			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			birthdateString = dateFormat.format(user.getBirthdate());

			return "home";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
		}

		if (messageErr.equals(""))
			messageErr = Constantes.PASSWORD_OR_USER_NOT_CORRECT;
		return "actuel";
	}

	/**
	 * 
	 * @return
	 */
	public String changeProfile() {

		messageErr = "";
		User userUpdate = null;

		System.out.println("sd: " + userTemp.getPassword().length());

		if (!ValidatorOfData.validateEMail(userTemp.getEmail())) {
			messageErr = Constantes.EMAIL_FORM_NOT_CORRECT;
			userTemp.setEmail(null);
			return "actuel";
		}
		if (!ValidatorOfData.validatePhone(userTemp.getMobilphone())) {
			messageErr = Constantes.MOBILE_NUMBER_FORM_NOT_CORRECT;
			userTemp.setMobilphone("");
			return "actuel";
		}
		if (!ValidatorOfData.validateData(userTemp.getFirstname())) {
			messageErr = Constantes.DATA_FORM_NOT_CORRECT;
			userTemp.setEmail(null);
			return "actuel";
		}
		
		if ( ! userTemp.getPassword().equals("") && !ValidatorOfData.validatePassWord(userTemp.getPassword())) {
			messageErr = Constantes.PASSWORD_FORM_NOT_CORRECT;
			userTemp.setPassword("");
			return "actuel";
		}
		
		// ///
		if (userTemp.getEmail().equals("")) {
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			userTemp.setEmail(null);
			return "actuel";
		} else if (userTemp.getPassword().length() != 0
				&& !userTemp.getPassword().equals(this.getConfirmPassword())) {
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}
		try {

			userUpdate = DaoUser.changeProfile(userTemp);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			messageErr = e.getMessage();
			return "actuel";
		}
		user = userUpdate;

		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		birthdateString = dateFormat.format(user.getBirthdate());

		return "profile";
	}

	/**
	 * 
	 * @return
	 */
	public User getLoggedUser() {
		return this.user;
	}

	/**
	 * 
	 * @return
	 */
	public String disconnect() {
		user = new User();

		return "disconnect";
	}
	
	public String getTrancheAge()
	{
		int a =  user.getBirthdate().getYear();
		System.out.println(a);
		switch (a) {
		case 92:
			return "Mineur(e)";
			
		case 80:
			return "Jeune";
			
		case 60:
			return "Adulte";
			
		case 40:
			return "Senior";
			
		case 20:
			return "Senior++";

		default:
			return "Pas encore défini ";
		}
	}
	
	public void setTrancheAge(String trancheAge) {
		this.trancheAge = trancheAge;
	}	


	// public static void main(String[] args) {
	//
	// Date date = new Timestamp(1999999999);
	// System.out.println(date.toString());
	// }
}
