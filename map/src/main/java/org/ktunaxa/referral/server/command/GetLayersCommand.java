/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.command.Command;
import org.ktunaxa.referral.server.command.dto.GetLayersRequest;
import org.ktunaxa.referral.server.command.dto.GetLayersResponse;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.service.DtoConverterService;
import org.ktunaxa.referral.server.service.ReferenceLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command for fetching reference layer meta-data.
 * 
 * @author Pieter De Graef
 */
@Component
public class GetLayersCommand implements Command<GetLayersRequest, GetLayersResponse> {

	@Autowired
	private ReferenceLayerService referenceLayerService;

	@Autowired
	private DtoConverterService converterService;

	public GetLayersResponse getEmptyCommandResponse() {
		return new GetLayersResponse();
	}

	public void execute(GetLayersRequest request, GetLayersResponse response) throws Exception {
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
	}
}