/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import java.util.Comparator;
import java.util.LinkedHashMap;
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
import org.ktunaxa.referral.client.widget.attribute.AbstractAttributeBlockLayout;
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
 * Form for editing comments in mapping dashboard.
 * 
 * @author Jan De Moerloose
 */

public class CommentsForm implements FeatureForm<Canvas> {

	private CommentsLayout commentsLayout;

	private CommentForm commentForm;

	private AssociationAttributeInfo attributeInfo;

	public CommentsForm(VectorLayer referralLayer) {
		Map<String, Comparator<AbstractAttributeBlock>> sortAttributes;
		sortAttributes = new LinkedHashMap<String, Comparator<AbstractAttributeBlock>>();
		sortAttributes.put(KtunaxaConstant.ATTRIBUTE_COMMENT_TITLE, new AttributeComparator(
				KtunaxaConstant.ATTRIBUTE_COMMENT_TITLE));
		sortAttributes.put(KtunaxaConstant.ATTRIBUTE_COMMENT_CREATION_DATE, new AttributeComparator(
				KtunaxaConstant.ATTRIBUTE_COMMENT_CREATION_DATE));
		sortAttributes.put(KtunaxaConstant.ATTRIBUTE_COMMENT_CREATED_BY, new AttributeComparator(
				KtunaxaConstant.ATTRIBUTE_COMMENT_CREATED_BY));
		AttributeBlockList listView = new AttributeBlockList(sortAttributes);
		listView.setDefaultCollapsed(true);
		for (AttributeInfo info : referralLayer.getLayerInfo().getFeatureInfo().getAttributes()) {
			if (info.getName().equals(KtunaxaConstant.ATTRIBUTE_COMMENTS)) {
				attributeInfo = ((AssociationAttributeInfo) info);
				commentForm = new CommentForm(attributeInfo.getFeature(), referralLayer);
			}
		}
		commentsLayout = new CommentsLayout(listView, commentForm);
		commentsLayout.setWidth100();
	}

	public Canvas getWidget() {
		return commentsLayout;
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
		return commentsLayout.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				h.onItemChanged(new ItemChangedEvent(null));
			}
		});
	}

	public void fireEvent(GwtEvent<?> event) {
		commentsLayout.fireEvent(event);
	}

	public void toForm(String name, Attribute<?> attribute) {
		if (name.equals(KtunaxaConstant.ATTRIBUTE_COMMENTS) && attribute instanceof OneToManyAttribute) {
			commentsLayout.toLayout((OneToManyAttribute) attribute);
		}
	}

	public void fromForm(String name, Attribute<?> attribute) {
		if (name.equals(KtunaxaConstant.ATTRIBUTE_COMMENTS) && attribute instanceof OneToManyAttribute) {
			commentsLayout.fromLayout((OneToManyAttribute) attribute);
		}
	}

	public void clear() {

	}

	public void toForm(AssociationValue value) {
		toForm(KtunaxaConstant.ATTRIBUTE_COMMENTS,
				value.getAllAttributes().get(KtunaxaConstant.ATTRIBUTE_COMMENTS));
	}

	public void fromForm(AssociationValue value) {
		fromForm(KtunaxaConstant.ATTRIBUTE_COMMENTS,
				value.getAllAttributes().get(KtunaxaConstant.ATTRIBUTE_COMMENTS));
	}

	/**
	 * Block layout for comments.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class CommentsLayout extends AbstractAttributeBlockLayout<DynamicForm> {

		public CommentsLayout(AttributeBlockList listView, CommentForm commentForm) {
			super(listView, commentForm);
		}

		@Override
		public AssociationValue newInstance() {
			AssociationValue comment = AttributeUtil.createEmptyAssociationValue(attributeInfo);
			comment.setBooleanAttribute(KtunaxaConstant.ATTRIBUTE_COMMENT_INCLUDE_IN_REPORT, false);
			return comment;
		}

		@Override
		protected AbstractAttributeBlock newBlock(AssociationValue value) {
			return new CommentBlock(value);
		}
	}

	public boolean silentValidate() {
		return true;
	}
}