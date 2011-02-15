package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import utilities.Constantes;

import com.mysql.jdbc.Messages;

import beans.Annonce;
import beans.User;

public class TraitementSQL {
	
	public static ConnexionBD con;	
	public static String url="jdbc:mysql://127.0.0.1:3306/sims?user=root&passwod=";  //  @jve:decl-index=0:
	public static String nomDriver="com.mysql.jdbc.Driver";  //  @jve:decl-index=0:

	
	/**
	 * 
	 * @param depart
	 * @param arrive
	 * @param date
	 * @return
	 */
	public static String insert(String depart, String arrive, Date date)
	{
		con = null;
        String lien= "list";
        
		try {			
			con=new ConnexionBD(url,nomDriver);		

			long formatteur =  date.getTime();			
						
			String query="INSERT INTO chemin (depart, arrivee, date) values ('"+ depart +"', '" + arrive +"', '" + formatteur +"');";
			con.insert(query);
			
		}catch(Exception e)
		{			
			System.err.println("Exception: " + e.getMessage());
			System.out.println("Connection echouer.............");
			lien= "actuel";	
				
		}
		
		con.close();		
		return lien;

	}
	
/**
 * 
 * @return
 */
public static ArrayList<Annonce> loadData()
{
	
	ArrayList<Annonce> listofAnnonces = new ArrayList<Annonce>();
	con = null;

	try {

		con=new ConnexionBD(url,nomDriver);	
		String query = "SELECT * FROM chemin";
		
		ResultSet curseur = con.search(query);
		
					
		while(curseur.next())
		{					
			  String depart =curseur.getString(2);
			  String arrive =curseur.getString(3);
			  
			  System.out.println("arrive :" +arrive);
			  
			  Date date  =new Date(curseur.getLong(4));
			  
			  
			  System.out.println("Daaaate :" +date);

			  
			  listofAnnonces.add(new Annonce(depart, arrive, date));
			  
			  System.out.println(depart + " # " + arrive + " # " + date);
			
		}
		
	}catch(Exception e) {
		System.err.println("Exception!: " + e.getMessage());
		System.out.println("Connection echouer.............");
	}
	
	con.close();
	return listofAnnonces;
		
}

/**
 *  
 * @param email
 * @param passWord
 * @param firstName
 * @param lastName
 * @param sexe
 * @return
 */
public static ArrayList<String> creatUser(String email, String passWord, String firstName, String lastName, String sexe)
{
	String lien="ok";
	con = null;
	String messageErr= null;
	
	ArrayList<String> result = new ArrayList<String>();
	
try{
		
		con = new ConnexionBD(url, nomDriver );
		
		String query = "call user_create('" + email + "', '" + passWord +"', '" + firstName	+ "', '" + lastName + "', '" + sexe + "')";
		
		try{
			con.insert(query);
		}
		catch(SQLException ex)
		{
			messageErr= Constantes.USER_ALREADY_SAVED;			
			lien="acteul";
			System.err.println(messageErr + " : " + ex );
			result.add(lien);
	    	result.add(messageErr);
	    	return result;  
		}		
	}catch (ClassNotFoundException ex) {        
        messageErr = Constantes.CLASS_DB_NOT_FOUNDED;         
        lien="acteul";
        System.err.println(messageErr + " : " + ex );
    	result.add(lien);
    	result.add(messageErr);
    	return result;    	
    }
    catch (SQLException ex) {
    	messageErr = Constantes.PROBLEME_CONNECTION_DB;    	        
        lien="acteul";
        System.err.println(messageErr + " : " + ex );
    	result.add(lien);
    	result.add(messageErr);
    	return result;
    }catch(Exception e){		
		messageErr = Constantes.OTHER_PROBLEME_IN_CONNECTION_DB;
		lien = "actuel";
		System.err.println(messageErr + " : " + e );
		result.add(lien);
		result.add(messageErr);
		return result;
	}
	
	
	if(con != null) con.close();
	
	result.add(lien);
	result.add("");
	
	return result;
	
}

/**
 * cette fonction retourne une list qui contient (messageErr, email, firstName, lastName, sexe)
 * @param email
 * @param passWord
 * @return
 */
public static ArrayList<String> authentification(String email, String passWord)
{
	con = null;	
	
	String messageErr = null;
	String firstName = null;
	String lastName = null;
	String sexe = null;
	
	ArrayList<String> result = new ArrayList<String>();;
	
	
	try{
						
		con = new ConnexionBD(url, nomDriver );
		
		String query = "call user_get_user_by_email('" + email + "', '" + passWord +"')";
		ResultSet curseur = con.search(query);
		
		while(curseur.next())
		{					
			  firstName =curseur.getString(2);
			  lastName =curseur.getString(3);
			  sexe =curseur.getString(7);
			  			
		}
		
	}catch (ClassNotFoundException ex) {        
        messageErr = Constantes.CLASS_DB_NOT_FOUNDED;
        System.err.println(messageErr + " : " + ex );
        result.add(messageErr);
        return result;
    }
    catch (SQLException ex) {
    	messageErr = Constantes.PROBLEME_CONNECTION_DB;    	        
        System.err.println(messageErr + " : " + ex );
        result.add(messageErr);
        return result;
    }catch(Exception e){		
		messageErr = Constantes.OTHER_PROBLEME_IN_CONNECTION_DB;		
		System.err.println(messageErr + " : " + e );
		result.add(messageErr);
		return result;
	}
	
	
    
	if(con != null){		
						
		if(firstName != null || lastName != null || sexe != null ){
			result.add("");
			result.add(email);		
			result.add(firstName);
			result.add(lastName);
			result.add(sexe);
		}
		else{
			result.add(Constantes.PASSWORD_OR_USER_NOT_GOOD);
		}
				
		
		con.close();		
	}
	
	return result;
	
}


public static void main(String[] args) {
	
//	User test = TraitementSQL.authentification("othman.bentria@gmail.com", "root");
//	
//	System.out.println(test.getFirstName());
	
	
}

	
}
