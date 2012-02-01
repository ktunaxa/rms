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

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.TaskService;
import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.GetReferralCommand;
import org.ktunaxa.referral.server.command.dto.AssignTaskRequest;
import org.ktunaxa.referral.server.command.dto.GetReferralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Set the assignee for a task.
 *
 * @author Joachim Van der Auwera
 */
@Component
public class AssignTaskCommand implements Command<AssignTaskRequest, GetReferralResponse> {

	@Autowired
	private GetReferralCommand getReferralCommand;

	@Autowired
	private TaskService taskService;

	public GetReferralResponse getEmptyCommandResponse() {
		return new GetReferralResponse();
	}

	public void execute(AssignTaskRequest request, GetReferralResponse response) throws Exception {
		String taskId = request.getTaskId();
		if (null == taskId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "taskId");
		}
		taskService.setAssignee(taskId, request.getAssignee());
		Object variable = taskService.getVariable(taskId, KtunaxaBpmConstant.VAR_REFERRAL_ID);
		if (null != variable) {
			response.setReferral(getReferralCommand.getReferral(variable.toString()));
		}
	}
}
