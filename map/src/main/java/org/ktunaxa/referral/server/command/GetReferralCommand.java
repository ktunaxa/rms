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

package org.ktunaxa.referral.server.command;

import org.geomajas.command.Command;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.CommandResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to get details of a referral.
 * 
 * @author Jan De Moerloose
 */
@Component
public class GetReferralCommand implements Command<GetReferralRequest, GetReferralResponse> {

	private final Logger log = LoggerFactory.getLogger(GetReferralCommand.class);

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private SecurityContext securityContext;

	public GetReferralResponse getEmptyCommandResponse() {
		return new GetReferralResponse();
	}

	public void execute(GetReferralRequest request, GetReferralResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}

		// Add the referral to the response:
		response.setReferral(getReferral(referralId));
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
			searchFeatureRequest.setLayerId(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID);
			searchFeatureRequest.setMax(1);
			searchFeatureRequest.setFeatureIncludes(GeomajasConstant.FEATURE_INCLUDE_ALL);
			CommandResponse commandResponse = commandDispatcher.execute(
					SearchFeatureRequest.COMMAND, searchFeatureRequest, securityContext.getToken(), null);
			if (commandResponse instanceof SearchFeatureResponse) {
				SearchFeatureResponse searchFeatureResponse = (SearchFeatureResponse) commandResponse;
				Feature[] features = searchFeatureResponse.getFeatures();
				if (null != features && features.length > 0) {
					return searchFeatureResponse.getFeatures()[0];
				}
			} else {
				log.error("Search referral failed {} {}", commandResponse.isError(), commandResponse.getExceptions());
			}
		}
		return null;
	}

}
