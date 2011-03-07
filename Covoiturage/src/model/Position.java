package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class Position {

	protected int id;
	protected String address;
	protected double latitude;
	protected double longitude;

	/**
	 * @param id
	 * @param address
	 * @param latitude
	 * @param longitude
	 */
	public Position(int id, String address, double latitude, double longitude) {
		super();
		this.id = id;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Position(ResultSet sqlrow) {
		super();
		try{
			this.id = sqlrow.getInt("pos_id");
			this.address = sqlrow.getString("pos_address");
			this.latitude = sqlrow.getDouble("pos_latitude");
			this.longitude = sqlrow.getDouble("pos_longitude");
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public Position(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.valueOf(sqlrow.get("pos_id"));
		this.address = sqlrow.get("pos_address");
		this.latitude = Double.valueOf(sqlrow.get("pos_latitude"));
		this.longitude = Double.valueOf(sqlrow.get("pos_longitude"));
	}
	
	public Position(ResultSet sqlrow, String prefix) {
		super();
		try{
			this.id = sqlrow.getInt(prefix+"pos_id");
			this.address = sqlrow.getString(prefix+"pos_address");
			this.latitude = sqlrow.getDouble(prefix+"pos_latitude");
			this.longitude = sqlrow.getDouble(prefix+"pos_longitude");
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public Hashtable<String, Double> getCoords() {
		Hashtable<String, Double> hs = new Hashtable<String, Double>();
		hs.put("latitude", this.getLatitude());
		hs.put("longitude", this.getLongitude());
		return hs;
	}
	


}
