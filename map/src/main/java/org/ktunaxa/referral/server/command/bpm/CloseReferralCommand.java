/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.security.SecurityContext;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.command.dto.CloseReferralRequest;
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
public class CloseReferralCommand implements Command<CloseReferralRequest, CommandResponse> {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private SecurityContext securityContext;

	public CommandResponse getEmptyCommandResponse() {
		return new CommandResponse();
	}

	public void execute(CloseReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
				variableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId).singleResult();
		if (!processInstance.isEnded()) {
			// @todo first update the referral status to ended...

			runtimeService.deleteProcessInstance(processInstance.getId(),
					"Requested by user " + securityContext.getUserId());
		}
	}
}
