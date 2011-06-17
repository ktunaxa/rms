/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.dto.FinishTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Finish a task and return the BPM dashboard URL (which should be redirected to.
 *
 * @author Joachim Van der Auwera
 */
@Component
public class FinishTaskCommand implements Command<FinishTaskRequest, CommandResponse> {

	@Autowired
	private TaskService taskService;

	public CommandResponse getEmptyCommandResponse() {
		return new CommandResponse();
	}

	public void execute(FinishTaskRequest request, CommandResponse response) throws Exception {
		String taskId = request.getTaskId();
		if (null == taskId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "taskId");
		}
		Map<String, String> variables = request.getVariables();
		if (null == variables) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "variables");
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (null != task) {
			taskService.complete(task.getId(), convertToObjects(variables));
		} else {
			throw new GeomajasException(ExceptionCode.UNEXPECTED_PROBLEM, "task " + taskId + " not found");
		}
	}

	private Map<String, Object> convertToObjects(Map<String, String> variables) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Map.Entry<String, String> entry : variables.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.endsWith(KtunaxaBpmConstant.SET_BOOLEAN)) {
				key = key.substring(0, key.length() - KtunaxaBpmConstant.SET_BOOLEAN.length());
				value = "true".equalsIgnoreCase(entry.getValue());
			}
			result.put(key, value);
		}
		return result;
	}
}
