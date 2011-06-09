/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.GetReferralCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class GetReferralRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

	private String referralId;

	/**
	 * Get the referral id.
	 *
	 * @return referral id
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * Set the referral id.
	 *
	 * @param referralId referral id
	 */
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}
}
