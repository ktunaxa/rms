/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.data.DataSource;
import org.geomajas.configuration.AttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeProvider;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureForm;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.geomajas.layer.feature.attribute.UrlAttribute;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.form.fields.FormItem;

/**
 * Form for editing a document.
 * 
 * @author Jan De Moerloose
 */
public class DocumentForm extends DefaultFeatureForm {

	private DocumentItem documentItem;
	private FormItem addedBy;
	private FormItem additionDate;

	public DocumentForm(FeatureInfo documentsInfo, VectorLayer referralLayer) {
		super(documentsInfo, new DefaultAttributeProvider(referralLayer, KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
		getWidget().setColWidths("120", "*");
	}

	public void uploadDocument() {
		documentItem.upload();
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
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_ADDED_BY.equals(info.getName())) {
			addedBy = item;
		}
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_ADDITION_DATE.equals(info.getName())) {
			additionDate = item;
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

	@Override
	public void fromForm(String name, Attribute<?> attribute) {
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE.equals(name) && attribute instanceof StringAttribute) {
			((StringAttribute) attribute).setValue(documentItem.getDocumentTitle());
		} else if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_DISPLAY_URL.equals(name) && attribute instanceof UrlAttribute) {
			((UrlAttribute) attribute).setValue(documentItem.getDocumentDisplayUrl());
		} else if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL.equals(name) && attribute instanceof UrlAttribute) {
			((UrlAttribute) attribute).setValue(documentItem.getDocumentDownLoadUrl());
		} else {
			super.fromForm(name, attribute);
		}
	}	
	

	@Override
	public void fromForm(AssociationValue value) {
		super.fromForm(value);
	}

	@Override
	protected boolean isIncluded(AttributeInfo info) {
		return !(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE.equals(info.getName()) ||
				KtunaxaConstant.ATTRIBUTE_DOCUMENT_DISPLAY_URL.equals(info.getName()) ||
				KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL.equals(info.getName())) && super.isIncluded(info);
	}

	public void setUploadVisible(boolean visible) {
		documentItem.setVisible(visible);
	}

	public boolean isUploadSuccess() {
		return documentItem.isUploadSuccess();
	}

	@Override
	protected void prepareForm(FormItemList formItems, DataSource source) {
		addedBy.setDisabled(true);
		additionDate.setDisabled(true);
	}

	private boolean isEmpty(Object value) {
		return value == null || "".equals(value.toString().trim());
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		addedBy.setDisabled(true);
		additionDate.setDisabled(true);
	}

	@Override
	public void toForm(AssociationValue value) {
		super.toForm(value);
		if (isEmpty(addedBy.getValue())) {
			addedBy.setValue(UserContext.getInstance().getUser());
		}
	}

}
