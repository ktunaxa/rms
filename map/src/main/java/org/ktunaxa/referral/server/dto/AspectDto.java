package org.ktunaxa.referral.server.dto;

import java.io.Serializable;

/**
 * Definition of an "aspect" to which referrals need to be checked. Possible aspects are:
 * <ul>
 * <li>Culture</li>
 * <li>Ecology</li>
 * <li>Archaeology</li>
 * <li>Aquatic</li>
 * <li>...</li>
 * </ul>
 * 
 * @author Pieter De Graef
 */
public class AspectDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	private String description;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public AspectDto() {
	};

	public AspectDto(long id) {
		this.id = id;
	};

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