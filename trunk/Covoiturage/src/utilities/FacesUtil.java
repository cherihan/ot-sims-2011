package utilities;

import javax.faces.context.FacesContext;

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
    public static Object getParameter(String name) {
    	FacesContext fc = FacesContext.getCurrentInstance();   	
    	   	
    	return (Object) fc.getApplication().createValueBinding(name).getValue(fc);
    }
    
     
}
