/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request data for {@link org.ktunaxa.referral.server.command.bpm.CloseReferralCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class CloseReferralRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.bpm.CloseReferral";

	private String referralId;
	private String reason;

	/**
	 * Full id for referral to close (eg "3500-12/10-201").
	 *
	 * @return referral id
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * Full id for referral to close (eg "3500-12/10-201").
	 *
	 * @param referralId referral id
	 */
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	/**
	 * Reason for stopping the referral process.
	 *
	 * @return reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Reason for stopping the referral process.
	 *
	 * @param reason reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
}
