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
 * Request object for {@link org.ktunaxa.referral.server.command.email.UpdateEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class UpdateEmailDataRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;
	
	public static final String COMMAND = "command.email.UpdateEmailData";

	private String notifier;
	private String subject;
	private String from;
	private String cc;
	private String body;

	public UpdateEmailDataRequest() {
	}

	public UpdateEmailDataRequest(String notifier) {
		this.notifier = notifier;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getCc() {
		return cc;
	}
	
	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	public String getNotifier() {
		return notifier;
	}

}
