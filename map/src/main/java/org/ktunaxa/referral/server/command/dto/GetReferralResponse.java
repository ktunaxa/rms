/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;
import org.geomajas.layer.feature.Feature;

/**
 * Command response object which contains a referral.
 *
 * @author Joachim Van der Auwera
 */
public class GetReferralResponse extends CommandResponse {

	private Feature referral;

	/**
	 * Get the feature for the referral.
	 *
	 * @return referral feature.
	 */
	public Feature getReferral() {
		return referral;
	}

	/**
	 * Set the feature for the referral.
	 *
	 * @param referral referral feature.
	 */
	public void setReferral(Feature referral) {
		this.referral = referral;
	}
}
