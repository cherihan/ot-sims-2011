package model;



public class Contain_criterion {



	protected int container;
	protected int contained;
	/**
	 * @return the container
	 */
	public int getContainer() {
		return container;
	}
	/**
	 * @param container the container to set
	 */
	public void setContainer(int container) {
		this.container = container;
	}
	/**
	 * @return the contained
	 */
	public int getContained() {
		return contained;
	}
	/**
	 * @param contained the contained to set
	 */
	public void setContained(int contained) {
		this.contained = contained;
	}
	/**
	 * @param container
	 * @param contained
	 */
	public Contain_criterion(int container, int contained) {
		super();
		this.container = container;
		this.contained = contained;
	}
	

}
