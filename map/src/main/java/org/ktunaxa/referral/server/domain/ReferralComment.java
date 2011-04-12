/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A comment that is associated with a certain referral.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "referral_comment")
public class ReferralComment extends AbstractComment {

	/** The referral to which this comment is associated. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "referral_id", nullable = false)
	private Referral referral;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralComment() {
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