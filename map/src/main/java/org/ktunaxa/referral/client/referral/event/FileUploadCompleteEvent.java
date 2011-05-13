/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Event that marks the moment when a file has been uploaded successfully to the server and a response has been
 * received.
 * 
 * @author Pieter De Graef
 */
public class FileUploadCompleteEvent extends GwtEvent<FileUploadDoneHandler> {

	private JSONObject response;

	public FileUploadCompleteEvent(JSONObject response) {
		this.response = response;
	}

	public Type<FileUploadDoneHandler> getAssociatedType() {
		return FileUploadDoneHandler.TYPE;
	}

	protected void dispatch(FileUploadDoneHandler handler) {
		handler.onFileUploadComplete(this);
	}

	public JSONObject getResponse() {
		return response;
	}

	public String getString(String name) {
		return ((JSONString) response.get(name)).stringValue();
	}
}