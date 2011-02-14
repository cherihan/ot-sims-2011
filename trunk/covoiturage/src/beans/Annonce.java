package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.TraitementSQL;

public class Annonce {
	
	public Annonce() {
		// TODO Auto-generated constructor stub
		this.depart = "";
		this.arrive = "";
		this.date = new Date();
	}
	
	public Annonce(String depart, String arrive, Date date) {
		super();
		this.depart = depart;
		this.arrive = arrive;	
		
		DateFormat dateForme = new SimpleDateFormat("dd MMM yyyy");	
		
		this.dateString = dateForme.format(date) ;		
		
	}
	
	
	String depart;
	String arrive;
	Date date;
	String dateString;
	
	int heure;
	int minute;
	
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDateString() {
		return dateString;
	}
	
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	
	public int getHeure() {
		return heure;
	}
	
	public void setHeure(int heure) {
		this.heure = heure;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	
	
	public String insert()
	{					
		String lien = TraitementSQL.insert(depart, arrive, date);
		
		System.out.println("Test: "+ lien);
		
		return lien;
		
	}
	
	
	
}
