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

import java.util.LinkedHashMap;

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
		// @todo hardcoded options, ideally the options should be read from the database when the application starts
		LinkedHashMap<String, String> statusValues = new LinkedHashMap<String, String>();
		statusValues.put("", "any");
		statusValues.put("1", "Incompletely created");
		statusValues.put("2", "In progress");
		statusValues.put("3", "Finished");
		statusValues.put("4", "Stopped");
		status.setTitle("Status");
		status.setValueMap(statusValues);
		status.setValue("");
		status.addChangedHandler(filterChangedHandler);
		// @todo hardcoded options, ideally the options should be read from the database when the application starts
		LinkedHashMap<String, String> agencyValues = new LinkedHashMap<String, String>();
		agencyValues.put("", "any");
		agencyValues.put("1", "BC Government");
		agencyValues.put("2", "Government of Canada");
		agencyValues.put("3", "Local Government");
		agencyValues.put("4", "Industry");
		agencyValues.put("5", "Other");
		agency.setTitle("External Agency Type");
		agency.setValueMap(agencyValues);
		agency.setValue("");
		agency.addChangedHandler(filterChangedHandler);
		// @todo hardcoded options, ideally the options should be read from the database when the application starts
		LinkedHashMap<String, String> typeValues = new LinkedHashMap<String, String>();
		typeValues.put("", "any");
		typeValues.put("1", "Adventure Tourism");
		typeValues.put("2", "Agriculture");
		typeValues.put("3", "Aquaculture");
		typeValues.put("4", "Clean Energy");
		typeValues.put("5", "Commercial");
		typeValues.put("6", "Communication Sites");
		typeValues.put("7", "Community & Institutional");
		typeValues.put("8", "Contaminated Sites and Restoration");
		typeValues.put("9", "Land Sales");
		typeValues.put("10", "Flood Protection");
		typeValues.put("11", "Forestry");
		typeValues.put("12", "Grazing");
		typeValues.put("13", "Guide Outfitting");
		typeValues.put("14", "Industrial");
		typeValues.put("15", "Mining: Placer");
		typeValues.put("16", "Mining: Aggregate and Quarry");
		typeValues.put("17", "Mining: Mine Development");
		typeValues.put("19", "Private Moorage");
		typeValues.put("20", "Property Development");
		typeValues.put("21", "Public Recreation - Parks");
		typeValues.put("22", "Public Recreation - General");
		typeValues.put("23", "Residential");
		typeValues.put("24", "Resort Development");
		typeValues.put("25", "Roads/Bridges");
		typeValues.put("26", "Utilities");
		typeValues.put("27", "Crown Grant");
		typeValues.put("28", "Mineral Exploration");
		typeValues.put("29", "Oil and Gas Production");
		typeValues.put("30", "Oil and Gas Exploration");
		typeValues.put("31", "Oil and Gas Infrastructure");
		typeValues.put("32", "Zoning, DPA or OCP Changes");
		typeValues.put("33", "Subdivision Application");
		typeValues.put("34", "Water Diversion");
		typeValues.put("35", "Changes in/about stream/waterbody");
		typeValues.put("36", "Waste Discharge");
		typeValues.put("37", "Pest Management");
		typeValues.put("38", "Assignment (name change)");
		typeValues.put("39", "Follow up letter");
		typeValues.put("40", "Multiple");
		typeValues.put("41", "Other");
		type.setTitle("Type");
		type.setValueMap(typeValues);
		type.setValue("");
		type.addChangedHandler(filterChangedHandler);
		form.setFields(status, agency, type);
		addMember(form);
		addMember(dateForm1);
		addMember(dateForm2);
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
			if (!"".equals(statusValue)) {
				filter.append("status.\"id\" = ");
				filter.append(statusValue);
			}

			String agencyValue = agency.getValueAsString();
			if (!"".equals(agencyValue)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append("externalAgencyType.\"id\" = ");
				filter.append(agencyValue);
			}

			String typeValue = type.getValueAsString();
			if (!"".equals(typeValue)) {
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
}
