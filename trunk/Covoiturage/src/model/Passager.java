package model;



public class Passager {



	protected int id;
	protected int route;
	protected int user;
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
	/**
	 * @param user the user to set
	 */
	public void setUser(int user) {
		this.user = user;
	}
	/**
	 * @param id
	 * @param route
	 * @param user
	 */
	public Passager(int id, int route, int user) {
		super();
		this.id = id;
		this.route = route;
		this.user = user;
	}


}
