/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.referral;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.configuration.AttributeInfo;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.AttributeForm;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFactory;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeFormFactory;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;

/**
 * Attribute form factory that create a specific form for features of the Referral layer, or delegates to the default
 * factory for features in other layers.
 * 
 * @author Pieter De Graef
 */
public class ReferralFormFactory implements AttributeFormFactory {

	private static final String LAYER_ID = "referralLayer";

	private AttributeFormFactory delegate = new DefaultAttributeFormFactory();

	public AttributeForm createAttributeForm(VectorLayer layer) {
		if (LAYER_ID.equals(layer.getId())) {
			return new ReferralAttributeForm(layer);
		}
		return delegate.createAttributeForm(layer);
	}

	private class ReferralAttributeForm extends AttributeForm {

		public ReferralAttributeForm(VectorLayer layer) {
			super(layer);
			getWidget().setWidth(800);
			getWidget().setNumCols(4);
			getWidget().setColWidths(175, 200, 225, 200);

			DataSource source = new DataSource();
			List<FormItem> formItems = new ArrayList<FormItem>();
			for (AttributeInfo info : layer.getLayerInfo().getFeatureInfo().getAttributes()) {
				if (info.isIncludedInForm()) {
					if ("landReferralId".equals(info.getName())) {
						RowSpacerItem rowSpacer = new RowSpacerItem("project-info-spacer");
						formItems.add(rowSpacer);
						HeaderItem header = new HeaderItem("project-info-header");
						header.setDefaultValue("General project information");
						header.setColSpan(4);
						formItems.add(header);
					} else if ("externalProjectId".equals(info.getName())) {
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
					}

					DataSourceField field = AttributeFormFieldRegistry.createDataSourceField(layer, info);
					field.setCanEdit(info.isEditable());
					source.addField(field);

					FormItem formItem = AttributeFormFieldRegistry.createFormItem(layer, info);
					formItem.setWidth("*");
					formItems.add(formItem);

					if ("externalAgencyName".equals(info.getName())) {
						formItem.setColSpan(4);
					} else if ("projectDescription".equals(info.getName())) {
						formItem.setColSpan(4);
						formItem.setHeight(45);
					}
					if ("projectBackground".equals(info.getName())) {
						formItem.setColSpan(4);
						formItem.setHeight(45);
					} else if ("reponseDeadline".equals(info.getName())) {
						formItem.setColSpan(4);
					}
				}
			}
			getWidget().setDataSource(source);
			getWidget().setFields(formItems.toArray(new FormItem[] {}));
		}
	}
}