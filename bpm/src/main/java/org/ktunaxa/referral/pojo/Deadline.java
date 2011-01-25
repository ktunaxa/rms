package org.ktunaxa.referral.pojo;

import java.util.Date;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class Deadline {

	private long id;

	private Date date;

	private String description;

	private Referral referral;

	public Deadline() {
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
	 * Set the value of date
	 * 
	 * @param newVar
	 *            the new value of date
	 */
	public void setDate(Date newVar) {
		date = newVar;
	}

	/**
	 * Get the value of date
	 * 
	 * @return the value of date
	 */
	public Date getDate() {
		return date;
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

	/**
	 * Set the value of referral
	 * 
	 * @param newVar
	 *            the new value of referral
	 */
	public void setReferral(Referral newVar) {
		referral = newVar;
	}

	/**
	 * Get the value of referral
	 * 
	 * @return the value of referral
	 */
	public Referral getReferral() {
		return referral;
	}
}