/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import org.ktunaxa.referral.client.referral.FileUploadForm;
import org.ktunaxa.referral.client.referral.event.FileUploadCompleteEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;
import org.ktunaxa.referral.client.referral.event.FileUploadFailedEvent;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Form item for searching/uploading documents.
 * 
 * @author Jan De Moerloose
 * 
 */
public class DocumentItem extends CanvasItem {

	private Img busyImg;

	private FileUploadForm form;

	private String documentId;

	private String documentTitle;

	private String documentDisplayUrl;

	private String documentDownLoadUrl;

	private LinkItem linkItem;

	@Override
	protected Canvas createCanvas() {
		VLayout layout = createUploadLayout();
		return layout;
	}

	@Override
	public void clearValue() {
		form.clearValues();
		linkItem.clearValue();
		documentId = null;
		documentTitle = null;
		documentDisplayUrl = null;
		documentDownLoadUrl = null;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public String getDocumentDisplayUrl() {
		return documentDisplayUrl;
	}

	public void setDocumentDisplayUrl(String documentDisplayUrl) {
		this.documentDisplayUrl = documentDisplayUrl;
	}

	public String getDocumentDownLoadUrl() {
		return documentDownLoadUrl;
	}

	public void setDocumentDownLoadUrl(String documentDownLoadUrl) {
		this.documentDownLoadUrl = documentDownLoadUrl;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	@Override
	public void setValue(String value) {
		if (value == null) {
			clearValue();
		} else {
			linkItem.clearValue();
			linkItem.setLinkTitle("Upload document first");
		}
		super.setValue(value);
	}

	private VLayout createUploadLayout() {
		VLayout uploadLayout = new VLayout();
		uploadLayout.setLayoutAlign(Alignment.CENTER);
		uploadLayout.setMembersMargin(10);
		HTMLFlow explanation = new HTMLFlow("<p>Please note that it may take a"
				+ " while to upload the document to the document management system.</p>");
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(20);
		form = new FileUploadForm("Select a file", GWT.getModuleBaseURL() + "../d/upload/referral/document");

		HLayout btnLayout = new HLayout(10);
		busyImg = new Img("[ISOMORPHIC]/images/loading.gif", 16, 16);
		busyImg.setVisible(false);
		IButton uploadbutton = new IButton("Upload");
		uploadbutton.setAutoFit(true);
		uploadbutton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				form.submit();
				busyImg.setVisible(true);
			}
		});
		btnLayout.addMember(uploadbutton);
		btnLayout.addMember(busyImg);
		final HTMLFlow errorFlow = new HTMLFlow();
		errorFlow.setHeight100();
		errorFlow.setWidth100();
		errorFlow.setVisible(false);
		btnLayout.addMember(errorFlow);

		form.addFileUploadDoneHandler(new FileUploadDoneHandler() {

			@SuppressWarnings("unchecked")
			public void onFileUploadComplete(FileUploadCompleteEvent event) {
				documentId = event.getString(KtunaxaConstant.FORM_DOCUMENT_ID);
				documentTitle = event.getString(KtunaxaConstant.FORM_DOCUMENT_TITLE);
				documentDisplayUrl = event.getString(KtunaxaConstant.FORM_DOCUMENT_DISPLAY_URL);
				documentDownLoadUrl = event.getString(KtunaxaConstant.FORM_DOCUMENT_DOWNLOAD_URL);
				linkItem.setLinkTitle(documentTitle);
				linkItem.setDisabled(false);
				setValue(documentId);
				fireEvent(new ChangedEvent(jsObj));
				busyImg.setVisible(false);
			}

			public void onFileUploadFailed(FileUploadFailedEvent event) {
				busyImg.setVisible(false);
				errorFlow.setContents("<div style='color: #AA0000'>" + event.getErrorMessage() + "</div>");
				errorFlow.setVisible(true);
			}
		});

		uploadLayout.addMember(explanation);
		DynamicForm linkForm = new DynamicForm();

		linkItem = new LinkItem("link");
		linkItem.setTitle("Test link");
		linkItem.setDisabled(true);
		linkItem.setLinkTitle("Upload document first");
		linkItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				Window.open(documentDisplayUrl, "_ktunaxa_document", null);
			}
		});
		linkForm.setFields(linkItem);

		uploadLayout.addMember(linkForm);
		uploadLayout.addMember(spacer);
		uploadLayout.addMember(form);
		uploadLayout.addMember(btnLayout);

		return uploadLayout;
	}

}
