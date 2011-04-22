/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.RuntimeService;
import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.bpm.KtunaxaConfiguration;
import org.ktunaxa.referral.server.command.request.CreateProcessRequest;
import org.ktunaxa.referral.server.command.request.UrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Create a referral handling process.
 *
 * @author Joachim Van der Auwera
 */
@Component
public class CreateProcessCommand implements Command<CreateProcessRequest, UrlResponse> {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private KtunaxaConfiguration ktunaxaConfiguration;

	public UrlResponse getEmptyCommandResponse() {
		return new UrlResponse();
	}

	public void execute(CreateProcessRequest request, UrlResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		String email = request.getEmail();
		if (null == email) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "email");
		}
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(KtunaxaConfiguration.REFERRAL_CONTEXT_REFERRAL_ID, referralId);
		String description = request.getDescription();
		if (null == description) {
			description = "";
		}
		context.put(KtunaxaConfiguration.REFERRAL_CONTEXT_DESCRIPTION, description);
		context.put(KtunaxaConfiguration.REFERRAL_CONTEXT_EMAIL, email);
		context.put(KtunaxaConfiguration.REFERRAL_CONTEXT_ENGAGEMENT_LEVEL, request.getEngagementLevel());
		context.put(KtunaxaConfiguration.REFERRAL_CONTEXT_COMPLETION_DEADLINE, request.getCompletionDeadline());
		runtimeService.startProcessInstanceByKey(KtunaxaConfiguration.REFERRAL_PROCESS_ID, context);
		response.setUrl(ktunaxaConfiguration.getBpmDashboardBaseUrl());
	}
}
