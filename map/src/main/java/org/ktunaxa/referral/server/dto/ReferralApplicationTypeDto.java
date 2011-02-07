package org.ktunaxa.referral.server.dto;

import java.io.Serializable;

/**
 * Definition of an application type for referrals. Possibilities are:
 * <ul>
 * <li>New</li>
 * <li>Renewal</li>
 * <li>Amendment</li>
 * <li>Replacement</li>
 * </ul>
 * 
 * @author Pieter De Graef
 */
public class ReferralApplicationTypeDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** Description for the type of activity occurring in the land: forestry, mining, etc... */
	private String description;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralApplicationTypeDto() {
	};

	public ReferralApplicationTypeDto(long id) {
		this.id = id;
	};

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The application type's unique identifier.
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