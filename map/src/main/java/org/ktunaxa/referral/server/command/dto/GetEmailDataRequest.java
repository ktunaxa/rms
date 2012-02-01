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

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.GetEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class GetEmailDataRequest extends AbstractTaskAttributesRequest {

	private static final long serialVersionUID = 100L;
	
	public static final String COMMAND = "command.email.GetEmailData";

	private String notifier;

	/** No-arguments constructor. */
	public GetEmailDataRequest() {
		super();
	}

	/**
	 * Set notifier, the e-mail template indicator.
	 *
	 * @param notifier notifier
	 */
	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	/**
	 * Get notifier, the e-mail template indicator.
	 *
	 * @return notifier
	 */
	public String getNotifier() {
		return notifier;
	}
}
