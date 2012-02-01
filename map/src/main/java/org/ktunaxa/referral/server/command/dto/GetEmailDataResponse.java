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

import org.geomajas.command.CommandResponse;

/**
 * Response object for {@link org.ktunaxa.referral.server.command.email.GetEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class GetEmailDataResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private String from;
	private String subject;
	private String body;

	/**
	 * Get e-mail from address.
	 *
	 * @return from address
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Set e-mail from address.
	 *
	 * @param from from address
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Get e-mail subject.
	 *
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Set ee-mail subject.
	 *
	 * @param subject subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Get e-mail body.
	 *
	 * @return body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set e-mail body.
	 *
	 * @param body body
	 */
	public void setBody(String body) {
		this.body = body;
	}
}
