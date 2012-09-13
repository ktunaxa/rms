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
import java.util.List;

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
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.security.SecurityContext;
import org.geomajas.service.FilterService;
import org.geomajas.service.GeoService;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.command.dto.DeleteReferralRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stop the process for the current given referral and delete it.
 * 
 * @author Jan De Moerloose
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class DeleteReferralCommand implements Command<DeleteReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(DeleteReferralCommand.class);

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

	public void execute(DeleteReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
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
		// find referral, update status and set reason
		Crs crs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
		List<InternalFeature> features = vectorLayerService.getFeatures(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID,
				crs,
				filterService.parseFilter(ReferralUtil.createFilter(referralId)), null,
				VectorLayerService.FEATURE_INCLUDE_ATTRIBUTES);
		vectorLayerService.saveOrUpdate(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs, features,
				new ArrayList<InternalFeature>());

	}
}
