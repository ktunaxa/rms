/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request DTO object for {@link org.ktunaxa.referral.server.command.bpm.GetTaskCommand} command.
 * 
 * @author Jan De Moerloose
 * 
 */
public class GetTaskRequest implements CommandRequest {

	public static final String COMMAND = "command.bpm.GetTask";

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
	 * @param taskId
	 *            task id
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
