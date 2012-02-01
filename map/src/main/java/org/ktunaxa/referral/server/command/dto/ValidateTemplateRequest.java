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
 * Request object for {@link org.ktunaxa.referral.server.command.email.ValidateTemplateCommand}.
 * 
 * @author Emiel Ackermann
 */
public class ValidateTemplateRequest extends AbstractTaskAttributesRequest {

	private static final long serialVersionUID = 100L;
	
	public static final String COMMAND = "command.email.ValidateTemplate";

	private String subject;
	private String body;

	/** No-arguments constructor. */
	public ValidateTemplateRequest() {
		super();
	}

	/**
	 * Set e-mail body template.
	 *
	 * @param body body template
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Get e-mail body template.
	 *
	 * @return template body template
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set e-mail subject template.
	 *
	 * @param subject subject template
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Get e-mail subject template.
	 *
	 * @return subject template
	 */
	public String getSubject() {
		return subject;
	}

}
