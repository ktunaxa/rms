/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that marks the moment when a file has been uploaded to the server and a response has been received.
 * 
 * @author Pieter De Graef
 */
public class FileUploadDoneEvent extends GwtEvent<FileUploadDoneHandler> {

	private String response;

	public FileUploadDoneEvent(String response) {
		this.response = response;
	}

	public Type<FileUploadDoneHandler> getAssociatedType() {
		return FileUploadDoneHandler.TYPE;
	}

	protected void dispatch(FileUploadDoneHandler handler) {
		handler.onFileUploadDone(this);
	}

	public String getResponse() {
		return response;
	}
}