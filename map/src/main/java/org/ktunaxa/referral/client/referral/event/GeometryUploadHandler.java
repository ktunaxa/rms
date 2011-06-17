/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * {@link EventHandler} for geometry upload events. Called when the geometry is successfully returned to the client.
 * 
 * @author Jan De Moerloose
 * 
 */
public interface GeometryUploadHandler extends EventHandler {

	GwtEvent.Type<GeometryUploadHandler> TYPE = new GwtEvent.Type<GeometryUploadHandler>();

	void onUploadSuccess(GeometryUploadSuccessEvent event);
}
