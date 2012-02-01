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

/**
 * Request object for {@link org.ktunaxa.referral.server.command.bpm.AssignTaskCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class AssignTaskRequest extends AbstractTaskRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.bpm.AssignTask";

	private String assignee;

	/**
	 * Get the new assignee for the task.
	 *
	 * @return new assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * Set the new assignee for the task.
	 *
	 * @param assignee new assignee
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}
