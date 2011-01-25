package org.ktunaxa.referral.pojo;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class ReferralComment extends Comment {

	private Referral referral;

	public ReferralComment() {
	};

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