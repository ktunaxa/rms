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
