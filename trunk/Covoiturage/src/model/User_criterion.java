package model;


import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;

public class User_criterion {



	protected int user_id;
	protected int crt_id;
	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the crt_id
	 */
	public int getCrt_id() {
		return crt_id;
	}
	/**
	 * @param crt_id the crt_id to set
	 */
	public void setCrt_id(int crt_id) {
		this.crt_id = crt_id;
	}
	/**
	 * @param user_id
	 * @param crt_id
	 */
	public User_criterion(int user_id, int crt_id) {
		super();
		this.user_id = user_id;
		this.crt_id = crt_id;
	}
	

}
