package utilities;

import javax.faces.context.FacesContext;

import model.User;

public class FacesUtil {

	/**
	 * Cette fonction sert pour recuperer les beans stocket dans la session. le
	 * parametre name et de la forme "#{bean.attribut}"
	 * 
	 * @param name
	 * @return "un objet selon le type de l'attribut de bean"
	 * 
	 *         Exemple :
	 * 
	 *         User utilisateur = (User)
	 *         FacesUtil.getParameter("#{beansUser.user}");
	 * 
	 */

	public static Object getParameter(String name) {
		FacesContext fc = FacesContext.getCurrentInstance();

		return (Object) fc.getApplication().getExpressionFactory()
				.createValueExpression(fc.getELContext(), name, Object.class)
				.getValue(fc.getELContext());
	}

	public static User getUser() {
		FacesContext fc = FacesContext.getCurrentInstance();

		return (User) fc
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(fc.getELContext(), "#{beansUser.user}",
						User.class).getValue(fc.getELContext());
	}

	public static void setUser(User usr) {
		FacesContext fc = FacesContext.getCurrentInstance();

		fc.getApplication()
				.getExpressionFactory()
				.createValueExpression(fc.getELContext(), "#{beansUser.user}",
						User.class).setValue(fc.getELContext(), usr);
	}

}
