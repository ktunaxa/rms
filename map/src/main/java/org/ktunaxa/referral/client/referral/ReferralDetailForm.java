/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral;

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.configuration.AssociationType;
import org.geomajas.configuration.AttributeInfo;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureForm;
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
			KtunaxaConstant.ATTRIBUTE_PRIMARY,
			KtunaxaConstant.ATTRIBUTE_SECONDARY,
			KtunaxaConstant.ATTRIBUTE_NUMBER,
			KtunaxaConstant.ATTRIBUTE_YEAR,
			KtunaxaConstant.ATTRIBUTE_ACTIVE_RETENTION_PERIOD,
			KtunaxaConstant.ATTRIBUTE_SEMI_ACTIVE_RETENTION_PERIOD,
			KtunaxaConstant.ATTRIBUTE_FINAL_DISPOSITION
		};

	public ReferralDetailForm(VectorLayer layer) {
		super(layer);
		getWidget().setStyleName(REFERRAL_FORM_STYLE);
	}

	@Override
	protected FormItem createItem(AttributeInfo info) {
		FormItem formItem = super.createItem(info);
		if (KtunaxaConstant.ATTRIBUTE_TARGET_REFERRAL.equals(info.getName())) {
			SelectItem targetItem = (SelectItem) formItem;
			targetItem.setOptionDataSource(new ReferralManyToOneDataSource(info,
					KtunaxaConstant.REFERRAL_SERVER_LAYER_ID));
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
	protected boolean isIncluded(AttributeInfo info) {
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
	protected void prepareForm(FormItemList formItems, DataSource source) {
		HeaderItem projectHeader = new HeaderItem("project-info-header");
		projectHeader.setDefaultValue("General project information");
		formItems.insertBefore("applicantName", projectHeader);

		RowSpacerItem externalSpacer = new RowSpacerItem("external-info-spacer");
		HeaderItem externalHeader = new HeaderItem("external-info-header");
		externalHeader.setDefaultValue("External project information");
		externalHeader.setColSpan(COLSPAN);
		formItems.insertBefore("externalProjectId", externalSpacer, externalHeader);

		RowSpacerItem contactSpacer = new RowSpacerItem("contact-info-spacer");
		HeaderItem contactHeader = new HeaderItem("contact-info-header");
		contactHeader.setDefaultValue("Referral contact information");
		contactHeader.setColSpan(COLSPAN);
		formItems.insertBefore("contactName", contactSpacer, contactHeader);

		RowSpacerItem typeSpacer = new RowSpacerItem("type-info-spacer");
		HeaderItem typeHeader = new HeaderItem("type-info-header");
		typeHeader.setDefaultValue("General information regarding the referral");
		typeHeader.setColSpan(COLSPAN);
		formItems.insertBefore("referralType", typeSpacer, typeHeader);

		RowSpacerItem dateSpacer = new RowSpacerItem("date-info-spacer");
		HeaderItem dateHeader = new HeaderItem("date-info-header");
		dateHeader.setDefaultValue("Project deadline information");
		dateHeader.setColSpan(COLSPAN);
		formItems.insertBefore("receiveDate", dateSpacer, dateHeader);

		RowSpacerItem documentSpacer = new RowSpacerItem("document-info-spacer");
		HeaderItem documentHeader = new HeaderItem("document-info-header");
		documentHeader.setDefaultValue("Document management classification");
		documentHeader.setColSpan(COLSPAN);
		formItems.insertBefore("activeRetentionPeriod", documentSpacer, documentHeader);

		getWidget().setWidth("100%");
		getWidget().setNumCols(2);
		getWidget().setColWidths(175, "50%");
	}

}
