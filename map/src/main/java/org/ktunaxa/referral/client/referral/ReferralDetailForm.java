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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geomajas.configuration.AbstractReadOnlyAttributeInfo;
import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.configuration.AssociationType;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureForm;
import org.geomajas.gwt.client.widget.attribute.FormItemList;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.RowSpacerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;

/**
 * Form for editing referral details in mapping dashboard.
 * 
 * @author Jan De Moerloose
 */
public class ReferralDetailForm extends DefaultFeatureForm {

	private static final int COLSPAN = 4;
	private static final int ITEM_HEIGHT = 50;
	private static final String REFERRAL_FORM_STYLE = "attributeForm";

	private static final String[] SKIPPED_NAMES = new String[] {
			KtunaxaConstant.ATTRIBUTE_FULL_ID,
			KtunaxaConstant.ATTRIBUTE_PRIMARY,
			KtunaxaConstant.ATTRIBUTE_SECONDARY,
			KtunaxaConstant.ATTRIBUTE_NUMBER,
			KtunaxaConstant.ATTRIBUTE_YEAR,
			KtunaxaConstant.ATTRIBUTE_ACTIVE_RETENTION_PERIOD,
			KtunaxaConstant.ATTRIBUTE_SEMI_ACTIVE_RETENTION_PERIOD,
			KtunaxaConstant.ATTRIBUTE_FINAL_DISPOSITION,
			KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_INTRODUCTION,
			KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_CONCLUSION
		};

	private static final Set<String> ENABLED_FIELDS_ADMIN = new HashSet<String>();
	private static final Set<String> ENABLED_FIELDS_REFERRAL_MANAGER = new HashSet<String>();
	private static final Set<String> ENABLED_FIELDS_OTHER = new HashSet<String>();
	private static final Set<String> ENABLED_FIELDS_GUEST = new HashSet<String>();

