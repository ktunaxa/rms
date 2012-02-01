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

import org.activiti.engine.RuntimeService;
import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.dto.CreateProcessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Create a referral handling process.
 *
 * @author Joachim Van der Auwera
 */
@Component
public class CreateProcessCommand implements Command<CreateProcessRequest, CommandResponse> {

	@Autowired
	private RuntimeService runtimeService;

	public CommandResponse getEmptyCommandResponse() {
		return new CommandResponse();
	}

	public void execute(CreateProcessRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		String email = request.getEmail();
		if (null == email) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "email");
		}
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId);
		String description = request.getDescription();
		if (null == description) {
			description = "";
		}
		DateFormat yyyymmdd = new SimpleDateFormat(KtunaxaBpmConstant.DATE_FORMAT);
		context.put(KtunaxaBpmConstant.VAR_REFERRAL_NAME, description);
		context.put(KtunaxaBpmConstant.VAR_EMAIL, email);
		context.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL, request.getEngagementLevel());
		context.put(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL, request.getEngagementLevel());
		context.put(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE,
				yyyymmdd.format(request.getCompletionDeadline()));
		runtimeService.startProcessInstanceByKey(KtunaxaBpmConstant.REFERRAL_PROCESS_ID, context);
	}
}
