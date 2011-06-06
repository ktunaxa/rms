/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.TaskService;
import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.GetReferralMapCommand;
import org.ktunaxa.referral.server.command.dto.AssignTaskRequest;
import org.ktunaxa.referral.server.command.dto.ReferralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Set the assignee for a task.
 *
 * @author Joachim Van der Auwera
 */
@Component
public class AssignTaskCommand implements Command<AssignTaskRequest, ReferralResponse> {

	@Autowired
	private GetReferralMapCommand referralMapCommand;

	@Autowired
	private TaskService taskService;

	public ReferralResponse getEmptyCommandResponse() {
		return new ReferralResponse();
	}

	public void execute(AssignTaskRequest request, ReferralResponse response) throws Exception {
		String taskId = request.getTaskId();
		if (null == taskId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "taskId");
		}
		taskService.setAssignee(taskId, request.getAssignee());
		Object variable = taskService.getVariable(taskId, KtunaxaBpmConstant.REFERRAL_CONTEXT_REFERRAL_ID);
		if (null != variable) {
			response.setReferral(referralMapCommand.getReferral(variable.toString()));
		}
	}
}
