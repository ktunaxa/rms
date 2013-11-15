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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.geometry.Crs;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.layer.feature.attribute.ManyToOneAttribute;
import org.geomajas.security.SecurityContext;
import org.geomajas.service.FilterService;
import org.geomajas.service.GeoService;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.referral.ReferralUtil;
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
public class FinishTaskCommand implements Command<FinishTaskRequest, CommandResponse> {

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
		setReferralInProgress(referralId);
	}

	private void setReferralInProgress(String referralId) throws GeomajasException {
		Crs crs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
		List<InternalFeature> features = vectorLayerService.getFeatures(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs,
				filterService.parseFilter(ReferralUtil.createFilter(referralId)), null,
				VectorLayerService.FEATURE_INCLUDE_ATTRIBUTES);
		if (features.size() != 1) {
			if (features.isEmpty()) {
				log.error("Process found but not referral for {}.", referralId);
			} else {
				log.error("Multiple ({}) referrals found for id {}.", features.size(), referralId);
			}
		} else {
			InternalFeature orgReferral = features.get(0);
			InternalFeature referral = orgReferral.clone();
			Map<String, Attribute> attributes = referral.getAttributes();
			Attribute currentStatus = attributes.get(KtunaxaConstant.ATTRIBUTE_STATUS);
			// set in progress if new
			if (KtunaxaConstant.STATUS_NEW.equals(((ManyToOneAttribute) currentStatus).getValue().getAttributeValue(
					KtunaxaConstant.ATTRIBUTE_STATUS_TITLE))) {
				List<InternalFeature> newFeatures = new ArrayList<InternalFeature>();
				newFeatures.add(referral);
				List<Attribute<?>> statusses = vectorLayerService.getAttributes(
						KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, KtunaxaConstant.ATTRIBUTE_STATUS,
						filterService.createTrueFilter());
				for (Attribute<?> status : statusses) {
					if (status instanceof ManyToOneAttribute) {
						if (KtunaxaConstant.STATUS_IN_PROGRESS.equals(((ManyToOneAttribute) status).getValue()
								.getAttributeValue(KtunaxaConstant.ATTRIBUTE_STATUS_TITLE))) {
							attributes.put(KtunaxaConstant.ATTRIBUTE_STATUS, status);
						}
					}
				}
				vectorLayerService.saveOrUpdate(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs, features, newFeatures);
			}
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
