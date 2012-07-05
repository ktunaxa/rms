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

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import org.geomajas.configuration.AbstractReadOnlyAttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeProvider;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureForm;
import org.geomajas.gwt.client.widget.attribute.FormItemList;
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
	private FormItem confidential;
	private FormItem includeInReport;

	public DocumentForm(FeatureInfo documentsInfo, VectorLayer referralLayer) {
		super(documentsInfo, new DefaultAttributeProvider(referralLayer, KtunaxaConstant.ATTRIBUTE_DOCUMENTS));
		getWidget().setColWidths("120", "*");
	}

	public void uploadDocument() {
		documentItem.upload();
	}

	@Override
	public FormItem createItem(AbstractReadOnlyAttributeInfo info) {
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
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_INCLUDE_IN_REPORT.equals(info.getName())) {
			includeInReport = item;
		}
		if (KtunaxaConstant.ATTRIBUTE_DOCUMENT_CONFIDENTIAL.equals(info.getName())) {
			confidential = item;
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
	public boolean isIncluded(AbstractReadOnlyAttributeInfo info) {
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
	public void prepareForm(FormItemList formItems, DataSource source) {
		super.prepareForm(formItems, source);
		confidential.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				confidentialDisableIncludeInReport();
			}
		});
		confidentialDisableIncludeInReport();

		addedBy.setDisabled(true);
		additionDate.setDisabled(true);
	}

	private boolean confidentialDisableIncludeInReport() {
		boolean value = (Boolean) confidential.getValue();
		if (value) {
			includeInReport.setValue(false);
		}
		includeInReport.setDisabled(value);
		return value;
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
