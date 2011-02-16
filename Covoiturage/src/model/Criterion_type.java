package model;


import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Criterion_type {



	protected int id;
	protected String label;
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
	/**
	 * @param id
	 * @param label
	 */
	public Criterion_type(int id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	

}
