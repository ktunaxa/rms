/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that marks a successful upload and recieval of the referral geometry by one of the three methods.
 * 
 * @author Jan De Moerloose
 * 
 */
public class GeometryUploadSuccessEvent extends GwtEvent<GeometryUploadHandler> {

	public Type<GeometryUploadHandler> getAssociatedType() {
		return GeometryUploadHandler.TYPE;
	}

	@Override
	protected void dispatch(GeometryUploadHandler handler) {
		handler.onUploadSuccess(this);
	}

}
