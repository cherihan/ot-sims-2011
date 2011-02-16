package model;


import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Criterion {



	protected int id;
	protected int type;
	protected int root_criterion;
	protected int order;
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

	
	
}
