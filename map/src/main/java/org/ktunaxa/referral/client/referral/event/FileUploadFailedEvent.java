/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral.event;

import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Event that marks the moment when a file has been uploaded to the server and a failure occurred.
 * 
 * @author Jan De Moerloose
 * 
 */
public class FileUploadFailedEvent extends GwtEvent<FileUploadDoneHandler> {

	private JSONObject error;

	public FileUploadFailedEvent(JSONObject error) {
		this.error = error;
	}

	public Type<FileUploadDoneHandler> getAssociatedType() {
		return FileUploadDoneHandler.TYPE;
	}

	protected void dispatch(FileUploadDoneHandler handler) {
		handler.onFileUploadFailed(this);
	}

	public JSONObject getError() {
		return error;
	}

	public String getString(String name) {
		return ((JSONString) error.get(name)).stringValue();
	}

	public String getErrorMessage() {
		return getString(KtunaxaConstant.FORM_ERROR_MESSAGE);
	}
}
