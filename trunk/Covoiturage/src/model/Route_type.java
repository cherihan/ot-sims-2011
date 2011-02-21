package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class Route_type {



	protected int id;
	protected String label;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Route_type(int id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	
	public Route_type(int id) {
		super();
		this.id = id;
	}
	
	public Route_type(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.parseInt(sqlrow.get("rtp_id"));
		this.label = sqlrow.get("rtp_label");
	}
	
	public Route_type(ResultSet  sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("rtp_id");
			this.label = sqlrow.getString("rtp_label");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	


}
