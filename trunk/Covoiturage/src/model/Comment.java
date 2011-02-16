package model;


public class Comment {


	protected int id;
	protected int user_from;
	protected int user_to;
	protected String text;
	protected int note;
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


}
