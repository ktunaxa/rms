/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.configuration.AttributeInfo;
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
		for (AttributeInfo info : referralLayer.getLayerInfo().getFeatureInfo().getAttributes()) {
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

	public boolean validate() {
		return true;
	}

	public HandlerRegistration addItemChangedHandler(ItemChangedHandler handler) {
		final ItemChangedHandler h = handler;
		return documentsLayout.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				h.onItemChanged(new ItemChangedEvent(null));
			}
		});
	}

	public void fireEvent(GwtEvent<?> event) {
		documentsLayout.fireEvent(event);
	}

	public void toForm(String name, Attribute<?> attribute) {
		if (name.equals(KtunaxaConstant.ATTRIBUTE_DOCUMENTS) && attribute instanceof OneToManyAttribute) {
			documentsLayout.toLayout((OneToManyAttribute) attribute);
		}
	}

	public void fromForm(String name, Attribute<?> attribute) {
		if (name.equals(KtunaxaConstant.ATTRIBUTE_DOCUMENTS) && attribute instanceof OneToManyAttribute) {
			documentsLayout.fromLayout((OneToManyAttribute) attribute);
		}
	}

	public void clear() {

	}

	public void toForm(AssociationValue value) {
		toForm(KtunaxaConstant.ATTRIBUTE_DOCUMENTS, value.getAllAttributes().get(KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
	}

	public void fromForm(AssociationValue value) {
		fromForm(KtunaxaConstant.ATTRIBUTE_DOCUMENTS, 
				value.getAllAttributes().get(KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
	}

	public boolean silentValidate() {
		return true;
	}
}
