/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.configuration.AttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeProvider;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureForm;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.geomajas.layer.feature.attribute.UrlAttribute;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;

/**
 * Form for editing a document.
 * 
 * @author Jan De Moerloose
 * 
 */
public class DocumentForm extends DefaultFeatureForm {

	private DocumentItem documentItem;

	public DocumentForm(FeatureInfo documentsInfo, VectorLayer referralLayer) {
		super(documentsInfo, new DefaultAttributeProvider(referralLayer, KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
		addItemChangedHandler(new SetDocumentTitleAndUrl());
	}

	@Override
	protected FormItem createItem(AttributeInfo info) {
		FormItem item = super.createItem(info);
		// intercept the document item
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_ID.equals(info.getName())) {
			documentItem = (DocumentItem) item;
		} else if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE.equals(info.getName())
				|| KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL.equals(info.getName())
				|| KtunaxaConstant.ATTRIBUTE_DOCUMENT_DISPLAY_URL.equals(info.getName())) {
			item.setDisabled(true);
		}
		return item;
	}

	@Override
	public void toForm(String name, Attribute<?> attribute) {
		super.toForm(name, attribute);
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE.equals(name)) {
			documentItem.setDocumentTitle((String) attribute.getValue());
		}
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_DISPLAY_URL.equals(name)) {
			documentItem.setDocumentDisplayUrl((String) attribute.getValue());
		}
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL.equals(name)) {
			documentItem.setDocumentDownLoadUrl((String) attribute.getValue());
		}
	}

	/**
	 * Sets a couple of derived values.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class SetDocumentTitleAndUrl implements ItemChangedHandler {

		public void onItemChanged(ItemChangedEvent event) {
			if (documentItem != null) {
				setValue(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DISPLAY_URL,
						new UrlAttribute(documentItem.getDocumentDisplayUrl()));
				setValue(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL,
						new UrlAttribute(documentItem.getDocumentDownLoadUrl()));
				setValue(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE, 
						new StringAttribute(documentItem.getDocumentTitle()));
			}
		}
	}

}
