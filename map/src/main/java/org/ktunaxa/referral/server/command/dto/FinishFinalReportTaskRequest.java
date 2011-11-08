/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.plugin.reporting.command.dto.PrepareReportingRequest;

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
	private String referralId;
	private String taskId;
	private Map<String, String> variables = new HashMap<String, String>();
	private PrepareReportingRequest prepareReportingRequest;

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

	/**
	 * Set the request object to prepare the reporting building.
	 *
	 * @param prepareReportingRequest prepare reporting request object
	 */
	public void setReportingRequest(PrepareReportingRequest prepareReportingRequest) {
		this.prepareReportingRequest = prepareReportingRequest;
	}

	/**
	 * Get the request object to prepare the reporting building.
	 *
	 * @return prepare reporting request object
	 */
	public PrepareReportingRequest getPrepareReportingRequest() {
		return prepareReportingRequest;
	}
}
