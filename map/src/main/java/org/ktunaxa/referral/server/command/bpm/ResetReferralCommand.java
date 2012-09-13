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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
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
import org.ktunaxa.referral.server.command.dto.ResetReferralRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stop the process for the current given referral and start a new process.
 * 
 * @author Jan De Moerloose
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class ResetReferralCommand implements Command<ResetReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(ResetReferralCommand.class);

	@Autowired
	private RuntimeService runtimeService;

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

	public void execute(ResetReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		// find referral
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
			// referral found
			InternalFeature orgReferral = features.get(0);
			// stop the existing process if any
			List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
					.variableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId).list();
			if (!processInstances.isEmpty()) {
				// stop process instances (contrary to the names, these are all the executions for the process instance)
				for (ProcessInstance processInstance : processInstances) {
					ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
					// Must check if the instance is still there as delete cascades to sub-processes !
					if (!query.processInstanceId(processInstance.getId()).list().isEmpty()) {
						runtimeService.deleteProcessInstance(processInstance.getId(), "Requested by user "
								+ securityContext.getUserId());
						historyService.deleteHistoricProcessInstance(processInstance.getId());
					}
				}
			}
			// start a new process for the referral
			Map<String, Object> context = new HashMap<String, Object>();
			context.put(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId);
			String description = getPrimitiveAttributeValue(orgReferral, KtunaxaConstant.ATTRIBUTE_PROJECT);
			if (null == description) {
				description = "";
			}
			String email = getPrimitiveAttributeValue(orgReferral, KtunaxaConstant.ATTRIBUTE_EMAIL);
			Integer engagement = getPrimitiveAttributeValue(orgReferral,
					KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_PROVINCE);
			Date deadline = getPrimitiveAttributeValue(orgReferral, KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE);
			DateFormat yyyymmdd = new SimpleDateFormat(KtunaxaBpmConstant.DATE_FORMAT);
			context.put(KtunaxaBpmConstant.VAR_REFERRAL_NAME, description);
			context.put(KtunaxaBpmConstant.VAR_EMAIL, email);
			context.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL, engagement);
			context.put(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL, engagement);
			context.put(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE, yyyymmdd.format(deadline));
			runtimeService.startProcessInstanceByKey(KtunaxaBpmConstant.REFERRAL_PROCESS_ID, context);
			
			// update the status to in progress
			InternalFeature referral = orgReferral.clone();
			Map<String, Attribute> attributes = referral.getAttributes();
			List<InternalFeature> newFeatures = new ArrayList<InternalFeature>();
			newFeatures.add(referral);
			List<Attribute<?>> statusses = vectorLayerService.getAttributes(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID,
					KtunaxaConstant.ATTRIBUTE_STATUS, filterService.createTrueFilter());
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

	private <T> T getPrimitiveAttributeValue(InternalFeature feature, String name) {
		return (T) feature.getAttributes().get(name).getValue();
	}
}
