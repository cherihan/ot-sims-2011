package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import beans.Annonce;

public class TraitementSQL {

	public static String insert(String depart, String arrive, Date date)
	{
		Connection con = null;
        String lien= "actuel";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sims", "root", "");
		
			if (!con.isClosed())
			{
				
				Statement sta=null;
									
				sta = con.createStatement();
				
				
				//DateFormat dateForme = new SimpleDateFormat("yyyy-MM-dd");
				
				//String formatteur = dateForme.format(date);
				long formatteur =  date.getTime();
				
				System.out.println("Test DateTime" + formatteur);
				
				sta.executeUpdate("INSERT INTO chemin (depart, arrivee, date) values ('"+ depart +"', '" + arrive +"', '" + formatteur +"');");
				
									
				lien= "list";
				
			}
			else{
//					output = "Connection echouer...";
					lien= "actuel";
			}
				
			
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
//			output = "Connection echouer.............";
			lien= "actuel";
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
					
		
		return lien;

	}
	
	
	


public static ArrayList<Annonce> loadData()
{
	
	ArrayList<Annonce> listofAnnonces = new ArrayList<Annonce>();
	Connection con = null;

	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sims", "root", "");
		
	
	if (!con.isClosed())
	{
		
		Statement sta=null;
		ResultSet re=null;
		
		sta = con.createStatement();
		re=sta.executeQuery("SELECT * FROM chemin");
		
							
		while(re.next())
		{					
			  String depart =re.getString(2);
			  String arrive =re.getString(3);
			  
			  System.out.println("arrive :" +arrive);
			  
			  Date date  =new Date(re.getLong(4));
			  
			  
			  System.out.println("Daaaate :" +date);
			  			  
			  
//			DateFormat dateForme = new SimpleDateFormat("yyyy-MM-dd");
//				
//				String formatteur = dateForme.format(date);
			  
			  listofAnnonces.add(new Annonce(depart, arrive, date));
			  
			  System.out.println(depart + " # " + arrive + " # " + date);
			
		}
		
		
		
	}

	return listofAnnonces;
		
	
} catch (Exception e) {
	System.err.println("Exception!: " + e.getMessage());
//	output = "Connection echouer.............";
//	lien= "actuel";
} finally {
	try {
		if (con != null)
			con.close();
	} catch (SQLException e) {
	}
}
return listofAnnonces;
}


public static void main(String[] args) {
	
	//TraitementSQL.loadData();
	
	//Date date = new Date()
	
	 
	
	
	
}

	
}
