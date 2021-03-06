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
import java.util.Map;

import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.feature.InternalFeature;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.dto.ResetReferralRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stop the process for the current given referral and start a new process.
 * 
 * @author Jan De Moerloose
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class ResetReferralCommand extends AbstractReferralCommand implements
		Command<ResetReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(ResetReferralCommand.class);

	public CommandResponse getEmptyCommandResponse() {
		return new CommandResponse();
	}

	public void execute(ResetReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		InternalFeature referral = findReferral(request.getReferralId());
		if (referral != null) {
			// delete the associated processes if any
			deleteTasks(referralId);			
			// delete the associated processes if any
			deleteProcesses(referralId);
			// start a new process for the referral
			Map<String, Object> context = new HashMap<String, Object>();
			context.put(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId);
			String description = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_PROJECT);
			if (null == description) {
				description = "";
			}
			String email = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_EMAIL);
			Integer engagement = getPrimitiveAttributeValue(referral,
					KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_PROVINCE);
			Date deadline = getPrimitiveAttributeValue(referral, KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE);
			DateFormat yyyymmdd = new SimpleDateFormat(KtunaxaBpmConstant.DATE_FORMAT);
			context.put(KtunaxaBpmConstant.VAR_REFERRAL_NAME, description);
			context.put(KtunaxaBpmConstant.VAR_EMAIL, email);
			context.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL, engagement);
			context.put(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL, engagement);
			context.put(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE, yyyymmdd.format(deadline));
			runtimeService.startProcessInstanceByKey(KtunaxaBpmConstant.REFERRAL_PROCESS_ID, context);
			// update status 
			updateStatus(referral, KtunaxaConstant.STATUS_IN_PROGRESS);
		} else {
			log.warn("Referral " + referralId + " not found");
			throw new GeomajasException(ExceptionCode.UNEXPECTED_PROBLEM, "referral " + referralId + " not found");
		}

	}

}
