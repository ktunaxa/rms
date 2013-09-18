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

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.AttributeProviderCallBack;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeProvider;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.ManyToOneAttribute;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * {@link LayerBlock} for a referral layer.
 * 
 * @author Joachim Van der Auwera
 */
public class ReferralLayerBlock extends LayerBlock {

	private final VectorLayer layer;

	private final DynamicForm form = new DynamicForm();

	private final ReferralDateFilterForm dateForm1;

	private final ReferralDateFilterForm dateForm2;

	private final ComboBoxItem status = new ComboBoxItem("Status");

	private final ComboBoxItem agency = new ComboBoxItem("ExternalAgencyType");

	private final ComboBoxItem type = new ComboBoxItem("Type");

	public ReferralLayerBlock(Layer<?> referral) {
		super(referral);
		this.layer = (VectorLayer) referral;

		ChangedHandler filterChangedHandler = new FilterChangedHandler();
		dateForm1 = new ReferralDateFilterForm(filterChangedHandler);
		dateForm2 = new ReferralDateFilterForm(filterChangedHandler);

		form.setWidth100();
		status.setTitle("Status");
		status.addChangedHandler(filterChangedHandler);
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("-1", "Any");
		status.setValueMap(valueMap);
		status.setValue("-1");
		agency.setTitle("External Agency Type");
		agency.addChangedHandler(filterChangedHandler);
		valueMap = new LinkedHashMap<String, String>();
		valueMap.put("-1", "Any");
		agency.setValueMap(valueMap);
		agency.setValue("-1");
		type.setTitle("Type");
		type.addChangedHandler(filterChangedHandler);
		valueMap = new LinkedHashMap<String, String>();
		valueMap.put("-1", "Any");
		type.setValueMap(valueMap);
		type.setValue("-1");
		form.setFields(status, agency, type);
		addMember(form);
		addMember(dateForm1);
		addMember(dateForm2);
		new DefaultAttributeProvider(layer.getServerLayerId(), "status").getAttributes(new ProviderCallback(status));
		new DefaultAttributeProvider(layer.getServerLayerId(), "externalAgencyType")
				.getAttributes(new ProviderCallback(agency));
		new DefaultAttributeProvider(layer.getServerLayerId(), "type").getAttributes(new ProviderCallback(type));
	}

	@Override
	protected void updateLayerEnabled(boolean enabled) {
		super.updateLayerEnabled(enabled);
		form.setDisabled(!enabled);
		dateForm1.setDisabled(!enabled);
		dateForm2.setDisabled(!enabled);
	}

	/**
	 * Handler for updating the current referral filter.
	 * 
	 * @author Joachim Van der Auwera
	 */
	private class FilterChangedHandler implements ChangedHandler {

		public void onChanged(ChangedEvent changedEvent) {
			StringBuilder filter = new StringBuilder();

			String statusValue = status.getValueAsString();
			if (!"-1".equals(statusValue)) {
				filter.append("status.\"id\" = ");
				filter.append(statusValue);
			}

			String agencyValue = agency.getValueAsString();
			if (!"-1".equals(agencyValue)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append("externalAgencyType.\"id\" = ");
				filter.append(agencyValue);
			}

			String typeValue = type.getValueAsString();
			if (!"-1".equals(typeValue)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append("type.\"id\" = ");
				filter.append(typeValue);
			}

			String dateFilter1 = dateForm1.getFilter();
			if (!"".equals(dateFilter1)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append(dateFilter1);
			}
			String dateFilter2 = dateForm2.getFilter();
			if (!"".equals(dateFilter2)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append(dateFilter2);
			}

			layer.setFilter(filter.toString());
		}
	}

	public class ProviderCallback implements AttributeProviderCallBack {

		private ComboBoxItem item;

		public ProviderCallback(ComboBoxItem item) {
			this.item = item;
		}

		@Override
		public void onSuccess(List<Attribute<?>> attributes) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put("-1", "Any");
			for (Attribute<?> attribute : attributes) {
				if (attribute instanceof ManyToOneAttribute) {
					ManyToOneAttribute mto = (ManyToOneAttribute) attribute;
					String title = (String) mto.getValue().getAttributeValue("title");
					valueMap.put(mto.getValue().getId().getValue().toString(), title);
				}
			}
			item.setValueMap(valueMap);
			item.setValue("-1");
		}

		@Override
		public void onError(List<String> errorMessages) {
		}

	}

}
