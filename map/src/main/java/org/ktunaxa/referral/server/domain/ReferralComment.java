package org.ktunaxa.referral.server.domain;

/**
 * A comment that is associated with a certain referral.
 * 
 * @author Pieter De Graef
 */
public class ReferralComment extends Comment {

	/** The referral to which this comment is associated. */
	private Referral referral;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralComment() {
	};

	public ReferralComment(long id) {
		super(id);
	};

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * Set referral to which this comment is associated.
	 * 
	 * @param referral
	 *            the new value of referral
	 */
	public void setReferral(Referral referral) {
		this.referral = referral;
	}

	/**
	 * Get the referral to which this comment is associated.
	 * 
	 * @return The referral to which this comment is associated.
	 */
	public Referral getReferral() {
		return referral;
	}
}