/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.service;

import org.activiti.engine.task.Task;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.springframework.stereotype.Component;

/**
 * Spring bean implementation of the DTO converter service interface.
 * 
 * @author Pieter De Graef
 */
@Component
public class DtoConverterServiceImpl implements DtoConverterService {

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
			}
		}
		return taskDto;
	}
}