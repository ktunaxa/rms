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

import java.util.List;

import org.geomajas.command.CommandResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

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
