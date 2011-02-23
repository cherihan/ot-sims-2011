package model;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

import dao.DaoPosition;
import dao.DaoRoute;
import utilities.DateUtils;


public class Segment {

	protected int id;
	protected int route;
	protected int pos_begin;
	protected int pos_end;
	protected Date date_begin;
	protected int duration;
	protected int order;
	protected Position pos_beginObj;
	protected Position pos_endObj;
	protected Route routeObj;
	
	
	public Segment(int id, int route, int pos_begin, int pos_end,
			Date date_begin, int duration, int order) {
		super();
		this.id = id;
		this.route = route;
		this.pos_begin = pos_begin;
		this.pos_end = pos_end;
		this.date_begin = date_begin;
		this.duration = duration;
		this.order = order;
	}
	
	public Segment(int id) {
		super();
		this.id = id;
	}
	
	public Segment(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.parseInt(sqlrow.get("seg_id"));
		this.route = Integer.parseInt(sqlrow.get("seg_route"));
		this.pos_begin = Integer.parseInt(sqlrow.get("seg_pos_begin"));
		this.pos_end = Integer.parseInt(sqlrow.get("seg_pos_end"));
		this.date_begin = DateUtils.getTimestampAsDate(Integer.parseInt(sqlrow.get("seg_date_begin")));
		this.duration = Integer.parseInt(sqlrow.get("seg_duration"));
		this.order = Integer.parseInt(sqlrow.get("seg_order"));
	}
	
	public Segment(ResultSet sqlrow) {
		super();
		try {
			this.id = (sqlrow.getInt("seg_id"));
			this.route = (sqlrow.getInt("seg_route"));
			this.pos_begin = (sqlrow.getInt("seg_pos_begin"));
			this.pos_end = (sqlrow.getInt("seg_pos_end"));
			this.date_begin = DateUtils.getTimestampAsDate((sqlrow.getInt("seg_date_begin")));
			this.duration = (sqlrow.getInt("seg_duration"));
			this.order = (sqlrow.getInt("seg_order"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoute() {
		return route;
	}

	public void setRoute(int route) {
		this.route = route;
	}

	public int getPos_begin() {
		return pos_begin;
	}

	public void setPos_begin(int pos_begin) {
		this.pos_begin = pos_begin;
	}

	public int getPos_end() {
		return pos_end;
	}

	public void setPos_end(int pos_end) {
		this.pos_end = pos_end;
	}

	public Date getDate_begin() {
		return date_begin;
	}

	public void setDate_begin(Date date_begin) {
		this.date_begin = date_begin;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Position getPos_beginObj() {
		if(this.pos_beginObj == null) {
			if(this.getPos_begin() > 0) {
				this.pos_beginObj = DaoPosition.getPosition(this.getPos_begin());
			}else{
				this.pos_beginObj = null;
			}
		}
		return this.pos_beginObj;	
	}

	public void setPos_beginObj(Position pos_beginObj) {
		this.pos_beginObj = pos_beginObj;
	}

	public Position getPos_endObj() {
		if(this.pos_endObj == null) {
			if(this.getPos_end() > 0) {
				this.pos_endObj = DaoPosition.getPosition(this.getPos_end());
			}else{
				this.pos_endObj = null;
			}
		}
		return this.pos_endObj;
	}

	public void setPos_endObj(Position pos_endObj) {
		this.pos_endObj = pos_endObj;
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

	public void setRouteObj(Route routeObj) {
		this.routeObj = routeObj;
	}
	
	
	
	
}
