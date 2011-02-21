package utilities;

import javax.faces.context.FacesContext;

import model.User;

public class FacesUtil {

    /**
     * Cette fonction sert pour recuperer les beans stocket dans la session.
     * le parametre name et de la forme "#{bean.attribut}" 
     * @param name
     * @return "un objet selon le type de l'attribut de bean" 
     * 
     * Exemple :
     *  
     * User utilisateur = (User) FacesUtil.getParameter("#{beansUser.user}");
     * 
     */
	@SuppressWarnings("deprecation")
	public static Object getParameter(String name) {
    	FacesContext fc = FacesContext.getCurrentInstance();   	
    	   	
    	return (Object) fc.getApplication().createValueBinding(name).getValue(fc);
    }
	
	
	@SuppressWarnings("deprecation")
	public static User getUser() {
    	FacesContext fc = FacesContext.getCurrentInstance();   	
    	   	
    	return (User) fc.getApplication().createValueBinding("#{beansUser.user}").getValue(fc);
    }
	
	@SuppressWarnings("deprecation")
	public static void setUser(User usr) {
    	FacesContext fc = FacesContext.getCurrentInstance();   	
    	   	
    	fc.getApplication().createValueBinding("#{beansUser.user}").setValue(fc, usr);
    }
    
     
}
