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

package org.ktunaxa.referral.client.form;

import java.util.HashMap;
import java.util.Map;

import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.LongAttribute;
import org.geomajas.layer.feature.attribute.ManyToOneAttribute;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.form.fields.CheckboxItem;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public class ConcernsAddressedForm extends AbstractTaskForm {

	private CheckboxItem concernsAddressed = new CheckboxItem();

	public ConcernsAddressedForm() {
		super();

		concernsAddressed.setName("concernsAddressed");
		concernsAddressed.setTitle("Concerns addressed");
		concernsAddressed.setPrompt("The KLRA concerns have been addressed by the proponent");

		setFields(concernsAddressed);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		concernsAddressed.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_CONCERNS_ADDRESSED));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.VAR_CONCERNS_ADDRESSED + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(concernsAddressed.getValue()));
		return result;
	}

	@Override
	public boolean validate() {
		if (concernsAddressed.getValueAsBoolean()) {
			// need to mark referral as finished
			MapLayout mapLayout = MapLayout.getInstance();
			VectorLayer layer = mapLayout.getReferralLayer();
			Feature current = mapLayout.getCurrentReferral();
			Feature previous = new org.geomajas.gwt.client.map.feature.Feature(current, layer).toDto();
			Map<String, Attribute> attributes = current.getAttributes();
			attributes.put(KtunaxaConstant.ATTRIBUTE_STATUS, new ManyToOneAttribute(new AssociationValue(
					new LongAttribute(3L), new HashMap<String, PrimitiveAttribute<?>>())));
			persistReferral(previous, current);
		}
		return super.validate();
	}
}
