package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import dao.DaoComment;
import dao.DaoUser;


public class Comment {


	protected int id=0;
	protected int user_from=0;
	protected int user_to=0;
	protected String text="";
	protected int note=0;
	
	protected User user_fromObj=null;
	protected User user_toObj=null;
	
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
	 * @return the user_from
	 */
	public int getUser_from() {
		return user_from;
	}
	
	public User getUser_fromObj() {
		if(this.user_fromObj == null) {
			if(this.getUser_from() > 0) {
				this.user_fromObj = DaoUser.getUser(this.getUser_from());
			}else{
				this.user_fromObj = null;
			}
		}
		return this.user_fromObj;	
	}
	
	/**
	 * @param user_from the user_from to set
	 */
	public void setUser_from(int user_from) {
		this.user_from = user_from;
	}
	/**
	 * @return the user_to
	 */
	public int getUser_to() {
		return user_to;
	}
	
	public User getUser_toObj() {
		if(this.user_toObj == null) {
			if(this.getUser_to() > 0) {
				this.user_toObj = DaoUser.getUser(this.getUser_to());
			}else{
				this.user_toObj = null;
			}
		}
		return this.user_toObj;	
	}
	
	
	/**
	 * @param user_to the user_to to set
	 */
	public void setUser_to(int user_to) {
		this.user_to = user_to;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the note
	 */
	public int getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(int note) {
		this.note = note;
	}
	/**
	 * @param id
	 * @param user_from
	 * @param user_to
	 * @param text
	 * @param note
	 */
	public Comment(int id, int user_from, int user_to, String text, int note) {
		super();
		this.id = id;
		this.user_from = user_from;
		this.user_to = user_to;
		this.text = text;
		this.note = note;
	}
	
	/**
	 * @param id
	 */
	public Comment(int id) {
		super();
		this.id = id;
	}
	
	public Comment(Hashtable<String, String> sqlrow) {
		super();
		this.id = Integer.valueOf(sqlrow.get("cmn_id"));
		this.user_from = Integer.valueOf(sqlrow.get("cmn_user_from"));
		this.user_to = Integer.valueOf(sqlrow.get("cmn_user_to"));
		this.text = sqlrow.get("cmn_text");
		this.note = Integer.valueOf(sqlrow.get("cmn_note"));
	}
	
	public Comment(ResultSet sqlrow) {
		super();
		
		try {
			this.id = sqlrow.getInt("cmn_id");
			this.user_from = sqlrow.getInt("cmn_user_from");
			this.user_to = sqlrow.getInt("cmn_user_to");
			this.text = sqlrow.getString("cmn_text");
			this.note = sqlrow.getInt("cmn_note");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	static Comment getCommentFromAbout(int user_from_id, int user_to_id) {
		Comment cmn=null;
		
		cmn = DaoComment.getCommentFromAbout(user_from_id, user_to_id);
		
		return cmn;
	}

}
