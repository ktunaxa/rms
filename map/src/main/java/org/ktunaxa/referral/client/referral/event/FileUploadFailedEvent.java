/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that marks the moment when a file has been uploaded to the server and a failure occurred.
 * @author Jan De Moerloose
 *
 */
public class FileUploadFailedEvent extends GwtEvent<FileUploadDoneHandler> {

	private String error;

	public FileUploadFailedEvent(String error) {
		this.error = error;
	}

	public Type<FileUploadDoneHandler> getAssociatedType() {
		return FileUploadDoneHandler.TYPE;
	}

	protected void dispatch(FileUploadDoneHandler handler) {
		handler.onFileUploadFailed(this);
	}

	public String getError() {
		return error;
	}
}
