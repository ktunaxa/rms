/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.request;

import org.geomajas.command.CommandRequest;

import java.util.Date;

/**
 * Request data for {@link org.ktunaxa.referral.server.command.bpm.CreateProcessCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class CreateProcessRequest implements CommandRequest {

	public static final String COMMAND = "command.bpm.CreateProcess";

	private String referralId;
	private String description;
	private String email;
	private int engagementLevel;
	private Date completionDeadline;

	/**
	 * Get referral id.
	 *
	 * @return referral id
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * Set referral id.
	 *
	 * @param referralId referral id
	 */
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	/**
	 * Get referral description.
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set referral description.
	 *
	 * @param description description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get referral contact e-mail address.
	 *
	 * @return e-mail address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set referral contact e-mail address.
	 *
	 * @param email e-mail address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get engagement level for referral.
	 *
	 * @return engagement level
	 */
	public int getEngagementLevel() {
		return engagementLevel;
	}

	/**
	 * set engagement level for referral.
	 *
	 * @param engagementLevel engagement level
	 */
	public void setEngagementLevel(int engagementLevel) {
		this.engagementLevel = engagementLevel;
	}

	/**
	 * Get completion deadline for referral.
	 *
	 * @return completion deadline
	 */
	public Date getCompletionDeadline() {
		return completionDeadline;
	}

	/**
	 * Set completion deadline for referral.
	 *
	 * @param completionDeadline completion deadline
	 */
	public void setCompletionDeadline(Date completionDeadline) {
		this.completionDeadline = completionDeadline;
	}
}
