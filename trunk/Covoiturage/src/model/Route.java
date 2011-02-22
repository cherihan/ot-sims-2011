package model;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

import utilities.DateUtils;

import dao.DaoCar;
import dao.DaoPosition;
import dao.DaoRoute;
import dao.DaoUser;


public class Route {

	protected int id;
	protected int type;
	protected int position_begin;
	protected int position_end;
	protected Date date_begin;
	protected Date date_end;
	protected String comment;
	protected int owner;
	protected int seat;
	protected int car;
	
	protected Position position_beginObj;
	protected Position position_endObj;
	protected User ownerObj;
	protected Car carObj;
	
	protected Hashtable<Integer, Passager> passagers=null;
	
	/**
	 * @param id
	 * @param type
	 * @param position_begin
	 * @param position_end
	 * @param date_begin
	 * @param date_end
	 * @param comment
	 * @param owner
	 * @param seat
	 * @param car
	 */
	public Route(int id, int type, int position_begin, int position_end,
			Date date_begin, Date date_end, String comment, int owner,
			int seat, int car) {
		super();
		this.id = id;
		this.type = type;
		this.position_begin = position_begin;
		this.position_end = position_end;
		this.date_begin = date_begin;
		this.date_end = date_end;
		this.comment = comment;
		this.owner = owner;
		this.seat = seat;
		this.car = car;
	}
	
	public Route(int id) {
		super();
		this.id = id;
	}
	
	public Route(){
		super();
	}

	public Route(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.parseInt(sqlrow.get("rte_id"));
		this.type = Integer.parseInt(sqlrow.get("rte_type"));
		this.position_begin = Integer.parseInt(sqlrow.get("rte_position_begin"));
		this.position_end = Integer.parseInt(sqlrow.get("rte_position_end"));
		//this.date_begin = sqlrow.get("rte_date_begin");
		//this.date_end = sqlrow.get("rte_date_end");
		this.comment = sqlrow.get("rte_comment");
		this.owner = Integer.parseInt(sqlrow.get("rte_owner"));
		this.seat = Integer.parseInt(sqlrow.get("rte_seat"));
		this.car = Integer.parseInt(sqlrow.get("rte_car"));
	}

	public Route(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("rte_id");
			this.type = sqlrow.getInt("rte_type");
			this.position_begin = sqlrow.getInt("rte_pos_begin");
			this.position_end = sqlrow.getInt("rte_pos_end");
			this.date_begin = sqlrow.getDate("rte_date_begin");
			this.date_end = sqlrow.getDate("rte_date_end");
			this.comment = sqlrow.getString("rte_comment");
			this.owner = sqlrow.getInt("rte_owner");
			this.seat = sqlrow.getInt("rte_seat");
			this.car = sqlrow.getInt("rte_car");
			
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
	
	
	public Position getPosition_beginObj() {
		if(this.position_beginObj == null) {
			if(this.getPosition_begin() > 0) {
				this.position_beginObj = DaoPosition.getPosition(this.getPosition_begin());
			}else{
				this.position_beginObj = null;
			}
		}
		return this.position_beginObj;		
	}
	
	public Position getPosition_endObj() {
		if(this.position_endObj == null) {
			if(this.getPosition_end() > 0) {
				this.position_endObj = DaoPosition.getPosition(this.getPosition_end());
			}else{
				this.position_endObj = null;
			}
		}
		return this.position_endObj;		
	}
	
	public User getOwnerObj() {
		if(this.ownerObj == null) {
			if(this.getOwner() > 0) {
				this.ownerObj = DaoUser.getUser(this.getOwner());
			}else{
				this.ownerObj = null;
			}
		}
		return this.ownerObj;		
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
	public int getOwner() {
		return owner;
	}
	/**
	 * @param route_owner the route_owner to set
	 */
	public void setOwner(int route_owner) {
		this.owner = route_owner;
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
	

	public Car getCarObj() {
		if(this.carObj == null) {
			if(this.getCar() > 0) {
				this.carObj = DaoCar.getCar(this.getCar());
			}else{
				this.carObj = null;
			}
		}
		return this.carObj;		
	} 

	public Integer getDate_beginAsInteger() {
		return DateUtils.getDateAsInteger(this.getDate_begin());
	}

	public Integer getDate_endAsInteger() {
		return DateUtils.getDateAsInteger(this.getDate_end());
	}
	
	public Hashtable<Integer, Passager> getPassagers() {
		if(this.passagers == null) {
			this.passagers = DaoRoute.getPassagers(this.getId());
		}
		return this.passagers;		
	} 
	

}
