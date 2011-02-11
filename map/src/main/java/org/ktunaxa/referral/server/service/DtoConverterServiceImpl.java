/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.ktunaxa.referral.server.service;

import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.springframework.stereotype.Component;

/**
 * Spring bean implementation of the DTO converter service interface.
 * 
 * @author Pieter De Graef
 */
@Component
public class DtoConverterServiceImpl implements DtoConverterService {

	public ReferenceLayerTypeDto toDto(ReferenceLayerType layerType) {
		ReferenceLayerTypeDto dto = new ReferenceLayerTypeDto(layerType.getId());
		dto.setBaseLayer(layerType.isBaseLayer());
		dto.setDescription(layerType.getDescription());
		return dto;
	}

	public ReferenceLayerDto toDto(ReferenceLayer layer) {
		ReferenceLayerDto dto = new ReferenceLayerDto(layer.getId());
		dto.setName(layer.getName());
		dto.setScaleMax(layer.getScaleMax());
		dto.setScaleMin(layer.getScaleMin());
		dto.setVisibleByDefault(layer.isVisibleByDefault());
		dto.setType(toDto(layer.getType()));
		return dto;
	}
}