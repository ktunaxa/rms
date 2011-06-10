/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Request data for {@link org.ktunaxa.referral.server.command.bpm.FinishTaskCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class FinishTaskRequest extends AbstractTaskRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.bpm.FinishTask";

	private Map<String, String> variables = new HashMap<String, String>();

	/**
	 * Get variables to set when finishing the task.
	 *
	 * @return variables to set on task
	 */
	public Map<String, String> getVariables() {
		return variables;
	}

	/**
	 * Set variables to set when finishing task.
	 *
	 * @param variables variables to set on task
	 */
	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
}
