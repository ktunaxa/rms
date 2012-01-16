/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.Map;

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
