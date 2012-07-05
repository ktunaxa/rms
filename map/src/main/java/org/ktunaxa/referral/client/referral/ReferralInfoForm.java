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
package org.ktunaxa.referral.client.referral;

import org.geomajas.configuration.AbstractReadOnlyAttributeInfo;
import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.configuration.AssociationType;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureForm;
import org.geomajas.gwt.client.widget.attribute.FormItemList;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;

/**
 * Custom form for the referral layer, used when creating a new referral.
 * 
 * @author Pieter De Graef
 */
public class ReferralInfoForm extends DefaultFeatureForm {

	private static final int COLUMN_COUNT = 4;

	private static final String[] SKIPPED_NAMES = new String[] {
			KtunaxaConstant.ATTRIBUTE_DECISION,
			KtunaxaConstant.ATTRIBUTE_PROVINCIAL_DECISION,
			KtunaxaConstant.ATTRIBUTE_STATUS,
			KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL,
			KtunaxaConstant.ATTRIBUTE_STOP_REASON,
			KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_INTRODUCTION,
			KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_CONCLUSION
		};

	public ReferralInfoForm(VectorLayer layer) {
		super(layer);
		addItemChangedHandler(new ReferralIdSetter());
	}

	@Override
	public FormItem createItem(AbstractReadOnlyAttributeInfo info) {
		FormItem formItem = super.createItem(info);
		if (KtunaxaConstant.ATTRIBUTE_NUMBER.equals(info.getName())) {
			formItem.setVisible(false);
		} else if (KtunaxaConstant.ATTRIBUTE_TARGET_REFERRAL.equals(info.getName())) {
			SelectItem targetItem = (SelectItem) formItem;
			targetItem.setOptionDataSource(new ReferralManyToOneDataSource(info,
					KtunaxaConstant.LAYER_REFERRAL_SERVER_ID));
			targetItem.setDisplayField(ReferralManyToOneDataSource.LAND_REFERRAL_ID_FIELD);
		}
		if (KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY.equals(info.getName())) {
			formItem.setColSpan(COLUMN_COUNT);
		} else if (KtunaxaConstant.ATTRIBUTE_PROJECT_DESCRIPTION.equals(info.getName())) {
			formItem.setColSpan(COLUMN_COUNT);
			formItem.setHeight(50);
		} else if (KtunaxaConstant.ATTRIBUTE_PROJECT_BACKGROUND.equals(info.getName())) {
			formItem.setColSpan(COLUMN_COUNT);
			formItem.setHeight(50);
		} else if (KtunaxaConstant.ATTRIBUTE_CONTACT_ADDRESS.equals(info.getName())) {
			formItem.setHeight(50);
		} else if (KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE.equals(info.getName())) {
			formItem.setColSpan(4);
		}
		return formItem;
	}

	@Override
	public boolean isIncluded(AbstractReadOnlyAttributeInfo info) {
		for (String name : SKIPPED_NAMES) {
			if (info.getName().equals(name)) {
				return false;
			}
		}
		if (!info.isHidden()) {
			if (info instanceof AssociationAttributeInfo) {
				AssociationAttributeInfo associationAttributeInfo = (AssociationAttributeInfo) info;
				return associationAttributeInfo.getType() == AssociationType.MANY_TO_ONE;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public void prepareForm(FormItemList formItems, DataSource source) {
		HeaderItem projectHeader = new HeaderItem("project-info-header");
		projectHeader.setDefaultValue("General project information");
		formItems.insertBefore(KtunaxaConstant.ATTRIBUTE_PRIMARY, projectHeader);

		RowSpacerItem externalSpacer = new RowSpacerItem("external-info-spacer");
		HeaderItem externalHeader = new HeaderItem("external-info-header");
		externalHeader.setDefaultValue("External project information");
		externalHeader.setColSpan(COLUMN_COUNT);
		formItems.insertBefore("externalProjectId", externalSpacer, externalHeader);

		RowSpacerItem contactSpacer = new RowSpacerItem("contact-info-spacer");
		HeaderItem contactHeader = new HeaderItem("contact-info-header");
		contactHeader.setDefaultValue("Referral contact information");
		contactHeader.setColSpan(COLUMN_COUNT);
		formItems.insertBefore("contactName", contactSpacer, contactHeader);

		RowSpacerItem typeSpacer = new RowSpacerItem("type-info-spacer");
		HeaderItem typeHeader = new HeaderItem("type-info-header");
		typeHeader.setDefaultValue("General information regarding the referral");
		typeHeader.setColSpan(COLUMN_COUNT);
		formItems.insertBefore("referralType", typeSpacer, typeHeader);

		RowSpacerItem dateSpacer = new RowSpacerItem("date-info-spacer");
		HeaderItem dateHeader = new HeaderItem("date-info-header");
		dateHeader.setDefaultValue("Project deadline information");
		dateHeader.setColSpan(COLUMN_COUNT);
		formItems.insertBefore("receiveDate", dateSpacer, dateHeader);

		RowSpacerItem documentSpacer = new RowSpacerItem("document-info-spacer");
		HeaderItem documentHeader = new HeaderItem("document-info-header");
		documentHeader.setDefaultValue("Document management classificiation");
		documentHeader.setColSpan(COLUMN_COUNT);
		formItems.insertBefore("activeRetentionPeriod", documentSpacer, documentHeader);

		getWidget().setWidth("100%");
		getWidget().setNumCols(COLUMN_COUNT);
		getWidget().setColWidths(175, "50%", 225, "50%");
	}

	/**
	 * Re(sets) the referral Id when a component changes.
	 * 
	 * @author Jan De Moerloose
	 */
	public class ReferralIdSetter implements ItemChangedHandler {

		public void onItemChanged(ItemChangedEvent event) {
			Integer primClassNr = (Integer) getWidget().getValue(KtunaxaConstant.ATTRIBUTE_PRIMARY);
			Integer secClassNr = (Integer) getWidget().getValue(KtunaxaConstant.ATTRIBUTE_SECONDARY);
			Integer year = (Integer) getWidget().getValue(KtunaxaConstant.ATTRIBUTE_YEAR);
			Integer number = (Integer) getWidget().getValue(KtunaxaConstant.ATTRIBUTE_NUMBER);
			String referralId = ReferralUtil.createId(primClassNr, secClassNr, year, number);
			HeaderItem header = (HeaderItem) getWidget().getItem("project-info-header");
			header.setDefaultValue("General project information [" + referralId + "]");
		}
	}

}
