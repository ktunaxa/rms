package org.ktunaxa.referral.server.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Deadlines (start, stop, ...) that can be associated with a certain referral.
 * 
 * @author Pieter De Graef
 */
public class DeadlineDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** The date associated with the deadline. */
	private Date date;

	/** The textual description for this deadline. */
	private String description;

	/** The referral that this deadline is associated with. */
	private ReferralDto referral;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public DeadlineDto() {
	};

	public DeadlineDto(long id) {
		this.id = id;
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The aspect's unique identifier.
	 * 
	 * @param id
	 *            The new value for the identifier.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the value of the identifier.
	 * 
	 * @return the value of the identifier.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Returns the date associated with the deadline.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the date associated with the deadline.
	 * 
	 * @param date
	 *            The new date.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Returns the textual description for this deadline.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the textual description for this deadline.
	 * 
	 * @param description
	 *            The new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The referral that this deadline is associated with.
	 */
	public ReferralDto getReferral() {
		return referral;
	}

	/**
	 * Set the referral that this deadline is associated with.
	 * 
	 * @param referral
	 *            The new referral object.
	 */
	public void setReferral(ReferralDto referral) {
		this.referral = referral;
	};
}