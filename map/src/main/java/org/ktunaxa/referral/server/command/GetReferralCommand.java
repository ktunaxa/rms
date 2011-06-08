/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command;

import org.geomajas.command.Command;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.dto.SearchFeatureRequest;
import org.geomajas.command.dto.SearchFeatureResponse;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasConstant;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.feature.Feature;
import org.geomajas.security.SecurityContext;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.command.dto.GetReferralRequest;
import org.ktunaxa.referral.server.command.dto.GetReferralResponse;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to get details of a referral.
 * 
 * @author Jan De Moerloose
 */
@Component
public class GetReferralCommand implements Command<GetReferralRequest, GetReferralResponse> {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private SecurityContext securityContext;

	public GetReferralResponse getEmptyCommandResponse() {
		return new GetReferralResponse();
	}

	public void execute(GetReferralRequest request, GetReferralResponse response) throws Exception {
		String taskId = request.getReferralId();
		if (null == taskId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "taskId");
		}

		// Add the referral to the response:
		response.setReferral(getReferral(request.getReferralId()));
	}

	/**
	 * Get the referral feature object for a feature id.
	 *
	 * @param referralId referral id
	 * @return referral feature
	 */
	public Feature getReferral(String referralId) {
		if (null != referralId) {
			SearchFeatureRequest searchFeatureRequest = new SearchFeatureRequest();
			searchFeatureRequest.setBooleanOperator("AND");
			searchFeatureRequest.setCriteria(ReferralUtil.createCriteria(referralId));
			searchFeatureRequest.setCrs(KtunaxaConstant.MAP_CRS);
			searchFeatureRequest.setLayerId(KtunaxaConstant.REFERRAL_SERVER_LAYER_ID);
			searchFeatureRequest.setMax(1);
			searchFeatureRequest.setFeatureIncludes(GeomajasConstant.FEATURE_INCLUDE_ALL);
			SearchFeatureResponse searchFeatureResponse = (SearchFeatureResponse) commandDispatcher.execute(
					SearchFeatureRequest.COMMAND, searchFeatureRequest, securityContext.getToken(), null);
			if (searchFeatureResponse.getFeatures().length > 0) {
				return searchFeatureResponse.getFeatures()[0];
			}
		}
		return null;
	}

}
