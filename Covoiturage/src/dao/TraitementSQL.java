package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import utilities.Constantes;
import beans.Annonce;
import beans.BeansUser;
import model.User;

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
 * @param genre
 * @return
 * @throws Exception 
 */
public static User creatUser(String email, String password) throws Exception
{
	
	con = null;
	String messageErr= null;
	ResultSet res;
	
try{
		
		con = new ConnexionBD(url, nomDriver );
		
		String query = "call user_create_short('" + email + "', '" + password + "')" ;
		
		try{
			res = con.execute(query);
		}
		catch(MySQLIntegrityConstraintViolationException ex)
		{
			messageErr= Constantes.USER_ALREADY_SAVED;			
			System.err.println(messageErr + " : " + ex );
			throw new Exception(messageErr);
		}		
	}catch (ClassNotFoundException ex) {        
        messageErr = Constantes.CLASS_DB_NOT_FOUND;
        System.err.println(messageErr + " : " + ex );
        throw new Exception(messageErr);  	
    }
    catch (SQLException ex) {
    	messageErr = Constantes.PROBLEME_CONNECTION_DB;
        System.err.println(messageErr + " : " + ex );
        throw new Exception(messageErr);
    }	
	
	if(con != null) con.close();	
	
	User utilisateur = new User(res);
	
	return utilisateur;
	
}

/**
 * cette fonction retourne une list qui contient (messageErr, email, firstName, lastName, genre)
 * @param email
 * @param passWord
 * @return
 * @throws Exception 
 */
public static BeansUser authentification(String email, String passWord) throws Exception
{
	con = null;	
	
	String messageErr = null;
	String firstName = null;
	String lastName = null;
	String genre = null;
	
	BeansUser utilisateur = null;
	
	
	try{
						
		con = new ConnexionBD(url, nomDriver );
		
		String query = "call user_get_user_by_email('" + email + "', '" + passWord +"')";
		ResultSet curseur = con.search(query);
		
		while(curseur.next())
		{					
			  firstName =curseur.getString("usr_firstname");
			  lastName =curseur.getString("usr_lastname");
			  genre =curseur.getString("usr_genre");
			  			
		}
		
	}catch (ClassNotFoundException ex) {        
        messageErr = Constantes.CLASS_DB_NOT_FOUND;
        System.err.println(messageErr + " : " + ex );        
        throw new Exception(messageErr);
    }
    catch (SQLException ex) {
    	messageErr = Constantes.PROBLEME_CONNECTION_DB;    	        
        System.err.println(messageErr + " : " + ex );        
        throw new Exception(messageErr);
    }catch(Exception e){		
		messageErr = Constantes.OTHER_PROBLEME_IN_CONNECTION_DB;		
		System.err.println(messageErr + " : " + e );		
		throw new Exception(messageErr);
		
	}
	
       
	if(con != null){		
		utilisateur = new BeansUser(email, passWord, firstName, lastName, genre);		
		con.close();		
	}
	
	return utilisateur;
	
}


public static void main(String[] args) {
	
//	User test = TraitementSQL.authentification("othman.bentria@gmail.com", "root");
//	
//	System.out.println(test.getFirstName());
	
	
}

	
}
