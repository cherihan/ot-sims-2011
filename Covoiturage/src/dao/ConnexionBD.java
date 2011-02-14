package dao;

import java.sql.*;

public class ConnexionBD {
	
	public Connection          con = null;
    public Statement           sta=null;
    public ResultSet           re=null;
    public ResultSetMetaData   metaBase;
    
    public ConnexionBD(String url, String nomDriver) {
        try {
            Class.forName(nomDriver);
            con = DriverManager.getConnection(url);
            System.out.println("Overture de la connection");
            sta = con.createStatement();
        }
        catch (ClassNotFoundException ex) {
            System.err.println("Ne peut pas trouver les classes du conducteur de la base de donn�es.");
            System.err.println(ex);
        }
        catch (SQLException ex) {
            System.err.println("pas de connection au base de de donnee.");
            System.err.println(ex);
        }
     }
    
    public ResultSet search(String query){
    	try { 
    	re=sta.executeQuery(query);
    	System.out.println("Element trouvee ");
    		}
    	catch (SQLException ex) {
            System.err.println(ex);
         }
    	return re;
    	}
    
    public void insert(String query){
    	try { 
    	sta.executeUpdate(query);
    	System.out.println("Element ajouter a la base");
    		}
    	catch (SQLException ex) {
            System.err.println(ex);
    	}      
    }   
    public void update(String query){
    	try { 
    	sta.executeUpdate(query);
    	System.out.println("Element Modifier");
    	}
    	catch (SQLException ex){
        System.err.println(ex);
    	}  	
    } 
    
    
    public void delete(String query){
    	try { 
    	sta.executeUpdate(query);
    	System.out.println("Element suprimer");    	
    	}
    	catch (SQLException ex) {
         System.err.println(ex);
    	}  	
    }
    
    
    public void close(){
    	try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Probl�me de fermeture de la Base de donn�es");
		}
        System.out.println("Base de donn�es F�rmer");
    }
}