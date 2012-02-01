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

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.geomajas.command.Command;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.DtoConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Get a list of tasks, which tasks depends on the request.
 * You can request unassigned tasks, task for an assignee or tasks for a process instance.
 * 
 * @author Joachm Van der Auwera
 */
@Component
public class GetTasksCommand implements Command<GetTasksRequest, GetTasksResponse> {

	private final Logger log = LoggerFactory.getLogger(GetTasksCommand.class);
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private DtoConverterService converterService;


	public GetTasksResponse getEmptyCommandResponse() {
		return new GetTasksResponse();
	}

	public void execute(GetTasksRequest request, GetTasksResponse response) throws Exception {

		log.debug("GetTasksCommand started");
		List<TaskDto> taskDtos = new ArrayList<TaskDto>();
		response.setTasks(taskDtos);

		if (request.isIncludeUnassignedTasks()) {
			TaskQuery taskQuery = taskService.createTaskQuery();
			add(taskDtos, taskQuery.taskUnnassigned().list());
		}
		if (null != request.getAssignee()) {
			TaskQuery taskQuery = taskService.createTaskQuery();
			add(taskDtos, taskQuery.taskAssignee(request.getAssignee()).list());
		}
		String referralId = request.getReferralId();
		if (null != referralId) {
			TaskQuery taskQuery = taskService.createTaskQuery();
			add(taskDtos, taskQuery.processVariableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID,
					referralId).list());
			HistoricTaskInstanceQuery historicTaskQuery = historyService.createHistoricTaskInstanceQuery();
			addHistoric(taskDtos, historicTaskQuery.finished().
					processVariableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId).list());
		}
		log.debug("GetTasksCommand finished. {} were retrieved.", taskDtos.size());
	}

	private void add(List<TaskDto> taskDtos, List<Task> tasks) {
		for (Task task : tasks) {
			TaskDto dto = converterService.toDto(task);
			if (null != dto) {
				taskDtos.add(dto);
				log.debug("{}: {} added.", dto.getId(), dto.getDescription());
			}
		}
	}

	private void addHistoric(List<TaskDto> taskDtos, List<HistoricTaskInstance> tasks) {
		for (HistoricTaskInstance task : tasks) {
			taskDtos.add(converterService.toDto(task));
		}
	}

}
