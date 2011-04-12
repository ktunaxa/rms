/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event handler for file upload notifications. Fires events when a file has been uploaded and a response has been
 * received.
 * 
 * @author Pieter De Graef
 */
public interface FileUploadDoneHandler extends EventHandler {

	GwtEvent.Type<FileUploadDoneHandler> TYPE = new GwtEvent.Type<FileUploadDoneHandler>();

	void onFileUploadComplete(FileUploadCompleteEvent event);

	void onFileUploadFailed(FileUploadFailedEvent event);
}