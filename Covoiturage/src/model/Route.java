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
	protected double price;
	
	protected Position position_beginObj;
	protected Position position_endObj;
	protected User ownerObj;
	protected Car carObj;
	protected Hashtable<Integer, Passager> passagers=null;
	protected Passager [] passagerslist = null;
	protected Boolean isPassegerListEmpty = true;
	
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
			int seat, int car, double price) {
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
		this.price = price;
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
		this.date_begin = DateUtils.getTimestampAsDate(Integer.valueOf(sqlrow.get("rte_date_begin")));
		this.date_end = DateUtils.getTimestampAsDate(Integer.valueOf(sqlrow.get("rte_date_end")));
		this.comment = sqlrow.get("rte_comment");
		this.owner = Integer.parseInt(sqlrow.get("rte_owner"));
		this.seat = Integer.parseInt(sqlrow.get("rte_seat"));
		this.car = Integer.parseInt(sqlrow.get("rte_car"));
		this.price = Double.parseDouble(sqlrow.get("rte_price"));
	}

	public Route(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("rte_id");
			this.type = sqlrow.getInt("rte_type");
			this.position_begin = sqlrow.getInt("rte_pos_begin");
			this.position_end = sqlrow.getInt("rte_pos_end");
			this.date_begin = DateUtils.getTimestampAsDate(sqlrow.getInt("rte_date_begin"));
			this.date_end = DateUtils.getTimestampAsDate(sqlrow.getInt("rte_date_end"));
			this.comment = sqlrow.getString("rte_comment");
			this.owner = sqlrow.getInt("rte_owner");
			this.seat = sqlrow.getInt("rte_seat");
			this.car = sqlrow.getInt("rte_car");
			this.price = sqlrow.getDouble("rte_price");
			
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
	

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setPosition_beginObj(Position position_beginObj) {
		this.position_beginObj = position_beginObj;
	}

	public void setPosition_endObj(Position position_endObj) {
		this.position_endObj = position_endObj;
	}

	public void setOwnerObj(User ownerObj) {
		this.ownerObj = ownerObj;
	}

	public void setCarObj(Car carObj) {
		this.carObj = carObj;
	}

	public void setPassagers(Hashtable<Integer, Passager> passagers) {
		this.passagers = passagers;
		this.passagerslist = passagers.values().toArray(new Passager[0]);
	}

	public Passager [] getPassagerslist() {
		if (passagerslist == null)
			System.out.println("passager list null");
		else if (passagerslist.length == 0)
			System.out.println("passager list empty");
		return passagerslist;
	}

	public void setPassagerslist(Passager [] passagerslist) {
		this.passagerslist = passagerslist;
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
	
	public Hashtable<Integer, Passager> getPassagersOfType(int pgt_id) {
		if(this.passagers == null) {
			this.passagers = DaoRoute.getPassagersOfType(this.getId(), pgt_id);
		}
		return this.passagers;		
	} 
	
	public Hashtable<Integer, Passager> getPassagers() {
		if(this.passagers == null) {
			this.passagers = DaoRoute.getPassagers(this.getId());
		}
		return this.passagers;		
	} 
	
	public Boolean getIsPassegerListEmpty() {
		return passagerslist.length == 0;
	}

	public void setIsPassegerListEmpty(Boolean isPassegerListEmpty) {
		this.isPassegerListEmpty = isPassegerListEmpty;
	}

	public Integer getEmpty_seats(){
		// Retourne le nombre de places disponibles restantes
        return Math.max(0, this.getSeat() - this.getPassagersOfType(Passager.PASSAGER_TYPE_ACCEPTED).size());
	}

	public long getDelay_from_now() {
		long begin = this.date_begin.getTime();
		long now = new Date().getTime();
		long delta =  begin - now;
		long minutes = delta / 1000 / 60;
		return minutes;
	}
	
	public String getSeatDots() {
		// Retourne une représentation sous forme de points blancs et noirs du nombre de places restantes
		// Note : je sais, c'est mal. -Vincent.
		int nRestant = this.getEmpty_seats().intValue();
		int nTotal = this.getSeat();
		int i = 0;
		String outputStr = "";
		
		for (; i < nRestant ; i++)
			outputStr += "◎";
		for (; i < nTotal ; i++)
			outputStr += "◉";
			
		return outputStr;
	}
}
