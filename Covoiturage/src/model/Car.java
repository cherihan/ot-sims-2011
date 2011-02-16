package model;


import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Car {



	protected int id;
	protected String name;
	protected int car_owner;
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
	public int getCar_owner() {
		return car_owner;
	}
	/**
	 * @param car_owner the car_owner to set
	 */
	public void setCar_owner(int car_owner) {
		this.car_owner = car_owner;
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
	public Car(int id, String name, int car_owner, int seat) {
		super();
		this.id = id;
		this.name = name;
		this.car_owner = car_owner;
		this.seat = seat;
	}


}
