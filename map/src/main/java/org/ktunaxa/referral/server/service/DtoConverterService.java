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

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * General interface for converting domain objects to their DTO counterparts and vice versa.
 * 
 * @author Pieter De Graef
 */
public interface DtoConverterService {

	/**
	 * Convert a layer type to a DTO.
	 * 
	 * @param layerType
	 *            The layer type to convert.
	 * @return Returns the DTO version of the layer type.
	 */
	ReferenceLayerTypeDto toDto(ReferenceLayerType layerType);

	/**
	 * Convert a reference layer to a DTO.
	 * 
	 * @param layer
	 *            The reference layer to convert.
	 * @return Returns the DTO version of the reference layer.
	 */
	ReferenceLayerDto toDto(ReferenceLayer layer);

	/**
	 * Convert an Activiti task to a DTO.
	 * 
	 * @param task The task to convert or null when not authorized to see the task
	 * @return Returns the DTO version of the task.
	 */
	TaskDto toDto(Task task);

	/**
	 * Convert an Activiti task to a DTO.
	 *
	 * @param task
	 *            The task to convert.
	 * @return Returns the DTO version of the task.
	 */
	TaskDto toDto(HistoricTaskInstance task);
}