	static {
		ENABLED_FIELDS_OTHER.add(KtunaxaConstant.ATTRIBUTE_PROJECT_BACKGROUND);
		ENABLED_FIELDS_REFERRAL_MANAGER.addAll(ENABLED_FIELDS_OTHER);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_APPLICATION_TYPE);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_PROJECT);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_PROJECT_DESCRIPTION);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_CONTACT_ADDRESS);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_TYPE);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_PROJECT_LOCATION);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_CONTACT_NAME);
		ENABLED_FIELDS_REFERRAL_MANAGER.add(KtunaxaConstant.ATTRIBUTE_CONTACT_PHONE);
		ENABLED_FIELDS_ADMIN.addAll(ENABLED_FIELDS_REFERRAL_MANAGER);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_CONFIDENTIAL);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_TARGET_REFERRAL);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_PRIORITY);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY_TYPE);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_EXTERNAL_FILE_ID);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_EXTERNAL_PROJECT_ID);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_APPLICANT_NAME);
		ENABLED_FIELDS_ADMIN.add(KtunaxaConstant.ATTRIBUTE_EMAIL);
	}

	private FormItemList formItems;

	public ReferralDetailForm(VectorLayer layer) {
		super(layer);
		getWidget().setStyleName(REFERRAL_FORM_STYLE);
	}

	@Override
	public FormItem createItem(AbstractReadOnlyAttributeInfo info) {
		FormItem formItem = super.createItem(info);
		if (KtunaxaConstant.ATTRIBUTE_TARGET_REFERRAL.equals(info.getName())) {
			SelectItem targetItem = (SelectItem) formItem;
			targetItem.setOptionDataSource(new ReferralManyToOneDataSource(info,
					KtunaxaConstant.LAYER_REFERRAL_SERVER_ID));
			targetItem.setDisplayField(ReferralManyToOneDataSource.LAND_REFERRAL_ID_FIELD);
		}
		if (KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY.equals(info.getName())) {
			formItem.setColSpan(COLSPAN);
		} else if (KtunaxaConstant.ATTRIBUTE_PROJECT_DESCRIPTION.equals(info.getName())) {
			formItem.setColSpan(COLSPAN);
			formItem.setHeight(ITEM_HEIGHT);
		} else if (KtunaxaConstant.ATTRIBUTE_PROJECT_BACKGROUND.equals(info.getName())) {
			formItem.setColSpan(COLSPAN);
			formItem.setHeight(ITEM_HEIGHT);
		} else if (KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE.equals(info.getName())) {
			formItem.setColSpan(COLSPAN);
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
		this.formItems = formItems;
		
		Map<String, FormItem> itemMap = new HashMap<String, FormItem>();
		for (FormItem formItem : formItems) {
			itemMap.put(formItem.getName(), formItem);
		}
		formItems.clear();
		
		HeaderItem projectHeader = new HeaderItem("project-info-header");
		projectHeader.setDefaultValue("General project information");
		formItems.add(projectHeader);
		
		List<FormItem> projectItems = grabList(itemMap, 
				KtunaxaConstant.ATTRIBUTE_APPLICANT_NAME,
				KtunaxaConstant.ATTRIBUTE_PROJECT,
				KtunaxaConstant.ATTRIBUTE_PROJECT_LOCATION,
				KtunaxaConstant.ATTRIBUTE_PROJECT_DESCRIPTION,
				KtunaxaConstant.ATTRIBUTE_PROJECT_BACKGROUND
		);
		formItems.addAll(projectItems);
		
		RowSpacerItem externalSpacer = new RowSpacerItem("external-info-spacer");
		HeaderItem externalHeader = new HeaderItem("external-info-header");
		externalHeader.setDefaultValue("External project information");
		externalHeader.setColSpan(COLSPAN);
		formItems.add(externalSpacer);
		formItems.add(externalHeader);
		
		List<FormItem> externalItems = grabList(itemMap, 
				KtunaxaConstant.ATTRIBUTE_EXTERNAL_PROJECT_ID,
				KtunaxaConstant.ATTRIBUTE_EXTERNAL_FILE_ID,
				KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY_TYPE,
				KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY,
				KtunaxaConstant.ATTRIBUTE_PRIORITY
		);
		formItems.addAll(externalItems);
		
		RowSpacerItem contactSpacer = new RowSpacerItem("contact-info-spacer");
		HeaderItem contactHeader = new HeaderItem("contact-info-header");
		contactHeader.setDefaultValue("Referral contact information");
		contactHeader.setColSpan(COLSPAN);
		formItems.add(contactSpacer);
		formItems.add(contactHeader);
		
		List<FormItem> contactItems = grabList(itemMap, 
				KtunaxaConstant.ATTRIBUTE_CONTACT_NAME,
				KtunaxaConstant.ATTRIBUTE_EMAIL,
				KtunaxaConstant.ATTRIBUTE_CONTACT_PHONE,
				KtunaxaConstant.ATTRIBUTE_CONTACT_ADDRESS);
		formItems.addAll(contactItems);

		RowSpacerItem typeSpacer = new RowSpacerItem("type-info-spacer");
		HeaderItem typeHeader = new HeaderItem("type-info-header");
		typeHeader.setDefaultValue("General information regarding the referral");
		typeHeader.setColSpan(COLSPAN);
		formItems.add(typeSpacer);
		formItems.add(typeHeader);

		List<FormItem> typeItems = grabList(itemMap, 
				KtunaxaConstant.ATTRIBUTE_TYPE,
				KtunaxaConstant.ATTRIBUTE_STATUS,
				KtunaxaConstant.ATTRIBUTE_STOP_REASON,
				KtunaxaConstant.ATTRIBUTE_DECISION,
				KtunaxaConstant.ATTRIBUTE_PROVINCIAL_DECISION,
				KtunaxaConstant.ATTRIBUTE_APPLICATION_TYPE,
				KtunaxaConstant.ATTRIBUTE_TARGET_REFERRAL,
				KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_PROVINCE,
				KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL,
				KtunaxaConstant.ATTRIBUTE_CONFIDENTIAL);
		formItems.addAll(typeItems);

		RowSpacerItem dateSpacer = new RowSpacerItem("date-info-spacer");
		HeaderItem dateHeader = new HeaderItem("date-info-header");
		dateHeader.setDefaultValue("Project deadline information");
		dateHeader.setColSpan(COLSPAN);
		formItems.add(dateSpacer);
		formItems.add(dateHeader);

		List<FormItem> dateItems = grabList(itemMap, 
				KtunaxaConstant.ATTRIBUTE_RECEIVED_DATE,
				KtunaxaConstant.ATTRIBUTE_RESPONSE_DATE,
				KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE);
		formItems.addAll(dateItems);
		
		// Shouldn't crash when someone adds more attributes !
		if (itemMap.size() > 0) {
			RowSpacerItem otherSpacer = new RowSpacerItem("other-info-spacer");
			HeaderItem otherHeader = new HeaderItem("other-info-header");
			otherHeader.setDefaultValue("Other information");
			otherHeader.setColSpan(COLSPAN);
			formItems.add(otherSpacer);
			formItems.add(otherHeader);
			formItems.addAll(itemMap.values());
		}

		getWidget().setWidth("100%");
		getWidget().setNumCols(2);
		getWidget().setColWidths(175, "50%");

		setDisabledStatusOnFields(false);
	}

	/**
	 * Grab a list of items from the map of items. Removes the items so they can only be used once.
	 * @param itemMap
	 * @param names
	 * @return the list
	 */
	private List<FormItem> grabList(Map<String, FormItem> itemMap, String... names) {
		List<FormItem> items = new ArrayList<FormItem>();
		for (String name : names) {
			if (itemMap.containsKey(name)) {
				items.add(itemMap.get(name));
			}
		}
		for (String name : names) {
			itemMap.remove(name);
		}
		return items;
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		setDisabledStatusOnFields(disabled);
	}

	/**
	 * Disable form elements which should not be editable (depending on whether logged in user is admin).
	 *
	 * @param base disabled state
	 */
	private void setDisabledStatusOnFields(boolean base) {
		Set<String> enabled = ENABLED_FIELDS_OTHER;
		if (UserContext.getInstance().isReferralManager()) {
			enabled = ENABLED_FIELDS_REFERRAL_MANAGER;
		} else if (UserContext.getInstance().isAdmin() || UserContext.getInstance().isDataEntry()) {
			enabled = ENABLED_FIELDS_ADMIN;
		} else if (UserContext.getInstance().isGuest()) {
			enabled = ENABLED_FIELDS_GUEST;
		}
		for (FormItem formItem : formItems) {
			formItem.setDisabled(base || !enabled.contains(formItem.getName()));
		}
	}

}
