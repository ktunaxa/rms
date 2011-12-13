/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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

}
