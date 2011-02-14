package beans;


import dao.TraitementSQL;

public class User {

	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String email, String passWord, String firstName,
			String lastName, String sexe) {
		super();
		this.email = email;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sexe = sexe;
	}




	String email;
	String passWord;	
	String confirmPassWord;
	String firstName;
	String lastName;
	String sexe;
	
	
	
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
				
		if(!passWord.equals(confirmPassWord)) return "actuel";
		
		return TraitementSQL.creatUser(email, passWord, firstName, lastName, sexe);
	}
	
	
	
	/**
	 * 
	 */
	public String authentication(){
		
			
		if(!passWord.equals(confirmPassWord)) return "actuel";
		
		User utilisateur = TraitementSQL.authentification(email, passWord);
		
		if(utilisateur != null){
			
			email = utilisateur.getEmail();
			firstName = utilisateur.getFirstName();
			lastName = utilisateur.getLastName();
			sexe = utilisateur.getSexe();
			
			return "ok";
		}
		
		return "actuel";
	}
	
	
}
