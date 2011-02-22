package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

import utilities.DateUtils;

import dao.DaoRoute;
import dao.DaoUser;



public class Passager {

	protected int id=0;
	protected int route=0;
	protected int user=0;
	protected int type=0;
	protected Date askdate=null;
	
	protected Route routeObj=null;
	protected User userObj=null;
	
	public static int PASSAGER_TYPE_ACCEPTED = 1;
	public static int PASSAGER_TYPE_REJECTED = 2;
	public static int PASSAGER_TYPE_PENDING = 3;
	
	/**
	 * @param id
	 * @param route
	 * @param user
	 */
	public Passager(int id, int route, int user, int type, Date askdate) {
		super();
		this.id = id;
		this.route = route;
		this.user = user;
		this.type = type;
		this.askdate = askdate;
	}
	
	/**
	 * @param id
	 */
	public Passager(int id) {
		super();
		this.id = id;
	}
	
	/**
	 */
	public Passager(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.valueOf(sqlrow.get("psg_id"));
		this.route = Integer.valueOf(sqlrow.get("psg_route"));
		this.user = Integer.valueOf(sqlrow.get("psg_user"));
		this.type = Integer.valueOf(sqlrow.get("psg_type"));
		this.askdate = DateUtils.getTimestampAsDate(Integer.valueOf(sqlrow.get("psg_askdate")));
	}
	
	public Passager(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("psg_id");
			this.route = sqlrow.getInt("psg_route");
			this.user = sqlrow.getInt("psg_user");
			this.type = sqlrow.getInt("psg_type");
			this.askdate = DateUtils.getTimestampAsDate(sqlrow.getInt("psg_askdate"));
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
	 * @return the route
	 */
	public int getRoute() {
		return route;
	}
	
	public Route getRouteObj() {
		if(this.routeObj == null) {
			if(this.getRoute() > 0) {
				this.routeObj = DaoRoute.getRoute(this.getRoute());
			}else{
				this.routeObj = null;
			}
		}
		return this.routeObj;	
	}
	
	public void setRouteObj(Route rte) {
		this.routeObj = rte;	
	}
	
	public void setUserObj(User usr) {
		this.userObj = usr;	
	}
	
	
	/**
	 * @param route the route to set
	 */
	public void setRoute(int route) {
		this.route = route;
	}
	/**
	 * @return the user
	 */
	public int getUser() {
		return user;
	}
	
	public User getUserObj() {
		if(this.userObj == null) {
			if(this.getUser() > 0) {
				this.userObj = DaoUser.getUser(this.getUser());
			}else{
				this.userObj = null;
			}
		}
		return this.userObj;	
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(int user) {
		this.user = user;
	}
	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getAskdate() {
		return askdate;
	}
	
	public Integer getAskdateAsInteger() {
		return DateUtils.getDateAsInteger(this.getAskdate());
	}

	public void setAskdate(Date askdate) {
		this.askdate = askdate;
	}


}
