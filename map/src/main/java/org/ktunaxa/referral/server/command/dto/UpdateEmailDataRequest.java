/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.UpdateEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class UpdateEmailDataRequest implements CommandRequest {

	private static final long serialVersionUID = 1000L;
	
	public static final String COMMAND = "command.email.UpdateEmailData";

	private String notifier;
	private String subject;
	private String from;
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
