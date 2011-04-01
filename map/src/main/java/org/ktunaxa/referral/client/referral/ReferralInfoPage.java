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

	public ReferralInfoPage(VectorLayer layer) {
		editor = new FeatureAttributeEditor(layer, false, new ReferralFormFactory());
		Feature feature = new Feature(layer);
		editor.setFeature(feature);
	}

	public String getTitle() {
		return "Referral information";
	}

	public String getExplanation() {
		return "Fill in all referral related meta-data.";
	}

	public boolean validate() {
		// TODO Only if the form validates does this page validate.
		return true;
	}

	public Canvas asWidget() {
		return editor;
	}

	public Feature getFeature() {
		return editor.getFeature();
	}

	public void setFeature(Feature feature) {
		editor.setFeature(feature);
	}
}