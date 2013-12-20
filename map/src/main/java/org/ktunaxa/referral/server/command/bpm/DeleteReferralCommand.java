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

import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.geometry.Crs;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.feature.InternalFeature;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.command.dto.DeleteReferralRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stop the process for the current given referral and delete it.
 * 
 * @author Jan De Moerloose
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class DeleteReferralCommand extends AbstractReferralCommand implements
		Command<DeleteReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(DeleteReferralCommand.class);

	public CommandResponse getEmptyCommandResponse() {
		return new CommandResponse();
	}

	public void execute(DeleteReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		// delete the associated processes if any
		deleteTasks(referralId);			
		// delete the associated processes if any
		deleteProcesses(referralId);
		// delete the referral
		Crs crs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
		List<InternalFeature> features = vectorLayerService.getFeatures(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs,
				filterService.parseFilter(ReferralUtil.createFilter(referralId)), null,
				VectorLayerService.FEATURE_INCLUDE_ATTRIBUTES);
		vectorLayerService.saveOrUpdate(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs, features,
				new ArrayList<InternalFeature>());

	}
}
