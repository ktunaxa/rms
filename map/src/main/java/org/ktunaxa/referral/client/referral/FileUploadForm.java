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

package org.ktunaxa.referral.client.referral;

import org.ktunaxa.referral.client.referral.event.FileUploadCompleteEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;
import org.ktunaxa.referral.client.referral.event.FileUploadFailedEvent;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.NamedFrame;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.UploadItem;

/**
 * Simple form definition for uploading files, and getting some response in the form of a string.
 * 
 * @author Pieter De Graef
 */
public class FileUploadForm extends DynamicForm {

	private HandlerManager handlerManager;

	private UploadItem fileItem;

	private String targetUrl;

	public FileUploadForm(String title, String targetUrl, String referralId) {
		super();
		if (!targetUrl.contains("?")) {
			this.targetUrl = targetUrl + "?";
		} else {
			this.targetUrl = targetUrl + "&";
		}
		this.targetUrl += KtunaxaConstant.FORM_ID + "=" + getID();
		if (referralId != null) {
			setReferralId(referralId);
		}
		String targetFrame = "resFrame" + getID();
		handlerManager = new HandlerManager(this);
		initComplete(this);
		setEncoding(Encoding.MULTIPART);
		setTarget(targetFrame);
		setCanSubmit(true);
		setWidth100();

		fileItem = new UploadItem(KtunaxaConstant.FORM_FILE);
		fileItem.setTitle(title);
		fileItem.setWrapTitle(false);
		fileItem.setWidth("*");
		fileItem.setHeight(24);
		fileItem.setCellHeight(24);
		/*
		 * fileItem.addChangeHandler(new ChangeHandler() { public void onChange(ChangeEvent event) { if
		 * (fileItem.getEnteredValue().endsWith(".zip")) { uploadButton.enable(); } else { uploadButton.disable(); } }
		 * });
		 */
		setFields(fileItem);

		NamedFrame frame = new NamedFrame(targetFrame);
		frame.setSize("1", "1");
		frame.setVisible(false);
		addChild(frame);
	}

	public void setReferralId(String referralId) {
		if (referralId != null) {
			setAction(targetUrl + "&" + KtunaxaConstant.FORM_REFERRAL + "=" + referralId.replace("/", "%2F"));
		} else {
			setAction(null);
		}
	}

	public final HandlerRegistration addFileUploadDoneHandler(final FileUploadDoneHandler handler) {
		return handlerManager.addHandler(FileUploadDoneHandler.TYPE, handler);
	}

	@Override
	public boolean validate() {
		// true if a file has been chosen
		return fileItem.getValue() != null;
	}

	public void submit(boolean override) {
		if (!override) {
			submit();
		} else {
			String action = getAction();
			setAction(action + "&" + KtunaxaConstant.FORM_OVERRIDE + "=true");
			submit();
			setAction(action);
		}
	}

	private static void uploadComplete(JavaScriptObject result) {
		JSONObject json = new JSONObject(result);
		String formId = ((JSONString) json.get(KtunaxaConstant.FORM_ID)).stringValue();
		FileUploadForm form = (FileUploadForm) Canvas.getById(formId);
		form.handlerManager.fireEvent(new FileUploadCompleteEvent(json));
	}

	private static void uploadFailed(JavaScriptObject result) {
		JSONObject json = new JSONObject(result);
		String formId = ((JSONString) json.get(KtunaxaConstant.FORM_ID)).stringValue();
		FileUploadForm form = (FileUploadForm) Canvas.getById(formId);
		form.handlerManager.fireEvent(new FileUploadFailedEvent(json));
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	@SuppressWarnings("all")
	private native void initComplete(FileUploadForm upload) /*-{
		$wnd.uploadComplete = function(response) {
			@org.ktunaxa.referral.client.referral.FileUploadForm::uploadComplete(Lcom/google/gwt/core/client/JavaScriptObject;)(response);
		};
		$wnd.uploadFailed = function(error) {
			@org.ktunaxa.referral.client.referral.FileUploadForm::uploadFailed(Lcom/google/gwt/core/client/JavaScriptObject;)(error);
		};
	}-*/;
}