package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;


public class Criterion_type {



	protected int id;
	protected String label;
	
	
	/**
	 * @param id
	 * @param label
	 */
	public Criterion_type(int id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	
	/**
	 * @param id
	 */
	public Criterion_type(int id) {
		super();
		this.id = id;
	}
	
	public Criterion_type(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.valueOf(sqlrow.get("ctt_id"));
		this.label = sqlrow.get("ctt_label");
	}
	
	public Criterion_type(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("ctt_id");
			this.label = sqlrow.getString("ctt_label");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	

}
