/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.wizard.WizardPage;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * First page in the referral creation wizard: fill in the general referral meta-data.
 * 
 * @author Pieter De Graef
 */
public class ReferralInfoPage extends WizardPage<ReferralData> {

	private VLayout mainLayout;

	private FeatureAttributeEditor editor;
	
	private HTMLFlow invalidTop;

	private HTMLFlow invalidBottom;
	
	public ReferralInfoPage() {
		mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();
	}

	private HTMLFlow createInvalid() {
		HTMLFlow flow = new HTMLFlow("<div style='color: #AA0000'>Some required fields are missing !</div>");
		flow.setWidth100();
		flow.setVisible(false);
		return flow;
	}

	public String getTitle() {
		return "Referral information";
	}

	public String getExplanation() {
		return "Fill in all referral related meta-data.";
	}

	public boolean doValidate() {
		boolean validate = editor.validate();
		if (!validate) {
			invalidTop.setVisible(true);
			invalidBottom.setVisible(true);
		} else {
			invalidTop.setVisible(false);
			invalidBottom.setVisible(false);
			// must copy feature back !
			getWizardData().setFeature(editor.getFeature());
		}
		return validate;
	}

	public Canvas asWidget() {
		return mainLayout;
	}
	
	public void clear() {
		if (editor != null) {
			editor.setFeature(null);
		}
	}
	
	@Override
	protected void show() {
		if (editor == null) {
			editor = new FeatureAttributeEditor(getWizardData().getLayer(), false, new ReferralFormFactory());
			editor.setWidth100();
			editor.setHeight100();
			invalidTop = createInvalid();
			invalidBottom = createInvalid();
			mainLayout.addMember(invalidTop);
			mainLayout.addMember(editor);
			mainLayout.addMember(invalidBottom);
		}
		editor.setFeature(getWizardData().getFeature());
	}

}