/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.geomajas.command.Command;
import org.ktunaxa.referral.server.command.dto.GetTaskRequest;
import org.ktunaxa.referral.server.command.dto.GetTaskResponse;
import org.ktunaxa.referral.server.service.DtoConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Fetch a task.
 * 
 * @author Jan De Moerloose
 * 
 */
@Component
public class GetTasksCommand implements Command<GetTaskRequest, GetTaskResponse> {

	@Autowired
	private TaskService taskService;

	@Autowired
	private DtoConverterService converterService;

	public GetTaskResponse getEmptyCommandResponse() {
		return new GetTaskResponse();
	}

	public void execute(GetTaskRequest request, GetTaskResponse response) throws Exception {
		Task task = taskService.createTaskQuery().executionId(request.getTaskId()).singleResult();
		if (task != null) {
			response.setTask(converterService.toDto(task));
		}
	}

}
