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
