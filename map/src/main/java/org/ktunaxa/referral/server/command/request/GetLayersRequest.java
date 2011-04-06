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

import org.geomajas.command.CommandRequest;

/**
 * Request object to for fetching reference layer meta-data. We could add some filtering options here....
 * 
 * @author Pieter De Graef
 */
public class GetLayersRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

	private String layerType;

	public GetLayersRequest() {
	}

	public String getLayerType() {
		return layerType;
	}

	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}
}