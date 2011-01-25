package org.ktunaxa.referral.pojo;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class Aspect {

	private long id;

	private String description;

	public Aspect() {
	};

	/**
	 * Set the value of id
	 * 
	 * @param newVar
	 *            the new value of id
	 */
	public void setId(long newVar) {
		id = newVar;
	}

	/**
	 * Get the value of id
	 * 
	 * @return the value of id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set the value of description
	 * 
	 * @param newVar
	 *            the new value of description
	 */
	public void setDescription(String newVar) {
		description = newVar;
	}

	/**
	 * Get the value of description
	 * 
	 * @return the value of description
	 */
	public String getDescription() {
		return description;
	}
}