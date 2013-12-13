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

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
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
import org.ktunaxa.referral.server.command.dto.CloseReferralRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stop the process for the current given referral and mark it as stopped.
 * 
 * @author Joachim Van der Auwera
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class CloseReferralCommand extends AbstractReferralCommand implements
		Command<CloseReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(CloseReferralCommand.class);

	@Autowired
	private RuntimeService runtimeService;

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

	public void execute(CloseReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		deleteProcesses(referralId);
		// find referral, update status and set reason
		InternalFeature referral = findReferral(request.getReferralId());
		if(referral != null) {
			updateStatus(referral, KtunaxaConstant.STATUS_STOPPED, request.getReason());
		} else {
			log.warn("Referral " + referralId + " not found");
			throw new GeomajasException(ExceptionCode.UNEXPECTED_PROBLEM, "referral " + referralId + " not found");
		}
	}
}
