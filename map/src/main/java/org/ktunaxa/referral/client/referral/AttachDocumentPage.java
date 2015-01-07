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

import org.geomajas.gwt.client.util.GeometryConverter;
import org.geomajas.layer.feature.Feature;
import org.geomajas.widget.utility.gwt.client.wizard.WizardPage;
import org.ktunaxa.referral.client.gui.DocumentPanel;
import org.ktunaxa.referral.client.gui.MapLayout;

import com.smartgwt.client.widgets.Canvas;

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
		boolean validate = documentPanel.getEditor().validate();
		if (validate) {
			// must copy feature back !
			getWizardData().setFeature(documentPanel.getEditor().getFeature());
		}
		return validate;
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