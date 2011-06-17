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
 * 
 * @author Jan De Moerloose
 * 
 */
public interface UploadGeometryPanel {

	void setFeature(Feature feature);

	HandlerRegistration addGeometryUploadHandler(GeometryUploadHandler handler);
}
