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
