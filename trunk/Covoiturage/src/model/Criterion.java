package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;



public class Criterion {



	protected int id;
	protected int type;
	protected int root_criterion;
	protected int order;
	protected String label;
	
	
	/**
	 * @param id
	 * @param type
	 * @param root_criterion
	 * @param order
	 * @param label
	 */
	public Criterion(int id, int type, int root_criterion, int order,
			String label) {
		super();
		this.id = id;
		this.type = type;
		this.root_criterion = root_criterion;
		this.order = order;
		this.label = label;
	}
	
	/**
	 * @param id
	 */
	public Criterion(int id) {
		super();
		this.id = id;
	}
	
	public Criterion(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.valueOf(sqlrow.get("crt_id"));
		this.type = Integer.valueOf(sqlrow.get("crt_type"));
		this.root_criterion = Integer.valueOf(sqlrow.get("crt_root_criterion"));
		this.order = Integer.valueOf(sqlrow.get("crt_order"));
		this.label = sqlrow.get("crt_label");
	}
	
	public Criterion(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("crt_id");
			this.type = sqlrow.getInt("crt_type");
			this.root_criterion = sqlrow.getInt("crt_root_criterion");
			this.order = sqlrow.getInt("crt_order");
			this.label = sqlrow.getString("crt_label");
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
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the root_criterion
	 */
	public int getRoot_criterion() {
		return root_criterion;
	}
	/**
	 * @param root_criterion the root_criterion to set
	 */
	public void setRoot_criterion(int root_criterion) {
		this.root_criterion = root_criterion;
	}
	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
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
