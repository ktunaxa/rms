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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
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
import org.ktunaxa.referral.server.command.dto.FinishReferralRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Fast-forward the current given referral through all the tasks and put it in finished state.
 * 
 * @author Jan De Moerloose
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class FinishReferralCommand extends AbstractReferralCommand implements
		Command<FinishReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(FinishReferralCommand.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

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

	public void execute(FinishReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		// find referral
		InternalFeature referral = findReferral(request.getReferralId());
		if (referral != null) {
			// finish all tasks
			if (null != referralId) {
				TaskQuery taskQuery = taskService.createTaskQuery();
				List<Task> tasks = taskQuery.processVariableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId)
						.list();
				for (Task task : tasks) {
					taskService.complete(task.getId(), convertToObjects(referral));
				}
			}
			deleteProcesses(referralId);
			// update status
			updateStatus(referral, KtunaxaConstant.STATUS_FINISHED);
		} else {
			log.warn("Referral " + referralId + " not found");
			throw new GeomajasException(ExceptionCode.UNEXPECTED_PROBLEM, "referral " + referralId + " not found");
		}

	}

	private Map<String, Object> convertToObjects(InternalFeature referral) {
		Map<String, Object> result = new HashMap<String, Object>();

		DateFormat yyyymmdd = new SimpleDateFormat(KtunaxaBpmConstant.DATE_FORMAT);
		String fullId = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_FULL_ID);
		String projectName = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_PROJECT);
		String email = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_EMAIL);
		Integer provinceEngagementLevel = getPrimitiveAttributeValue(referral,
				KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_PROVINCE);
		Integer engagementLevel = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL);
		String engagementComment = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_COMMENT_CONTENT);
		Date completionDeadline = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE);

		result.put(KtunaxaBpmConstant.VAR_REFERRAL_ID, fullId);
		result.put(KtunaxaBpmConstant.VAR_REFERRAL_NAME, projectName);
		result.put(KtunaxaBpmConstant.VAR_EMAIL, email);
		result.put(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL, provinceEngagementLevel);
		result.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL, engagementLevel);
		result.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT, engagementComment);
		result.put(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE, yyyymmdd.format(completionDeadline));
		result.put(KtunaxaBpmConstant.VAR_NEED_CHANGE_NOTIFICATION, false);
		result.put(KtunaxaBpmConstant.VAR_INCOMPLETE, false);
		result.put(KtunaxaBpmConstant.VAR_FINAL_DECISION_CONSISTENT, true);
		result.put(KtunaxaBpmConstant.VAR_REPORT_VALUES, true);
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_AQUATIC, false);
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_ARCHAEOLOGICAL, false);
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_CULTURAL, false);
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_TERRESTRIAL, false);
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_TREATY, false);
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_A_INPUT, false);
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_B_INPUT, false);
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_C_INPUT, false);
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_D_INPUT, false);
		return result;
	}

}
