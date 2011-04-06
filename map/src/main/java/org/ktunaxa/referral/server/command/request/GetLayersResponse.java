/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.server.command.request;

import java.util.List;

import org.geomajas.command.CommandResponse;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;

/**
 * Response object that sends reference layer meta-data back to the client.
 * 
 * @author Pieter De Graef
 */
public class GetLayersResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private List<ReferenceLayerTypeDto> layerTypes;

	private List<ReferenceLayerDto> layers;

	public GetLayersResponse() {
	}

	public List<ReferenceLayerDto> getLayers() {
		return layers;
	}

	public void setLayers(List<ReferenceLayerDto> layers) {
		this.layers = layers;
	}

	public List<ReferenceLayerTypeDto> getLayerTypes() {
		return layerTypes;
	}

	public void setLayerTypes(List<ReferenceLayerTypeDto> layerTypes) {
		this.layerTypes = layerTypes;
	}
}