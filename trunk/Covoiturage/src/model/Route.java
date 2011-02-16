package model;



import java.util.Date;


public class Route {



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
	 * @param id
	 * @param type
	 * @param position_begin
	 * @param position_end
	 * @param date_begin
	 * @param date_end
	 * @param comment
	 * @param route_owner
	 * @param seat
	 * @param car
	 */
	public Route(int id, int type, int position_begin, int position_end,
			Date date_begin, Date date_end, String comment, int route_owner,
			int seat, int car) {
		super();
		this.id = id;
		this.type = type;
		this.position_begin = position_begin;
		this.position_end = position_end;
		this.date_begin = date_begin;
		this.date_end = date_end;
		this.comment = comment;
		this.route_owner = route_owner;
		this.seat = seat;
		this.car = car;
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
	 * @return the position_begin
	 */
	public int getPosition_begin() {
		return position_begin;
	}
	/**
	 * @param position_begin the position_begin to set
	 */
	public void setPosition_begin(int position_begin) {
		this.position_begin = position_begin;
	}
	/**
	 * @return the position_end
	 */
	public int getPosition_end() {
		return position_end;
	}
	/**
	 * @param position_end the position_end to set
	 */
	public void setPosition_end(int position_end) {
		this.position_end = position_end;
	}
	/**
	 * @return the date_begin
	 */
	public Date getDate_begin() {
		return date_begin;
	}
	/**
	 * @param date_begin the date_begin to set
	 */
	public void setDate_begin(Date date_begin) {
		this.date_begin = date_begin;
	}
	/**
	 * @return the date_end
	 */
	public Date getDate_end() {
		return date_end;
	}
	/**
	 * @param date_end the date_end to set
	 */
	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the route_owner
	 */
	public int getRoute_owner() {
		return route_owner;
	}
	/**
	 * @param route_owner the route_owner to set
	 */
	public void setRoute_owner(int route_owner) {
		this.route_owner = route_owner;
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
	 * @return the car
	 */
	public int getCar() {
		return car;
	}
	/**
	 * @param car the car to set
	 */
	public void setCar(int car) {
		this.car = car;
	}
	protected int id;
	protected int type;
	protected int position_begin;
	protected int position_end;
	protected Date date_begin;
	protected Date date_end;
	protected String comment;
	protected int route_owner;
	protected int seat;
	protected int car;

}
