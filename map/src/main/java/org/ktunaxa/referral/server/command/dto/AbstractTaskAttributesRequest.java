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

import java.util.Map;

import org.geomajas.command.CommandRequest;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Base request object which contains a task DTO and attributes map.
 *
 * @author Joachim Van der Auwera
 */
public abstract class AbstractTaskAttributesRequest implements CommandRequest {

	private TaskDto task;
	private Map<String, String> attributes;

	/**
	 * Get task DTO.
	 *
	 * @return task DTO
	 */
	public TaskDto getTask() {
		return task;
	}

	/**
	 * Set task DTO.
	 *
	 * @param task task dto
	 */
	public void setTask(TaskDto task) {
		this.task = task;
	}

	/**
	 * Get attributes which can be used in the e-mail template.
	 *
	 * @return map of attributes which can be used in the e-mail
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Set attributes which can be used in the e-mail template.
	 *
	 * @param attributes map of attributes which can be used in the e-mail
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
