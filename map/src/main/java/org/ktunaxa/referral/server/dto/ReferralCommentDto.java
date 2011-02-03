package org.ktunaxa.referral.server.dto;

/**
 * A comment that is associated with a certain referral.
 * 
 * @author Pieter De Graef
 */
public class ReferralCommentDto extends CommentDto {

	private static final long serialVersionUID = 100L;

	/** The referral to which this comment is associated. */
	private ReferralDto referral;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralCommentDto() {
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
	public void setReferral(ReferralDto referral) {
		this.referral = referral;
	}

	/**
	 * Get the referral to which this comment is associated.
	 * 
	 * @return The referral to which this comment is associated.
	 */
	public ReferralDto getReferral() {
		return referral;
	}
}