package beans;


import java.util.ArrayList;

import utilities.Constantes;

import dao.TraitementSQL;

public class User {

	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String email, String passWord, String firstName,
			String lastName, String sexe, String messageErr) {
		super();
		this.email = email;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sexe = sexe;
		this.messageErr = messageErr;
	}




	String email;
	String passWord;	
	String confirmPassWord;
	String firstName;
	String lastName;
	String sexe;
		
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

	
	
	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	
	/**
	 * 
	 */
	public String creatUser()
	{
				
		if(email.equals("") || firstName.equals("") || lastName.equals("") || sexe.equals("") ){
			messageErr = Constantes.DATAS_NOT_FILL_IN;
			return "actuel";
			
		}else if(!passWord.equals(confirmPassWord) || passWord.equals("") || confirmPassWord.equals("") || email.equals("") ){
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			return "actuel";
		}
		
		ArrayList<String> result = TraitementSQL.creatUser(email, confirmPassWord, firstName, lastName, sexe);		
		
		String lien= result.get(0);	
		messageErr = result.get(1);	
		
		if(lien.equals("ok")) messageErr="";
		
		return lien;
	}
	
	
	
	/**
	 * 
	 */
	public String authentication(){		
			
			
		if(!passWord.equals(confirmPassWord) || passWord.equals("") || confirmPassWord.equals("") || email.equals("") ){
			
			messageErr = Constantes.PASSWORD_NOT_IDENTIQUE_OR_NULL;
			
			return "actuel";
		}		
		
		ArrayList<String> result = TraitementSQL.authentification(email, passWord);
					
		if(result.size()==1) messageErr = result.get(0); 
		
		if(result.size() > 1){
			
			email = result.get(1);
			firstName = result.get(2);
			lastName = result.get(3);
			sexe = result.get(4);					
			messageErr = "";
			return "ok";
		}		
		
		return "actuel";
	}
	
	
}
