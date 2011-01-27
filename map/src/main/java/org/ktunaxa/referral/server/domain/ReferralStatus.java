package org.ktunaxa.referral.server.domain;

/**
 * Status value object that referrals can point to. Only a limited set of possible status' are known:
 * <ul>
 * <li>New</li>
 * <li>In progress</li>
 * <li>Approved</li>
 * <li>Denied</li>
 * </ul>
 * 
 * @author Pieter De Graef
 */
public class ReferralStatus {

	private long id;

	private String description;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralStatus() {
	};

	public ReferralStatus(long id) {
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
	 * Set the value of description
	 * 
	 * @param description
	 *            the new value of description
	 */
	public void setDescription(String description) {
		this.description = description;
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