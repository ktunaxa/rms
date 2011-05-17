/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.configuration.AttributeInfo;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureFormFactory;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;
import org.geomajas.gwt.client.widget.attribute.ManyToOneDataSource;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

/**
 * Attribute form factory that create a specific form for features of the Referral layer, or delegates to the default
 * factory for features in other layers.
 * 
 * @author Pieter De Graef
 */
public class ReferralFormFactory implements FeatureFormFactory {

	private static final String LAND_REFERRAL_ID_FIELD = "land_referral_id";

	private FeatureFormFactory delegate = new DefaultFeatureFormFactory();

	public FeatureForm createFeatureForm(VectorLayer layer) {
		if (KtunaxaConstant.REFERRAL_LAYER_ID.equals(layer.getId())) {
			return new ReferralFeatureForm(layer);
		}
		return delegate.createFeatureForm(layer);
	}

	/**
	 * Custom form for the referral layer.
	 * 
	 * @author Pieter De Graef
	 */
	private class ReferralFeatureForm extends FeatureForm {

		public ReferralFeatureForm(VectorLayer layer) {
			super(layer);
			getWidget().setWidth("100%");
			getWidget().setNumCols(4);
			getWidget().setColWidths(175, "50%", 225, "50%");

			DataSource source = new DataSource();
			List<FormItem> formItems = new ArrayList<FormItem>();
			for (AttributeInfo info : layer.getLayerInfo().getFeatureInfo().getAttributes()) {
				if (info.isIncludedInForm()) {
					if ("primaryClassificationNumber".equals(info.getName())) {
						HeaderItem header = new HeaderItem("project-info-header");
						header.setDefaultValue("General project information");
						header.setColSpan(4);
						formItems.add(header);
					} else if ("number".equals(info.getName())) {
						// insert fake number item + make real one invisible (see further)
						StaticTextItem number = new StaticTextItem();
						number.setName("dummy");
						number.setTitle(info.getLabel());
						number.setValue("XXXX");
						formItems.add(number);
					} else if ("externalProjectId".equals(info.getName())) {
						// new group
						RowSpacerItem rowSpacer = new RowSpacerItem("external-info-spacer");
						formItems.add(rowSpacer);
						HeaderItem header = new HeaderItem("external-info-header");
						header.setDefaultValue("External project information");
						header.setColSpan(4);
						formItems.add(header);
					} else if ("contactName".equals(info.getName())) {
						RowSpacerItem rowSpacer = new RowSpacerItem("contact-info-spacer");
						formItems.add(rowSpacer);
						HeaderItem header = new HeaderItem("contact-info-header");
						header.setDefaultValue("Referral contact information");
						header.setColSpan(4);
						formItems.add(header);
					} else if ("referralType".equals(info.getName())) {
						RowSpacerItem rowSpacer = new RowSpacerItem("type-info-spacer");
						formItems.add(rowSpacer);
						HeaderItem header = new HeaderItem("type-info-header");
						header.setDefaultValue("General information regarding the referral");
						header.setColSpan(4);
						formItems.add(header);
					} else if ("receiveDate".equals(info.getName())) {
						RowSpacerItem rowSpacer = new RowSpacerItem("date-info-spacer");
						formItems.add(rowSpacer);
						HeaderItem header = new HeaderItem("date-info-header");
						header.setDefaultValue("Project deadline information");
						header.setColSpan(4);
						formItems.add(header);
					} else if ("activeRetentionPeriod".equals(info.getName())) {
						RowSpacerItem rowSpacer = new RowSpacerItem("date-info-spacer");
						formItems.add(rowSpacer);
						HeaderItem header = new HeaderItem("date-info-header");
						header.setDefaultValue("Document management classificiation");
						header.setColSpan(4);
						formItems.add(header);
					}

					DataSourceField field = AttributeFormFieldRegistry.createDataSourceField(layer, info);
					field.setCanEdit(info.isEditable());
					source.addField(field);
					FormItem formItem = AttributeFormFieldRegistry.createFormItem(layer, info);
					if ("number".equals(info.getName())) {
						formItem.setVisible(false);
					} 
					formItems.add(formItem);
					if (KtunaxaConstant.TARGET_REFERRAL_ATTRIBUTE_NAME.equals(info.getName())) {
						SelectItem targetItem = (SelectItem) formItem;
						targetItem.setOptionDataSource(new TargetReferralOptionDataSource(layer, info));
						targetItem.setDisplayField(LAND_REFERRAL_ID_FIELD);
					} 
					if ("externalAgencyName".equals(info.getName())) {
						formItem.setColSpan(4);
					} else if ("projectDescription".equals(info.getName())) {
						formItem.setColSpan(4);
						formItem.setHeight(50);
					} else if ("projectBackground".equals(info.getName())) {
						formItem.setColSpan(4);
						formItem.setHeight(50);
					} else if ("responseDeadline".equals(info.getName())) {
						formItem.setColSpan(4);
					}
				}
			}
			getWidget().setDataSource(source);
			getWidget().setFields(formItems.toArray(new FormItem[formItems.size()]));
			addItemChangedHandler(new ReferralIdSetter());
		}

		/**
		 * Re(sets) the referral Id when a component changes.
		 * 
		 * @author Jan De Moerloose
		 * 
		 */
		public class ReferralIdSetter implements ItemChangedHandler {

			public void onItemChanged(ItemChangedEvent event) {
				Integer primClassNr = (Integer) getWidget().getValue("primaryClassificationNumber");
				Integer secClassNr = (Integer) getWidget().getValue("secondaryClassificationNumber");
				Integer year = (Integer) getWidget().getValue("calendarYear");
				String referralId = ReferralUtil.createId(primClassNr, secClassNr, year, null);
				HeaderItem header = (HeaderItem) getWidget().getItem("project-info-header");
				header.setDefaultValue("General project information [" + referralId + "]");
			}
		}
	}

	/**
	 * Slightly adapted option data source to show the complete referral id in the drop-down box.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class TargetReferralOptionDataSource extends ManyToOneDataSource {

		public TargetReferralOptionDataSource(VectorLayer layer, AttributeInfo associationInfo) {
			super(layer.getLayerInfo().getServerLayerId(), (AssociationAttributeInfo) associationInfo);
			DataSourceTextField field = new DataSourceTextField(LAND_REFERRAL_ID_FIELD, "Land Referral Id");
			addField(field);
		}

		protected void transformResponse(DSResponse response, DSRequest request, Object data) {
			super.transformResponse(response, request, data);
			for (Record record : response.getData()) {
				String referralId = ReferralUtil.createId(record);
				record.setAttribute(LAND_REFERRAL_ID_FIELD, referralId);
			}
		}
	}

}