/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.HashMap;

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.gwt.client.util.AttributeUtil;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.LongAttribute;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.ktunaxa.referral.client.widget.attribute.AbstractAttributeBlock;
import org.ktunaxa.referral.client.widget.attribute.AbstractAttributeBlockLayout;
import org.ktunaxa.referral.client.widget.attribute.AttributeBlockList;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;

/**
 * Block layout for documents.
 * 
 * @author Jan De Moerloose
 * 
 */
class DocumentsLayout extends AbstractAttributeBlockLayout<DynamicForm> {
	private DocumentForm documentForm;
	
	private AssociationAttributeInfo attributeInfo;

	public DocumentsLayout(AttributeBlockList listView, DocumentForm documentForm, 
			AssociationAttributeInfo attributeInfo) {
		super(listView, documentForm);
		this.attributeInfo = attributeInfo;
		this.documentForm = documentForm;
		setSaveAction(new UploadAndSaveAction());
	}

	@Override
	public AssociationValue newInstance() {
		AssociationValue document = AttributeUtil.createEmptyAssociationValue(attributeInfo);
		document.setBooleanAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_INCLUDE_IN_REPORT, false);
		document.setBooleanAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_CONFIDENTIAL, false);
		document.setBooleanAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_CONFIDENTIAL, false);
		document.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TYPE, 
				new AssociationValue(new LongAttribute(1L),
				new HashMap<String, PrimitiveAttribute<?>>()));
		return document;
	}

	@Override
	protected AbstractAttributeBlock newBlock(AssociationValue value) {
		return new DocumentBlock(value);
	}
	
	@Override
	public void showDetails(AssociationValue value) {
		documentForm.setUploadVisible(isNewBlock());
		super.showDetails(value);
	}

	/**
	 * Action to do both file upload and save.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class UploadAndSaveAction implements ClickHandler, ItemChangedHandler {

		private HandlerRegistration registration;

		public void onClick(ClickEvent event) {
			if (documentForm.validate()) {
				if (isNewBlock()) {
					// upload document to alfresco
					registration = documentForm.addItemChangedHandler(this);
					documentForm.uploadDocument();
				} else {
					addToList();
				}
			}
		}

		public void onItemChanged(ItemChangedEvent event) {
			if (registration != null) {
				registration.removeHandler();
				if (documentForm.isUploadSuccess()) {
					addToList();
				}
			}
		}

	}
}