package beans;

import model.User;
import utilities.FacesUtil;

public class HomeCtrl {
	
	Boolean userConnected;
	
	Boolean userNotConnected;
	
	public Boolean getUserConnected() {
		return FacesUtil.getUserConnected();
	}
	
	
	public void setUserConnected(Boolean userConnected) {
		this.userConnected = userConnected;
	}
	
	
	public Boolean getUserNotConnected() {	
		
		User utilisateur = FacesUtil.getUser();		
				
		if(utilisateur == null || utilisateur.getEmail() == null)return true;
				
		return false;
	}
	
	


}
