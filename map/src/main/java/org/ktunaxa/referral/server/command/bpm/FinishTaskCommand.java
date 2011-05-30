/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.bpm.KtunaxaConfiguration;
import org.ktunaxa.referral.server.command.dto.FinishTaskRequest;
import org.ktunaxa.referral.server.command.dto.UrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Finish a task and return the BPM dashboard URL (which should be redirected to.
 *
 * @author Joachim Van der Auwera
 */
@Component
public class FinishTaskCommand implements Command<FinishTaskRequest, UrlResponse> {

	@Autowired
	private TaskService taskService;

	@Autowired
	private KtunaxaConfiguration ktunaxaConfiguration;

	public UrlResponse getEmptyCommandResponse() {
		return new UrlResponse();
	}

	public void execute(FinishTaskRequest request, UrlResponse response) throws Exception {
		if (null == request.getTaskId()) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "taskId");
		}
		Task task = taskService.createTaskQuery().executionId(request.getTaskId()).singleResult();
		if (task != null) {
			taskService.complete(task.getId());
			response.setUrl(ktunaxaConfiguration.getBpmDashboardBaseUrl());
		}
	}
}
