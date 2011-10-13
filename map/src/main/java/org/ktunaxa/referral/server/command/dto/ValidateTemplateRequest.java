/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.ValidateTemplateCommand}.
 * 
 * @author Emiel Ackermann
 */
public class ValidateTemplateRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;
	
	public static final String COMMAND = "command.email.ValidateTemplate";

	private TaskDto task;
	private String subject;
	private String body;
	
	public ValidateTemplateRequest() {
	}

	public TaskDto getTask() {
		return task;
	}

	public void setTask(TaskDto task) {
		this.task = task;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	
}
