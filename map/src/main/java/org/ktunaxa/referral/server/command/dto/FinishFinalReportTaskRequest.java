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
 * Request object for {@link org.ktunaxa.referral.server.command.FinishFinalReportTaskCommand}.
 *
 * @author Joachim Van der Auwera
 */
public class FinishFinalReportTaskRequest extends SendEmailRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.FinishFinalReportTask";

	private boolean sendMail;
	private boolean skipReportUpload;
	private String referralId;
	private String taskId;
	private Map<String, String> variables = new HashMap<String, String>();

	/**
	 * Should the e-mail with the report be sent?
	 *
	 * @return true when mail should be sent
	 */
	public boolean isSendMail() {
		return sendMail;
	}

	/**
	 * Should the e-mail with the report be sent?
	 *
	 * @param sendMail true when mail should be sent
	 */
	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}
	
	/**
	 * Should the report creation, upload to Alfresco and attachment to the referral be skipped ?
	 *
	 * @return true when all of this should be skipped
	 */
	public boolean isSkipReportUpload() {
		return skipReportUpload;
	}

	
	/**
	 * Should the report creation, upload to Alfresco and attachment to the referral be skipped ?
	 *
	 * @param skipReportUpload true when all of this should be skipped
	 */
	public void setSkipReportUpload(boolean skipReportUpload) {
		this.skipReportUpload = skipReportUpload;
	}

	/**
	 * Full id for referral to close (eg "3500-12/10-201").
	 *
	 * @return referral id
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * Full id for referral to close (eg "3500-12/10-201").
	 *
	 * @param referralId referral id
	 */
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

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
	 * @param taskId task id
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

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
