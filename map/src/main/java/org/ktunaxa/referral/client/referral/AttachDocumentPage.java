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

import org.geomajas.gwt.client.map.feature.Feature;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.WizardPage;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;

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
public class AttachDocumentPage implements WizardPage {

	private Feature feature;

	private VLayout layout;

	private ListGrid grid;

	private Img busyImg;

	public AttachDocumentPage() {
		layout = new VLayout();
		layout.addMember(createUploadLayout());

		grid = new ListGrid();
		grid.setLayoutAlign(Alignment.CENTER);
		grid.setWidth(500);
		grid.setAutoHeight();
		grid.setShowAllRecords(true);

		ListGridField nameField = new ListGridField("name", "Document");
		grid.setFields(nameField);
		grid.setData(new ListGridRecord[] {});
		layout.addMember(grid);
	}

	public String getTitle() {
		return "Attach documents";
	}

	public String getExplanation() {
		return "Attach documents to the referral.";
	}

	public boolean validate() {
		return true;
	}

	public Canvas asWidget() {
		return layout;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void addDocument(String name) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("name", name);
		grid.addData(record);
	}

	private VLayout createUploadLayout() {
		VLayout uploadLayout = new VLayout();
		uploadLayout.setLayoutAlign(Alignment.CENTER);
		HTMLFlow explanation = new HTMLFlow("<h3>Upload a new document</h3><div><p>Please note that it may take a"
				+ " while to upload the document to the document management system.</p></div>");
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(20);
		final FileUploadForm form = new FileUploadForm("Select a file", "uploadDocument");
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

		form.addFileUploadDoneHandler(new FileUploadDoneHandler() {

			public void onFileUploadDone(FileUploadDoneEvent event) {
				addDocument(event.getResponse());
				busyImg.setVisible(false);
			}
		});

		uploadLayout.addMember(explanation);
		uploadLayout.addMember(spacer);
		uploadLayout.addMember(form);
		uploadLayout.addMember(btnLayout);

		return uploadLayout;
	}
}