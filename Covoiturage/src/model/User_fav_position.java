package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class User_fav_position {

	protected int id;
	protected int user;
	protected int position;
	protected String label;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public int getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(int user) {
		this.user = user;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param id
	 * @param user
	 * @param position
	 * @param label
	 */
	public User_fav_position(int id, int user, int position, String label) {
		super();
		this.id = id;
		this.user = user;
		this.position = position;
		this.label = label;
	}

	public User_fav_position(int id) {
		super();
		this.id = id;
	}

	public User_fav_position(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.valueOf(sqlrow.get("ufp_id"));
		this.user = Integer.valueOf(sqlrow.get("ufp_user"));
		this.position = Integer.valueOf(sqlrow.get("ufp_position"));
		this.label = sqlrow.get("ufp_label");
	}

	public User_fav_position(ResultSet sqlrow) {
		super();
		try {
			this.id = sqlrow.getInt("ufp_id");
			this.user = sqlrow.getInt("ufp_user");
			this.position = sqlrow.getInt("ufp_position");
			this.label = sqlrow.getString("ufp_label");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
