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

package org.geomajas.gwt2.plugin.wms.client.capabilities;

import java.io.Serializable;
import java.util.List;

import org.geomajas.annotation.Api;

/**
 * Generic WMS GetCapabilities definition.
 *
 * @author Pieter De Graef
 * @since 2.1.0
 */
@Api
public interface WmsGetCapabilitiesInfo extends Serializable {

	/**
	 * Retrieve the list of supported request types for this WMS server.
	 *
	 * @return The list of supported requests.
	 */
	List<WmsRequestInfo> getRequests();

	/**
	 * Retrieve the list of layers defined in the capabilities file.
	 *
	 * @return The full list of layers.
	 */
	List<WmsLayerInfo> getLayers();

	/**
	 * Retrieve the list of layers defined in the capabilities file.
	 *
	 * @return The full list of layers.
	 */
	WmsLayerInfo getRootLayer();
}
