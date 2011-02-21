package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;



public class Car {



	protected int id;
	protected String name;
	protected int owner;
	protected int seat;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the car_owner
	 */
	public int getOwner() {
		return owner;
	}
	/**
	 * @param car_owner the car_owner to set
	 */
	public void setOwner(int owner) {
		this.owner = owner;
	}
	/**
	 * @return the seat
	 */
	public int getSeat() {
		return seat;
	}
	/**
	 * @param seat the seat to set
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}
	/**
	 * @param id
	 * @param name
	 * @param car_owner
	 * @param seat
	 */
	public Car(int id, String name, int owner, int seat) {
		super();
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.seat = seat;
	}
	
	/**
	 * @param id
	 */
	public Car(int id) {
		super();
		this.id = id;
	}
	
	public Car(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.valueOf(sqlrow.get("car_id"));
		this.name = sqlrow.get("car_name");
		this.owner = Integer.valueOf(sqlrow.get("car_owner"));
		this.seat = Integer.valueOf(sqlrow.get("car_seat"));
	}
	
	public Car(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("car_id");
			this.name = sqlrow.getString("car_name");
			this.owner = sqlrow.getInt("car_owner");
			this.seat = sqlrow.getInt("car_seat");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


}
