package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.WizardPage;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReferralConfirmPage extends VLayout implements WizardPage {

	private Feature feature;

	public ReferralConfirmPage(VectorLayer layer) {
	}

	public String getTitle() {
		return "Referral confirm";
	}

	public String getExplanation() {
		return "Confirm the ";
	}

	public boolean validate() {
		// TODO Only if the form validates does this page validate.
		return true;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Canvas asWidget() {
		return this;
	}
}