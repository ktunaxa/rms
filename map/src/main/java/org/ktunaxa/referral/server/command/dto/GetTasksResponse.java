/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.List;

/**
 * Response DTO object for {@link org.ktunaxa.referral.server.command.bpm.GetTasksCommand} command.
 * 
 * @author Joachim Van der Auwera
 */
public class GetTasksResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private List<TaskDto> tasks;

	/**
	 * Tasks in the response.
	 *
	 * @return tasks
	 */
	public List<TaskDto> getTasks() {
		return tasks;
	}

	/**
	 * Set the tasks for the response.
	 *
	 * @param tasks tasks
	 */
	public void setTasks(List<TaskDto> tasks) {
		this.tasks = tasks;
	}
}
