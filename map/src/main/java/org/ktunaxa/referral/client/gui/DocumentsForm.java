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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.geomajas.configuration.AbstractAttributeInfo;
import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.OneToManyAttribute;
import org.ktunaxa.referral.client.widget.attribute.AbstractAttributeBlock;
import org.ktunaxa.referral.client.widget.attribute.AttributeBlockList;
import org.ktunaxa.referral.client.widget.attribute.AttributeBlockList.AttributeComparator;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

/**
 * Form for editing documents in mapping dashboard.
 * 
 * @author Jan De Moerloose
 */
public class DocumentsForm implements FeatureForm<Canvas> {

	private DocumentsLayout documentsLayout;

	private DocumentForm documentForm;

	private AssociationAttributeInfo attributeInfo;

	public DocumentsForm(VectorLayer referralLayer) {
		Map<String, Comparator<AbstractAttributeBlock>> sortAttributes;
		sortAttributes = new HashMap<String, Comparator<AbstractAttributeBlock>>();
		sortAttributes.put("Title", new AttributeComparator(
				KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE));
		sortAttributes.put("Description", new AttributeComparator(
				KtunaxaConstant.ATTRIBUTE_DOCUMENT_DESCRIPTION));
		sortAttributes.put("Type", new AttributeComparator(
				KtunaxaConstant.ATTRIBUTE_DOCUMENT_TYPE));
		AttributeBlockList listView = new AttributeBlockList(sortAttributes);
		for (AbstractAttributeInfo info : referralLayer.getLayerInfo().getFeatureInfo().getAttributes()) {
			if (info.getName().equals(KtunaxaConstant.ATTRIBUTE_DOCUMENTS)) {
				attributeInfo = ((AssociationAttributeInfo) info);
				documentForm = new DocumentForm(attributeInfo.getFeature(), referralLayer);
			}
		}
		documentsLayout = new DocumentsLayout(listView, documentForm, attributeInfo);
		documentsLayout.setWidth100();
	}

	public Canvas getWidget() {
		return documentsLayout;
	}

	public void setDisabled(boolean disabled) {

	}

	public boolean isDisabled() {
		return false;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public HandlerRegistration addItemChangedHandler(ItemChangedHandler handler) {
		final ItemChangedHandler h = handler;
		return documentsLayout.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				h.onItemChanged(new ItemChangedEvent(null));
			}
		});
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		documentsLayout.fireEvent(event);
	}

	@Override
	public void toForm(String name, Attribute<?> attribute) {
		if (name.equals(KtunaxaConstant.ATTRIBUTE_DOCUMENTS) && attribute instanceof OneToManyAttribute) {
			documentsLayout.toLayout((OneToManyAttribute) attribute);
		}
	}

	@Override
	public void fromForm(String name, Attribute<?> attribute) {
		if (name.equals(KtunaxaConstant.ATTRIBUTE_DOCUMENTS) && attribute instanceof OneToManyAttribute) {
			documentsLayout.fromLayout((OneToManyAttribute) attribute);
		}
	}

	@Override
	public void clear() {

	}

	@Override
	public void toForm(AssociationValue value) {
		toForm(KtunaxaConstant.ATTRIBUTE_DOCUMENTS, value.getAllAttributes().get(KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
	}

	@Override
	public void fromForm(AssociationValue value) {
		fromForm(KtunaxaConstant.ATTRIBUTE_DOCUMENTS, 
				value.getAllAttributes().get(KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
	}

	@Override
	public boolean silentValidate() {
		return true;
	}

	@Override
	public void toForm(Feature feature) {
		toForm(KtunaxaConstant.ATTRIBUTE_DOCUMENTS,
				feature.getAttributes().get(KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
	}

}
