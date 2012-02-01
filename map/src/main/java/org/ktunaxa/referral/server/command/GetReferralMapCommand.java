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

import java.util.ArrayList;
import java.util.List;

import org.geomajas.command.Command;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.dto.GetConfigurationRequest;
import org.geomajas.command.dto.GetConfigurationResponse;
import org.ktunaxa.referral.server.command.dto.GetReferralMapRequest;
import org.ktunaxa.referral.server.command.dto.GetReferralMapResponse;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.security.AppSecurityContext;
import org.ktunaxa.referral.server.service.CmisService;
import org.ktunaxa.referral.server.service.DtoConverterService;
import org.ktunaxa.referral.server.service.ReferenceLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to get the RFA map configuration and some additional data.
 * 
 * @author Jan De Moerloose
 * @author Joachim Van der Auwera
 */
@Component
public class GetReferralMapCommand implements Command<GetReferralMapRequest, GetReferralMapResponse> {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private AppSecurityContext securityContext;

	@Autowired
	private ReferenceLayerService referenceLayerService;

	@Autowired
	private DtoConverterService converterService;

	@Autowired
	private CmisService cmisService;

	public GetReferralMapResponse getEmptyCommandResponse() {
		return new GetReferralMapResponse();
	}

	public void execute(GetReferralMapRequest request, GetReferralMapResponse response) throws Exception {
		GetConfigurationResponse original = (GetConfigurationResponse) commandDispatcher.execute(
				GetConfigurationRequest.COMMAND, request, securityContext.getToken(), null);
		response.setApplication(original.getApplication());

		// Add the urls to the response:
		response.setCmisBaseUrl(cmisService.getBaseUrl());

		// Add the layer types to the response:
		List<ReferenceLayerType> layerTypes = referenceLayerService.findLayerTypes();
		List<ReferenceLayerTypeDto> typeDtos = new ArrayList<ReferenceLayerTypeDto>(layerTypes.size());
		for (ReferenceLayerType layerType : layerTypes) {
			typeDtos.add(converterService.toDto(layerType));
		}
		response.setLayerTypes(typeDtos);

		// Add the layers to the response:
		List<ReferenceLayer> layers = referenceLayerService.findLayers();
		List<ReferenceLayerDto> dtos = new ArrayList<ReferenceLayerDto>(layers.size());
		for (ReferenceLayer layer : layers) {
			dtos.add(converterService.toDto(layer));
		}
		response.setLayers(dtos);

		response.setBpmRoles(securityContext.getBpmRoles());
		response.setAdmin(securityContext.isAdmin());
	}

}
