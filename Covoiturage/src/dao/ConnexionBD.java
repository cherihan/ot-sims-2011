package dao;

import java.sql.*;

public class ConnexionBD {
	
	public Connection          con = null;
    public Statement           sta=null;
    public ResultSet           re=null;
    public ResultSetMetaData   metaBase;
    public static String url = "jdbc:mysql://127.0.0.1:3306/sims?user=root&passwod="; // @jve:decl-index=0:
	public static String nomDriver = "com.mysql.jdbc.Driver"; // @jve:decl-index=0:
    
    public ConnexionBD(String url, String nomDriver) throws SQLException, ClassNotFoundException {
            Class.forName(nomDriver);
            con = DriverManager.getConnection(url);
            System.out.println("Overture de la connection");
            sta = con.createStatement();
        
        
     }
    
    
    public ResultSet search(String query) throws SQLException{
    	re=sta.executeQuery(query);
    	System.out.println("Element trouvee ");
    		
    	return re;
    	}
    
    public ResultSet execute(String query) throws SQLException{
    	re=sta.executeQuery(query);
    	System.out.println("Element trouvee ");
    		
    	return re;
    	}
    
    public void insert(String query) throws SQLException{
    	sta.executeUpdate(query);
    	System.out.println("Element ajouter a la base");
    	    
    }  
    
    
    public void update(String query) throws SQLException{
    	
    	sta.executeUpdate(query);
    	System.out.println("Element Modifier");
    	
    } 
    
    
    public void delete(String query) throws SQLException{
    	 
    	sta.executeUpdate(query);
    	System.out.println("Element suprimer");    	
    	 	
    }
    
    
    public void close(){
    	try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Probl�me de fermeture de la Base de donn�es");
		}
        System.out.println("Base de donn�es F�rmer");
    }
    
    public static String escape(String input) {
    	return input.replaceAll("'", "''");
    }
}