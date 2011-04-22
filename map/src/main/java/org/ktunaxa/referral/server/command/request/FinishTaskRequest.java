/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.request;

import org.geomajas.command.CommandRequest;

/**
 * Request data for {@link org.ktunaxa.referral.server.command.bpm.FinishTaskCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class FinishTaskRequest implements CommandRequest {

	public static final String COMMAND = "command.bpm.FinishTask";

	private String taskId;

	/**
	 * Get id of task which needs to be finished.
	 *
	 * @return task id
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * Set id of task which needs to be finished.
	 *
	 * @param taskId task id
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
