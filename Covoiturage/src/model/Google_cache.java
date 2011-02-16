package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class Google_cache {

	protected int id;
	protected String address;
	protected double latitude;
	protected double longitude;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param address
	 *            the address to set
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
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude)
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param id
	 * @param address
	 * @param latitude
	 * @param longitude
	 */
	public Google_cache(int id, String address, double latitude,
			double longitude) {
		super();
		this.id = id;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Google_cache(int id) {
		super();
		this.id = id;
	}
	
	public Google_cache(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.parseInt(sqlrow.get("gch_id"));
		this.address = sqlrow.get("gch_address");
		this.latitude = Double.parseDouble(sqlrow.get("gch_latitude"));
		this.longitude = Double.parseDouble(sqlrow.get("gch_longitude"));
	}
	
	public Google_cache(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("gch_id");
			this.address = sqlrow.getString("gch_address");
			this.latitude = (sqlrow.getDouble("gch_latitude"));
			this.longitude = (sqlrow.getDouble("gch_longitude"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public Hashtable<String, Double> getCoordsAsHash() {
		Hashtable<String, Double> result;

		result = new Hashtable<String, Double>();
		result.put("longitude", this.longitude);
		result.put("latitude", this.latitude);

		return result;
	}
	
	

}
