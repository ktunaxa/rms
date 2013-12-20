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

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.security.SecurityContext;
import org.geomajas.service.FilterService;
import org.geomajas.service.GeoService;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.dto.FinishTaskRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Finish a task and return the BPM dashboard URL (which should be redirected to).
 * 
 * @author Joachim Van der Auwera
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class FinishTaskCommand extends AbstractReferralCommand implements Command<FinishTaskRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(FinishTaskCommand.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private SecurityContext securityContext;

	@Autowired
	private VectorLayerService vectorLayerService;

	@Autowired
	private FilterService filterService;

	@Autowired
	private GeoService geoService;

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
		String referralId = (String) taskService.getVariable(taskId, KtunaxaBpmConstant.VAR_REFERRAL_ID);
		if (null != task) {
			taskService.complete(task.getId(), convertToObjects(variables));
		} else {
			throw new GeomajasException(ExceptionCode.UNEXPECTED_PROBLEM, "task " + taskId + " not found");
		}
		// find referral, update status and set reason
		InternalFeature referral = findReferral(referralId);
		if (referral != null) {
			updateStatus(referral, KtunaxaConstant.STATUS_IN_PROGRESS);
		} else {
			log.warn("Referral " + referralId + " not found");
			throw new GeomajasException(ExceptionCode.UNEXPECTED_PROBLEM, "referral " + referralId + " not found");
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
