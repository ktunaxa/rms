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

package org.ktunaxa.referral.server.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.security.AppSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Spring bean implementation of the DTO converter service interface.
 * 
 * @author Pieter De Graef
 * @author Joachim Van der Auwera
 */
@Component
public class DtoConverterServiceImpl implements DtoConverterService {

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private AppSecurityContext securityContext;

	public ReferenceLayerTypeDto toDto(ReferenceLayerType layerType) {
		ReferenceLayerTypeDto dto = new ReferenceLayerTypeDto(layerType.getId());
		dto.setBaseLayer(layerType.isBaseLayer());
		dto.setDescription(layerType.getDescription());
		return dto;
	}

	public ReferenceLayerDto toDto(ReferenceLayer layer) {
		ReferenceLayerDto dto = new ReferenceLayerDto(layer.getId());
		dto.setCode(layer.getCode());
		dto.setName(layer.getName());
		dto.setScaleMax(layer.getScaleMax());
		dto.setScaleMin(layer.getScaleMin());
		dto.setVisibleByDefault(layer.isVisibleByDefault());
		dto.setType(toDto(layer.getType()));
		return dto;
	}

	public TaskDto toDto(Task task) {
		TaskDto taskDto = new TaskDto();
		taskDto.setAssignee(task.getAssignee());
		taskDto.setDescription(task.getDescription());
		taskDto.setExecutionId(task.getExecutionId());
		taskDto.setCreateTime(task.getCreateTime());
		taskDto.setDueDate(task.getDueDate());
		taskDto.setId(task.getId());
		taskDto.setName(task.getName());
		taskDto.setOwner(task.getOwner());
		taskDto.setPriority(task.getPriority());
		taskDto.setProcessDefinitionId(task.getProcessDefinitionId());
		taskDto.setProcessInstanceId(task.getProcessInstanceId());
		taskDto.setTaskDefinitionKey(task.getTaskDefinitionKey());
		if (task.getDelegationState() != null) {
			switch (task.getDelegationState()) {
				case PENDING:
					taskDto.setDelegationState(TaskDto.DelegationState.PENDING);
					break;
				case RESOLVED:
					taskDto.setDelegationState(TaskDto.DelegationState.RESOLVED);
					break;
				default:
					throw new IllegalStateException("Unknown DelegationState " + task.getDelegationState());
			}
		}
		List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
		String userId = securityContext.getUserId();
		boolean authorized = false;
		if (null != userId) {
			authorized = userId.equals(task.getAssignee());
		}
		for (IdentityLink link : identityLinks) {
			String type = link.getType();
			if (IdentityLinkType.CANDIDATE.equals(type)) {
				if (null != link.getGroupId()) {
					authorized |= securityContext.getBpmRoles().contains(link.getGroupId());
					taskDto.addCandidate(link.getGroupId());
				}
				if (null != link.getUserId()) {
					authorized |= link.getUserId().equals(userId);
					taskDto.addCandidate(link.getUserId());
				}
			}
		}
		if (!authorized) {
			return null;
		}
		Map<String, Object> variables = taskService.getVariables(task.getId());
		for (Map.Entry<String, Object> variable : variables.entrySet()) {
			taskDto.addVariable(variable.getKey(), toString(variable.getValue()));
		}

		TaskFormData data = formService.getTaskFormData(task.getId());
		taskDto.setFormKey(data.getFormKey());

		return taskDto;
	}

	public TaskDto toDto(HistoricTaskInstance task) {
		TaskDto taskDto = new TaskDto();
		taskDto.setHistory(true);
		taskDto.setAssignee(task.getAssignee());
		taskDto.setDescription(task.getDescription());
		taskDto.setExecutionId(task.getExecutionId());
		taskDto.setStartTime(task.getStartTime());
		taskDto.setEndTime(task.getEndTime());
		taskDto.setDueDate(task.getDueDate());
		taskDto.setId(task.getId());
		taskDto.setName(task.getName());
		taskDto.setPriority(task.getPriority());
		taskDto.setProcessDefinitionId(task.getProcessDefinitionId());
		taskDto.setProcessInstanceId(task.getProcessInstanceId());
		taskDto.setTaskDefinitionKey(task.getTaskDefinitionKey());
		/*
		HistoricDetail historicDetail = historyService.createHistoricDetailQuery().taskId(task.getId()).singleResult();
		Map<String, Object> variables = historyService.createHistoricDetailQuery().taskId(task.getId()).singleResult();
		for (Map.Entry<String, Object> variable : variables.entrySet()) {
			taskDto.addVariable(variable.getKey(), toString(variable.getValue()));
		}
		*/

		return taskDto;
	}

	private String toString(Object obj) {
		if (null == obj) {
			return null;
		}
		return obj.toString();
	}
}