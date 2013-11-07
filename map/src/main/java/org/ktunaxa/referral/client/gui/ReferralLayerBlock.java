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

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeProvider;
import org.ktunaxa.referral.client.SortedManyToOneItem;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

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

	private final SortedManyToOneItem status = new SortedManyToOneItem("Status");

	private final SortedManyToOneItem agency = new SortedManyToOneItem("ExternalAgencyType");

	private final SortedManyToOneItem type = new SortedManyToOneItem("Type");
	
	private final ButtonItem clearStatus = new ButtonItem("Reset");
	
	private final ButtonItem clearAgency = new ButtonItem("Reset");
	
	private final ButtonItem clearType = new ButtonItem("Reset");
	

	public ReferralLayerBlock(Layer<?> referral) {
		super(referral);
		this.layer = (VectorLayer) referral;

		final ChangedHandler filterChangedHandler = new FilterChangedHandler();
		dateForm1 = new ReferralDateFilterForm(filterChangedHandler);
		dateForm2 = new ReferralDateFilterForm(filterChangedHandler);

		FeatureInfo featureInfo = layer.getLayerInfo().getFeatureInfo();

		AssociationAttributeInfo statusInfo = (AssociationAttributeInfo) featureInfo.getAttributesMap().get(
				KtunaxaConstant.ATTRIBUTE_STATUS);
		AssociationAttributeInfo agencyTypeInfo = (AssociationAttributeInfo) featureInfo.getAttributesMap().get(
				KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY_TYPE);
		AssociationAttributeInfo typeInfo = (AssociationAttributeInfo) featureInfo.getAttributesMap().get(
				KtunaxaConstant.ATTRIBUTE_TYPE);
		
		clearStatus.setStartRow(false);
		clearStatus.setEndRow(false);		
		clearStatus.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				status.clearValue();
				filterChangedHandler.onChanged(null);
			}
		});
		clearAgency.setStartRow(false);
		clearAgency.setEndRow(false);		
		clearAgency.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				agency.clearValue();				
				filterChangedHandler.onChanged(null);
			}
		});
		clearType.setStartRow(false);
		clearType.setEndRow(false);		
		clearType.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				type.clearValue();				
				filterChangedHandler.onChanged(null);
			}
		});

		form.setWidth100();
		status.getItem().setTitle("Status");
		status.getItem().addChangedHandler(filterChangedHandler);
		status.init(statusInfo, new DefaultAttributeProvider(layer, statusInfo.getName()));
		agency.getItem().setTitle("External Agency Type");
		agency.getItem().addChangedHandler(filterChangedHandler);
		agency.init(agencyTypeInfo, new DefaultAttributeProvider(layer, agencyTypeInfo.getName()));
		type.getItem().setTitle("Type");
		type.getItem().addChangedHandler(filterChangedHandler);
		type.init(typeInfo, new DefaultAttributeProvider(layer, typeInfo.getName()));
		form.setNumCols(4);
		form.setFields(status.getItem(), clearStatus, agency.getItem(), clearAgency, type.getItem(), clearType);
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

			String statusValue = status.getItem().getValueAsString();
			if (!isEmpty(statusValue)) {
				filter.append("status.\"id\" = ");
				filter.append(statusValue);
			}

			String agencyValue = agency.getItem().getValueAsString();
			if (!isEmpty(agencyValue)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append("externalAgencyType.\"id\" = ");
				filter.append(agencyValue);
			}

			String typeValue = type.getItem().getValueAsString();
			if (!isEmpty(typeValue)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append("type.\"id\" = ");
				filter.append(typeValue);
			}

			String dateFilter1 = dateForm1.getFilter();
			if (!isEmpty(dateFilter1)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append(dateFilter1);
			}
			String dateFilter2 = dateForm2.getFilter();
			if (!isEmpty(dateFilter2)) {
				if (filter.length() > 0) {
					filter.append(" AND ");
				}
				filter.append(dateFilter2);
			}

			layer.setFilter(filter.toString());
		}

		private boolean isEmpty(String s) {
			return s == null || s.isEmpty();
		}
	}

}
