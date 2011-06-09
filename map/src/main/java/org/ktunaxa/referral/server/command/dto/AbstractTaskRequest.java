/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Base request which contains a task id.
 *
 * @author Joachim Van der Auwera
 */
public abstract class AbstractTaskRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

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
