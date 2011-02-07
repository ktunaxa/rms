package org.ktunaxa.referral.server.dto;

import java.io.Serializable;

/**
 * Definition of a category of referrals.
 * 
 * @author Pieter De Graef
 */
public class ReferralTypeDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** Description for the type of activity occurring in the land: Forestry, Mining, etc... */
	private String description;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralTypeDto() {
	};

	public ReferralTypeDto(long id) {
		this.id = id;
	};

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The category's unique identifier.
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
	 * Set the value of description.
	 * 
	 * @param description
	 *            The new value of description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the value of description.
	 * 
	 * @return The value of description.
	 */
	public String getDescription() {
		return description;
	}
}