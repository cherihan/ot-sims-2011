package beans;

public class User {

	String name;
	String passWord;	
	String confirmPassWord;
	
	public String getName() {
		return name;
	}		
	
	public void setName(String name) {
		this.name = name;
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
	
	public String authentication(){
		
		return "ok";
	}
	
	
}
