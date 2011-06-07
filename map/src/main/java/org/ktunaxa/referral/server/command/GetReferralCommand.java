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
import org.geomajas.layer.feature.SearchCriterion;
import org.geomajas.security.SecurityContext;
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

	public static final String ATTRIBUTE_PRIMARY = "primaryClassificationNumber";

	public static final String ATTRIBUTE_SECONDARY = "secondaryClassificationNumber";

	public static final String ATTRIBUTE_YEAR = "calendarYear";

	public static final String ATTRIBUTE_NUMBER = "number";

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
			searchFeatureRequest.setCriteria(createCriteria(referralId));
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

	private SearchCriterion[] createCriteria(String referralId) {
		String[] values = parse(referralId);
		SearchCriterion[] criteria = new SearchCriterion[4];
		criteria[0] = new SearchCriterion(ATTRIBUTE_PRIMARY, "=", values[0]);
		criteria[1] = new SearchCriterion(ATTRIBUTE_SECONDARY, "=", values[1]);
		criteria[2] = new SearchCriterion(ATTRIBUTE_YEAR, "=", values[2]);
		criteria[3] = new SearchCriterion(ATTRIBUTE_NUMBER, "=", values[3]);
		return criteria;
	}

	private String[] parse(String referralId) {
		String[] result = new String[4];
		int position = referralId.indexOf('-');
		result[0] = referralId.substring(0, position);
		String temp = referralId.substring(position + 1);
		position = temp.indexOf('/');
		result[1] = temp.substring(0, position);
		temp = temp.substring(position + 1);
		position = temp.indexOf('-');
		result[2] = temp.substring(0, position);
		result[3] = temp.substring(position + 1);
		return result;
	}

}
