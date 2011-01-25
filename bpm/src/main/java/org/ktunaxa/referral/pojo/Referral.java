package org.ktunaxa.referral.pojo;

import java.util.Date;

import com.vividsolutions.jts.geom.Geometry;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class Referral {

	private long id;

	private String title;

	private String description;

	private User creator;

	private Date creationDate;

	private Geometry geometry;

	private boolean readonly;

	private ReferralStatus status;

	private String contactName;

	private String contactEmail;

	public Referral() {
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
	 * Set the value of title
	 * 
	 * @param newVar
	 *            the new value of title
	 */
	public void setTitle(String newVar) {
		title = newVar;
	}

	/**
	 * Get the value of title
	 * 
	 * @return the value of title
	 */
	public String getTitle() {
		return title;
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
	 * Set the value of creator
	 * 
	 * @param newVar
	 *            the new value of creator
	 */
	public void setCreator(User newVar) {
		creator = newVar;
	}

	/**
	 * Get the value of creator
	 * 
	 * @return the value of creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * Set the value of creationDate
	 * 
	 * @param newVar
	 *            the new value of creationDate
	 */
	public void setCreationDate(Date newVar) {
		creationDate = newVar;
	}

	/**
	 * Get the value of creationDate
	 * 
	 * @return the value of creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Set the value of geometry
	 * 
	 * @param newVar
	 *            the new value of geometry
	 */
	public void setGeometry(Geometry newVar) {
		geometry = newVar;
	}

	/**
	 * Get the value of geometry
	 * 
	 * @return the value of geometry
	 */
	public Geometry getGeometry() {
		return geometry;
	}

	/**
	 * Set the value of readonly
	 * 
	 * @param newVar
	 *            the new value of readonly
	 */
	public void setReadonly(boolean newVar) {
		readonly = newVar;
	}

	/**
	 * Get the value of readonly
	 * 
	 * @return the value of readonly
	 */
	public boolean getReadonly() {
		return readonly;
	}

	/**
	 * Set the value of status
	 * 
	 * @param newVar
	 *            the new value of status
	 */
	public void setStatus(ReferralStatus newVar) {
		status = newVar;
	}

	/**
	 * Get the value of status
	 * 
	 * @return the value of status
	 */
	public ReferralStatus getStatus() {
		return status;
	}

	/**
	 * Set the value of contactName
	 * 
	 * @param newVar
	 *            the new value of contactName
	 */
	public void setContactName(String newVar) {
		contactName = newVar;
	}

	/**
	 * Get the value of contactName
	 * 
	 * @return the value of contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Set the value of contactEmail
	 * 
	 * @param newVar
	 *            the new value of contactEmail
	 */
	public void setContactEmail(String newVar) {
		contactEmail = newVar;
	}

	/**
	 * Get the value of contactEmail
	 * 
	 * @return the value of contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}
}