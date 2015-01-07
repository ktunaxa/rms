/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.server.command.dto;

import java.util.Date;

import org.geomajas.command.CommandRequest;

/**
 * Request data for {@link org.ktunaxa.referral.server.command.bpm.CreateProcessCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class CreateProcessRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

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
