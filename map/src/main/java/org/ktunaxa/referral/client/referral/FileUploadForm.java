/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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

	public FileUploadForm(String title, String targetUrl, String referralId) {
		super();
		if (!targetUrl.contains("?")) {
			targetUrl += "?";
		} else {
			targetUrl += "&";
		}
		targetUrl += KtunaxaConstant.FORM_ID + "=" + getID() + "&" +
				KtunaxaConstant.FORM_REFERRAL + "=" + referralId.replace("/", "%2F");
		String targetFrame = "resFrame" + getID();
		handlerManager = new HandlerManager(this);
		initComplete(this);
		setEncoding(Encoding.MULTIPART);
		setTarget(targetFrame);
		setCanSubmit(true);
		setAction(targetUrl);
		setWidth100();

		fileItem = new UploadItem(KtunaxaConstant.FORM_FILE);
		fileItem.setTitle(title);
		fileItem.setWrapTitle(false);
		fileItem.setWidth("*");
		fileItem.setHeight(24);
		fileItem.setCellHeight(24);
		/*
		fileItem.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (fileItem.getEnteredValue().endsWith(".zip")) {
					uploadButton.enable();
				} else {
					uploadButton.disable();
				}
			}
		});
		*/
		setFields(fileItem);

		NamedFrame frame = new NamedFrame(targetFrame);
		frame.setSize("1", "1");
		frame.setVisible(false);
		addChild(frame);
	}

	public final HandlerRegistration addFileUploadDoneHandler(final FileUploadDoneHandler handler) {
		return handlerManager.addHandler(FileUploadDoneHandler.TYPE, handler);
	}

	@Override
	public boolean validate() {
		// true if a file has been chosen
		return fileItem.getValue() != null; 
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
		$wnd.uploadComplete = function (response) {
			@org.ktunaxa.referral.client.referral.FileUploadForm::uploadComplete(Lcom/google/gwt/core/client/JavaScriptObject;)(response);
		};
		$wnd.uploadFailed = function (error) {
			@org.ktunaxa.referral.client.referral.FileUploadForm::uploadFailed(Lcom/google/gwt/core/client/JavaScriptObject;)(error);
		};
	}-*/;
}