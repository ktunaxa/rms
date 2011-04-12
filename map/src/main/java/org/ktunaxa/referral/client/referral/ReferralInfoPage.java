/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.WizardPage;

import com.smartgwt.client.widgets.Canvas;

/**
 * First page in the referral creation wizard: fill in the general referral meta-data.
 * 
 * @author Pieter De Graef
 */
public class ReferralInfoPage implements WizardPage {

	private FeatureAttributeEditor editor;
	
	private VectorLayer layer;

	public ReferralInfoPage(VectorLayer layer) {
		this.layer = layer;
		editor = new FeatureAttributeEditor(layer, false, new ReferralFormFactory());
		Feature feature = new Feature(layer);
		editor.setFeature(feature);
		editor.setWidth100();
		editor.setHeight100();
	}

	public String getTitle() {
		return "Referral information";
	}

	public String getExplanation() {
		return "Fill in all referral related meta-data.";
	}

	public boolean validate() {
		return editor.validate();
	}

	public Canvas asWidget() {
		return editor;
	}

	public Feature getFeature() {
		return editor.getFeature();
	}

	public void setFeature(Feature feature) {
		if (feature != null) {
			editor.setFeature(feature);
		}
	}
	
	public void clear() {
		setFeature(new Feature(layer));
	}

}