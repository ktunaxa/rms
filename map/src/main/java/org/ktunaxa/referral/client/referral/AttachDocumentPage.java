/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geomajas.gwt.client.widget.wizard.WizardPage;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.ktunaxa.referral.client.referral.event.FileUploadCompleteEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;
import org.ktunaxa.referral.client.referral.event.FileUploadFailedEvent;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.GWT;
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
public class AttachDocumentPage extends WizardPage<ReferralData> {

	private VLayout layout;

	private ListGrid grid;

	private Img busyImg;

	public AttachDocumentPage() {
		layout = new VLayout();
		layout.addMember(createUploadLayout());

		grid = new ListGrid();
		grid.setLayoutAlign(Alignment.CENTER);
		grid.setWidth100();
		grid.setHeight100();
		grid.setShowAllRecords(true);

		ListGridField titleField = new ListGridField("title", "Title");
		ListGridField documentIdField = new ListGridField("documentId", "Alfresco ID");
		grid.setFields(titleField, documentIdField);
		grid.setData(new ListGridRecord[] {});
		layout.addMember(grid);
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

	private void addDocument(String title, String documentId) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("title", title);
		record.setAttribute("documentId", documentId);
		grid.addData(record);
	}

	private VLayout createUploadLayout() {
		VLayout uploadLayout = new VLayout();
		uploadLayout.setLayoutAlign(Alignment.CENTER);
		uploadLayout.setMembersMargin(10);
		HTMLFlow explanation = new HTMLFlow("<h3>Upload a new document</h3><div><p>Please note that it may take a"
				+ " while to upload the document to the document management system.</p></div>");
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(20);
		final FileUploadForm form = new FileUploadForm("Select a file", GWT.getModuleBaseURL()
				+ "../d/upload/referral/document");
		form.setHeight(40);

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
				errorFlow.setContents("");
				errorFlow.setVisible(false);
				String title = event.getString(KtunaxaConstant.FORM_DOCUMENT_TITLE);
				String documentId = event.getString(KtunaxaConstant.FORM_DOCUMENT_ID);
				AssociationValue document = new AssociationValue();
				Map<String, PrimitiveAttribute<?>> attribs = new HashMap<String, PrimitiveAttribute<?>>();
				attribs.put("title", new StringAttribute(title));
				attribs.put("description", new StringAttribute(title));
				attribs.put("documentId", new StringAttribute(documentId));
				document.setAttributes(attribs);
				getWizardData().getFeature().addOneToManyValue("documents", document);
				busyImg.setVisible(false);
				show();
			}

			public void onFileUploadFailed(FileUploadFailedEvent event) {
				busyImg.setVisible(false);
				errorFlow.setContents("<div style='color: #AA0000'>" + event.getErrorMessage() + "</div>");
				errorFlow.setVisible(true);
			}
		});

		uploadLayout.addMember(explanation);
		uploadLayout.addMember(spacer);
		uploadLayout.addMember(form);
		uploadLayout.addMember(btnLayout);

		return uploadLayout;
	}

	public void clear() {
		busyImg.setVisible(false);
		grid.setData(new ListGridRecord[] {});
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void show() {
		clear();
		List<AssociationValue> documents = (List<AssociationValue>) getWizardData().getFeature().getAttributeValue(
				"documents");
		if (documents != null) {
			for (AssociationValue associationValue : documents) {
				String title = (String) associationValue.getAttributes().get("title").getValue();
				String documentId = (String) associationValue.getAttributes().get("documentId").getValue();
				addDocument(title, documentId);
			}
		}
	}

}