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

package org.ktunaxa.referral.client;

import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.client.referral.AddGeometryPage;
import org.ktunaxa.referral.client.referral.AttachDocumentPage;
import org.ktunaxa.referral.client.referral.ReferralConfirmPage;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.WizardPage;
import org.ktunaxa.referral.client.referral.ReferralInfoPage;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Layout for creating new referrals. This layout will display a wizard.
 * 
 * @author Pieter De Graef
 */
public class ReferralLayout extends VLayout {

	private MapWidget mapWidget;

	private ReferralCreationWizard wizard;

	public ReferralLayout() {
		setWidth("100%");
		// setHeight("100%");
		setOverflow(Overflow.VISIBLE);

		addMember(createHeader());

		VLayout body = new VLayout();
		body.setMargin(10);
		wizard = new ReferralCreationWizard("Referral Creation Wizard", "Follow the steps below to create a "
				+ "new referral and add it to the system:");
		body.addMember(wizard);
		addMember(body);

		// Add steps:
		mapWidget = new MapWidget("mainMap", "app");
		mapWidget.setVisible(false);
		addMember(mapWidget);
		mapWidget.init();
		mapWidget.getMapModel().addMapModelHandler(new MapModelHandler() {

			public void onMapModelChange(MapModelEvent event) {
				VectorLayer layer = (VectorLayer) mapWidget.getMapModel().getLayer("referralLayer");
				if (layer != null) {
					WizardPage referralInfoPage = new ReferralInfoPage(layer);
					wizard.addStep(referralInfoPage);
					wizard.addStep(new AddGeometryPage());
					wizard.addStep(new AttachDocumentPage());
					wizard.addStep(new ReferralConfirmPage(layer, wizard));
				}
			}
		});
	}

	private Canvas createHeader() {
		HLayout header = new HLayout();
		header.setSize("100%", "44");
		header.setStyleName("header");

		HTMLFlow rfaLabel = new HTMLFlow("Ktunaxa - Referral Management System");
		rfaLabel.setStyleName("headerText");
		// rfaLabel.setTooltip(RFA_DESCRIPTION);
		// rfaLabel.setHoverWidth(700);
		rfaLabel.setWidth100();
		header.addMember(rfaLabel);

		LayoutSpacer spacer = new LayoutSpacer();
		header.addMember(spacer);

		ToolStrip headerBar = new ToolStrip();
		headerBar.setMembersMargin(2);
		headerBar.setSize("445", "44");
		headerBar.addFill();
		headerBar.setStyleName("headerRight");

		// headerBar.addMember(new ToolStripButton("Select"));
		// headerBar.addMember(new ToolStripButton("Finish"));
		// headerBar.addMember(new ToolStripButton("Tasks"));
		// headerBar.addMember(new ToolStripButton("Request"));
		headerBar.addSpacer(10);
		header.addMember(headerBar);

		return header;
	}
}