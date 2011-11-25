/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import com.smartgwt.client.widgets.Canvas;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.geomajas.layer.feature.Feature;
import org.geomajas.widget.utility.gwt.client.wizard.WizardPage;
import org.ktunaxa.referral.client.gui.DocumentPanel;
import org.ktunaxa.referral.client.gui.MapLayout;

/**
 * Third page in the referral creation wizard: attach documents to the referrals.
 *
 * @author Joachim Van der Auwera
 */
public class AttachDocumentPage extends WizardPage<ReferralData> {

	private DocumentPanel documentPanel;

	public AttachDocumentPage() {
		super();
		documentPanel = new DocumentPanel();
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
		return documentPanel;
	}

	@Override
	public void show() {
		ReferralData data = getWizardData();
		Feature plainFeature = new Feature(data.getFeature().getId());
		plainFeature.setAttributes(data.getFeature().getAttributes());
		plainFeature.setGeometry(GeometryConverter.toDto(data.getFeature().getGeometry()));
		MapLayout.getInstance().setReferralAndTask(plainFeature, null);
		documentPanel.init(data.getLayer(), data.getFeature());
	}

	@Override
	public void clear() {
	}
}