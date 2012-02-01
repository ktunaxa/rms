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

import org.geomajas.command.CommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.bpm.GetTasksCommand} command.
 *
 * @author Joachim Van der Auwera
 */
public class GetTasksRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.bpm.GetTasks";

	private boolean includeUnassignedTasks;
	private String assignee;
	private String referralId;

	/**
	 * Get whether unassigned tasks should be included.
	 *
	 * @return should unassigned tasks be included?
	 */
	public boolean isIncludeUnassignedTasks() {
		return includeUnassignedTasks;
	}

	/**
	 * Set whether unassigned tasks should be included.
	 *
	 * @param  includeUnassignedTasks should unassigned tasks be included?
	 */
	public void setIncludeUnassignedTasks(boolean includeUnassignedTasks) {
		this.includeUnassignedTasks = includeUnassignedTasks;
	}

	/**
	 * Get the assignee for which to return the open tasks.
	 *
	 * @return assignee to return tasks for
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * Set the assignee for which to return the open tasks.
	 *
 	 * @param assignee assignee
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * Get the process instance id for which to include tasks.
	 *
	 * @return process instance id
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * Set the process instance if which which to include tasks.
	 *
	 * @param referralId process instance id
	 */
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}
}
