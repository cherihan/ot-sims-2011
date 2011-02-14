package dao;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

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
			System.out.println("Test DateTime" + formatteur);
			
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
public static String creatUser(String email, String passWord, String firstName, String lastName, String sexe)
{
	String lien="ok";
	con = null;
	
try{
		
		con = new ConnexionBD(url, nomDriver );
		
		String query = "call user_create('" + email + "', '" + passWord +"', '" + firstName	+ "', '" + lastName + "', '" + sexe + "')";
		con.insert(query);
		
	}catch(Exception e){
		System.err.println("Exception: " + e.getMessage());
		System.out.println("Connection echouer.............");
		lien = "actuel"; 
	}
	
	con.close();	
	
	return lien;
	
}

public static User authentification(String email, String passWord)
{
	con = null;	
	User utilisateur = null;
	
	try{
						
		con = new ConnexionBD(url, nomDriver );
		
		String query = "call user_get_user_by_email('" + email + "', '" + passWord +"')";
		ResultSet curseur = con.search(query);
		
		while(curseur.next())
		{					
			  String firstName =curseur.getString(2);
			  String lastName =curseur.getString(3);
			  String sexe =curseur.getString(7);
			  
			  utilisateur = new User(email, passWord, firstName, lastName, sexe);			
		}
		
	}catch(Exception e){
		System.err.println("Exception: " + e.getMessage());
		System.out.println("Connection echouer.............");		
	}
	
	con.close();	
	
	return utilisateur;
	
}


public static void main(String[] args) {
	
	User test = TraitementSQL.authentification("othman.bentria@gmail.com", "root");
	
	System.out.println(test.getFirstName());
	
	
}

	
}
