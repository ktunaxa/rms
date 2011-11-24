/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import java.util.List;
import java.util.Map;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.util.HtmlBuilder;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.widget.utility.gwt.client.wizard.WizardPage;
import org.ktunaxa.referral.client.gui.LayoutConstant;
import org.ktunaxa.referral.client.referral.event.FileUploadCompleteEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;
import org.ktunaxa.referral.client.referral.event.FileUploadFailedEvent;
import org.ktunaxa.referral.client.referral.event.GeometryUploadHandler;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Third page in the referral creation wizard: attach documents to the referrals.
 * 
 * @author Pieter De Graef
 */
public class AttachDocumentPage extends WizardPage<ReferralData> implements UploadGeometryPanel {


	private VLayout layout;

	private ListGrid grid;

	private Img busyImg;
	private VLayout uploadLayout;
	private AssociationValue currentDocument;

	public AttachDocumentPage() {
		super();
		layout = new VLayout();
		layout.addMember(createUploadLayout());

		grid = new ListGrid();
		grid.setLayoutAlign(Alignment.CENTER);
		grid.setWidth100();
		grid.setHeight100();
		grid.setShowAllRecords(true);

		ListGridField titleField = new ListGridField(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE, "Title");
		ListGridField documentIdField = new ListGridField(KtunaxaConstant.ATTRIBUTE_DOCUMENT_ID, "Alfresco ID");
		grid.setFields(titleField, documentIdField);
		grid.setData(new ListGridRecord[] {});
		layout.addMember(grid);
	}

	@Override
	public void setWizardData(ReferralData wizardData) {
		super.setWizardData(wizardData);
		setFeature(wizardData.getFeature());
	}
	
	public String getTitle() {
		return "Attach documents";
	}

	public String getExplanation() {
		return "Attach documents to the referral.";
	}

	public boolean doValidate() {
		return true;
	}

	public Canvas asWidget() {
		return layout;
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void addDocument(AssociationValue associationValue) {
		Map<String, Attribute<?>> attributes = associationValue.getAllAttributes();
		String title = (String) attributes.get(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE).getValue();
		String documentId = (String) attributes.get(KtunaxaConstant.ATTRIBUTE_DOCUMENT_ID).getValue();
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE, title);
		record.setAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_ID, documentId);
		grid.addData(record);
	}

	private VLayout createUploadLayout() {
		uploadLayout = new VLayout();
		uploadLayout.setLayoutAlign(Alignment.CENTER);
		uploadLayout.setMembersMargin(LayoutConstant.MARGIN_LARGE);
		HTMLFlow explanation = new HTMLFlow("<h3>Upload a new document</h3><div><p>Please note that it may take a"
				+ " while to upload the document to the document management system.</p></div>");
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(20);

		uploadLayout.addMember(explanation);
		uploadLayout.addMember(spacer);

		return uploadLayout;
	}
	@SuppressWarnings("unchecked")
	public void init() {
		List<AssociationValue> documents = (List<AssociationValue>) getWizardData().getFeature().getAttributeValue(
				KtunaxaConstant.ATTRIBUTE_DOCUMENTS);
		if (documents != null) {
			for (AssociationValue associationValue : documents) {
				addDocument(associationValue);
			}
		}
	}
	
	@Override
	public void show() {
		if (null == currentDocument) {
			init();
		} else {
			addDocument(currentDocument);
		}
	}

	public void setFeature(Feature feature) {
		// finish layout cfr upload form

		final FileUploadForm form = new FileUploadForm("Select a file", GWT.getModuleBaseURL()
				+ KtunaxaConstant.URL_DOCUMENT_UPLOAD, ReferralUtil.createId(getWizardData().getFeature()));
		form.setHeight(40);
		HLayout btnLayout = new HLayout(LayoutConstant.MARGIN_LARGE);
		btnLayout.setHeight(60);
		busyImg = new Img(LayoutConstant.LOADING_IMAGE,
				LayoutConstant.LOADING_IMAGE_WIDTH, LayoutConstant.LOADING_IMAGE_HEIGHT);
		busyImg.setVisible(false);
		IButton uploadButton = new IButton("Upload");
		uploadButton.setAutoFit(true);
		uploadButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				form.submit();
//				busyImg.setVisible(true);
			}
		});
		btnLayout.addMember(form);
		btnLayout.addMember(uploadButton);
		btnLayout.addMember(busyImg);
		final HTMLFlow errorFlow = new HTMLFlow();
		errorFlow.setHeight100();
		errorFlow.setWidth100();
		errorFlow.setVisible(false);
		btnLayout.addMember(errorFlow);
		LayoutSpacer component = new LayoutSpacer();
		component.setWidth("60%");
		btnLayout.addMember(component);

		form.addFileUploadDoneHandler(new FileUploadDoneHandler() {

			public void onFileUploadComplete(FileUploadCompleteEvent event) {
				errorFlow.setContents("");
				errorFlow.setVisible(false);
				String title = event.getString(KtunaxaConstant.FORM_DOCUMENT_TITLE);
				String documentId = event.getString(KtunaxaConstant.FORM_DOCUMENT_ID);
				String displayUrl = event.getString(KtunaxaConstant.FORM_DOCUMENT_DISPLAY_URL);
				String downloadUrl = event.getString(KtunaxaConstant.FORM_DOCUMENT_DOWNLOAD_URL);
				currentDocument = new AssociationValue();
				currentDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE, title);
				currentDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DESCRIPTION, title);
				currentDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_ID, documentId);
				currentDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DISPLAY_URL, displayUrl);
				currentDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL, downloadUrl);
				getWizardData().getFeature().addOneToManyValue(KtunaxaConstant.ATTRIBUTE_DOCUMENTS, currentDocument);
				busyImg.setVisible(false);
				show();
			}

			public void onFileUploadFailed(FileUploadFailedEvent event) {
				busyImg.setVisible(false);
				errorFlow.setContents(HtmlBuilder.divStyle("color: #AA0000", event.getErrorMessage()));
				errorFlow.setVisible(true);
			}
		});

		uploadLayout.addMember(btnLayout);
	}

	public HandlerRegistration addGeometryUploadHandler(GeometryUploadHandler handler) {
		return null;  // don't do anything for this one
	}

	@Override
	public void clear() {
	}
}