/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.activiti.rest.util;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineInfo;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.rest.Config;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.ISO8601DateFormatMethod;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Replacement of the original Activiti REST class to allow a spring configured process engine to be used.
 *
 * @author Joachim Van der Auwera
 */
public class ActivitiWebScript extends DeclarativeWebScript {

	protected Config config;
	private ProcessEngine processEngine;

	/**
	 * Setter for the activiti config bean
	 *
	 * @param config The activiti config bean
	 */
	public void setConfig(Config config) {
		this.config = config;
	}

	/**
	 * The entry point for the webscript.
	 * <p/>
	 * Will create a model and call the executeWebScript() so extending activiti
	 * webscripts may implement custom logic.
	 *
	 * @param req The webscript request
	 * @param status The webscripts status
	 * @param cache The webscript cache
	 * @return The webscript template model
	 */
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		// Prepare model with process engine info
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("iso8601Date", new ISO8601DateFormatMethod());

		// Create activiti request to add heler methods
		ActivitiRequest ar = new ActivitiRequest(req);
		try {

			// Set logged in web user as current user in engine api
			getIdentityService().setAuthenticatedUserId(ar.getCurrentUserId());

			// Let implementing webscript do something useful
			executeWebScript(ar, status, cache, model);
		} finally {
			// Reset the current engine api user
			getIdentityService().setAuthenticatedUserId(null);
		}
		// Return model
		return model;
	}

	/**
	 * Override this class to implement custom logic.
	 *
	 * @param req The webscript request
	 * @param status The webscript
	 * @param cache cache
	 * @param model model
	 */
	protected void executeWebScript(ActivitiRequest req, Status status, Cache cache, Map<String, Object> model) {
		// Override to make something useful
	}

	/**
	 * Returns the process engine info.
	 *
	 * @return The process engine info
	 */
	protected ProcessEngineInfo getProcessEngineInfo() {
		return ProcessEngines.getProcessEngineInfo(processEngine.getName());
	}

	/**
	 * Returns the process engine.
	 *
	 * @return The process engine
	 */
	protected ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	/**
	 * Returns the identity service.
	 *
	 * @return The identity service
	 */
	protected IdentityService getIdentityService() {
		return getProcessEngine().getIdentityService();
	}

	/**
	 * Returns the management service.
	 *
	 * @return The management service.
	 */
	protected ManagementService getManagementService() {
		return getProcessEngine().getManagementService();
	}

	/**
	 * Returns The process service.
	 *
	 * @return The process service
	 */
	protected RuntimeService getRuntimeService() {
		return getProcessEngine().getRuntimeService();
	}

	/**
	 * Returns The history service.
	 *
	 * @return The history service
	 */
	protected HistoryService getHistoryService() {
		return getProcessEngine().getHistoryService();
	}

	/**
	 * Returns The repository service.
	 *
	 * @return The repository service
	 */
	protected RepositoryService getRepositoryService() {
		return getProcessEngine().getRepositoryService();
	}

	/**
	 * Returns the task service.
	 *
	 * @return The task service
	 */
	protected TaskService getTaskService() {
		return getProcessEngine().getTaskService();
	}

	/**
	 * Returns the form service.
	 *
	 * @return The form service
	 */
	protected FormService getFormService() {
		return getProcessEngine().getFormService();
	}

}
