/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Response DTO object for {@link org.ktunaxa.referral.server.command.bpm.GetTaskCommand} command.
 * 
 * @author Jan De Moerloose
 * 
 */
public class GetTaskResponse extends CommandResponse {

	private TaskDto task;

	public TaskDto getTask() {
		return task;
	}

	public void setTask(TaskDto task) {
		this.task = task;
	}

}
