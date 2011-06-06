/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.bpm.AssignTaskCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class AssignTaskRequest extends TaskRequest {

	public static final String COMMAND = "command.bpm.AssignTask";

	private String assignee;

	/**
	 * Get the new assignee for the task.
	 *
	 * @return new assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * Set the new assignee for the task.
	 *
	 * @param assignee new assignee
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}
