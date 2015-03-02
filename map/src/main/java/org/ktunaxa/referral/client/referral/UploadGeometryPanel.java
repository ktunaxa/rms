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
package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.map.feature.Feature;
import org.ktunaxa.referral.client.referral.event.GeometryUploadHandler;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Common interface for the 3 upload panels (shape, geomark, point).
 * It is also used for the {@link AttachDocumentPage} to get the referral...
 * 
 * @author Jan De Moerloose
 */
public interface UploadGeometryPanel {

	/**
	 * Set the feature for which the geometry needs to be set.
	 *
	 * @param feature feature
	 */
	void setFeature(Feature feature);

	/**
	 * Add handler which can be used to display the selected geometry on the map.
	 *
	 * @param handler geometry upload handler
	 * @return handler registration
	 */
	HandlerRegistration addGeometryUploadHandler(GeometryUploadHandler handler);

	/**
	 * Validate the geometry which is selected on this page and set the geometry in the feature.
	 *
	 * @return true when page validation is successful
	 */
	boolean validate();
	
	/**
	 * Clear user data.
	 */
	void clearData();

}
