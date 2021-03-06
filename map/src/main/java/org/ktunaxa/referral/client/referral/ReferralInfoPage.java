/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;
import org.geomajas.widget.utility.gwt.client.wizard.WizardPage;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
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
		super();
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
	public void show() {
		if (editor == null) {
			editor = new FeatureAttributeEditor(getWizardData().getLayer(), false, new ReferralInfoFormFactory());
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

	/**
	 * Factory for {@link ReferralInfoForm}.
	 * 
	 * @author Jan De Moerloose
	 */
	class ReferralInfoFormFactory implements FeatureFormFactory<DynamicForm> {

		public FeatureForm<DynamicForm> createFeatureForm(VectorLayer layer) {
			return new ReferralInfoForm(layer);
		}

	}

}