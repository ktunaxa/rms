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

package org.ktunaxa.referral.client.referral;

import org.ktunaxa.referral.client.referral.event.FileUploadDoneEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.NamedFrame;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;

/**
 * Simple form definition for uploading files, and getting some response in the form of a string.
 * 
 * @author Pieter De Graef
 */
public class FileUploadForm extends DynamicForm {

	private HandlerManager handlerManager;

	private UploadItem fileItem;

	public FileUploadForm(String title, String targetUrl) {
		handlerManager = new HandlerManager(this);
		initComplete(this);
		setEncoding(Encoding.MULTIPART);
		setTarget(targetUrl);
		setColWidths(250, "*");
		setCanSubmit(true);
		setAction(targetUrl);
		setWidth100();

		fileItem = new UploadItem("file");
		fileItem.setTitle(title);
		fileItem.setWrapTitle(false);
		fileItem.setWidth("*");
		fileItem.setHeight(25);
		fileItem.addChangeHandler(new ChangeHandler() {

			public void onChange(ChangeEvent event) {
				if (fileItem.getEnteredValue().endsWith(".zip")) {
					// uploadButton.enable();
				} else {
					// uploadButton.disable();
				}
			}
		});
		setFields(fileItem);

		NamedFrame frame = new NamedFrame(targetUrl);
		frame.setSize("1", "1");
		frame.setVisible(false);
		addChild(frame);
	}

	public final HandlerRegistration addFileUploadDoneHandler(final FileUploadDoneHandler handler) {
		return handlerManager.addHandler(FileUploadDoneHandler.TYPE, handler);
	}

	public String getFile() {
		Object obj = fileItem.getValue();
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	// Can we make this private??
	public void uploadComplete(String response) {
		handlerManager.fireEvent(new FileUploadDoneEvent(response));
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private native void initComplete(FileUploadForm upload) /*-{
		$wnd.uploadComplete = function (response) {
			upload.@org.ktunaxa.referral.client.referral.FileUploadForm::uploadComplete(Ljava/lang/String;)(response);
		};
	}-*/;
}