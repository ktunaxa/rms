/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
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
import org.geomajas.gwt.client.util.AttributeUtil;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.OneToManyAttribute;
import org.ktunaxa.referral.client.widget.attribute.AbstractAttributeBlock;
import org.ktunaxa.referral.client.widget.attribute.AttributeBlockLayout;
import org.ktunaxa.referral.client.widget.attribute.AttributeBlockList;
import org.ktunaxa.referral.client.widget.attribute.AttributeBlockList.AttributeComparator;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

/**
 * Form for editing documents in mapping dashboard.
 * 
 * @author Jan De Moerloose
 * 
 */
public class DocumentsForm implements FeatureForm<Canvas> {

	private DocumentsLayout documentsLayout;

	private DocumentForm documentForm;

	private AssociationAttributeInfo attributeInfo;

	public DocumentsForm(VectorLayer referralLayer) {
		Map<String, Comparator<AbstractAttributeBlock>> sortAttributes;
		sortAttributes = new HashMap<String, Comparator<AbstractAttributeBlock>>();
		sortAttributes.put("title", new AttributeComparator("title"));
		sortAttributes.put("description", new AttributeComparator("description"));
		sortAttributes.put("type", new AttributeComparator("type"));
		AttributeBlockList listView = new AttributeBlockList(sortAttributes);
		for (AttributeInfo info : referralLayer.getLayerInfo().getFeatureInfo().getAttributes()) {
			if (info.getName().equals(KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME)) {
				attributeInfo = ((AssociationAttributeInfo) info);
				documentForm = new DocumentForm(attributeInfo.getFeature(), referralLayer);
			}
		}
		documentsLayout = new DocumentsLayout(listView, documentForm);
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
		return false;
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
		if (name.equals(KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME)) {
			documentsLayout.toLayout((OneToManyAttribute) attribute);
		}
	}

	public void fromForm(String name, Attribute<?> attribute) {
		if (name.equals(KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME)) {
			documentsLayout.fromLayout((OneToManyAttribute) attribute);
		}
	}

	public void clear() {

	}

	public void toForm(AssociationValue value) {
		toForm(KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME,
				value.getAllAttributes().get(KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME));
	}

	public void fromForm(AssociationValue value) {
		fromForm(KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME,
				value.getAllAttributes().get(KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME));
	}

	/**
	 * Block layout for documents.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class DocumentsLayout extends AttributeBlockLayout<DynamicForm> {

		public DocumentsLayout(AttributeBlockList listView, DocumentForm documentForm) {
			super(listView, documentForm);
		}

		@Override
		public AssociationValue newInstance() {
			AssociationValue document = AttributeUtil.createEmptyAssociationValue(attributeInfo);
			return document;
		}

		@Override
		protected AbstractAttributeBlock newBlock(AssociationValue value) {
			return new DocumentBlock(value);
		}
	}

}
