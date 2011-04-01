/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
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

	void onFileUploadDone(FileUploadDoneEvent event);
}