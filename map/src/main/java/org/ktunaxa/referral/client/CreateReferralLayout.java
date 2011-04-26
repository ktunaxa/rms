/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Layout for creating new referrals. This layout will display a wizard.
 * 
 * @author Pieter De Graef
 */
public class CreateReferralLayout extends VLayout {

	private MapWidget mapWidget;

	private ReferralCreationWizard wizard;

	public CreateReferralLayout() {
		setWidth("100%");
		setOverflow(Overflow.VISIBLE);

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
					wizard.addStep(new ReferralConfirmPage(wizard));
				}
			}
		});
	}
}