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
import com.smartgwt.client.widgets.form.fields.FormItem;
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
import org.geomajas.layer.feature.attribute.IntegerAttribute;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

/**
 * Form for editing comments.
 * 
 * @author Jan De Moerloose
 */
public class CommentForm extends DefaultFeatureForm {

	private FormItem content;
	private FormItem includeInReport;
	private FormItem reportContent;
	private FormItem createdBy;
	private FormItem creationDate;

	public CommentForm(FeatureInfo commentsInfo, VectorLayer referralLayer) {
		super(commentsInfo, new DefaultAttributeProvider(referralLayer, KtunaxaConstant.ATTRIBUTE_COMMENTS));
		getWidget().setColWidths("120", "*");
		setDisabled(false);
	}

	@Override
	public FormItem createItem(AbstractReadOnlyAttributeInfo info) {
		FormItem item = super.createItem(info);
		if (KtunaxaConstant.ATTRIBUTE_COMMENT_CONTENT.equals(info.getName())) {
			content = item;
		}
		if (KtunaxaConstant.ATTRIBUTE_COMMENT_INCLUDE_IN_REPORT.equals(info.getName())) {
			includeInReport = item;
		}
		if (KtunaxaConstant.ATTRIBUTE_COMMENT_REPORT_CONTENT.equals(info.getName())) {
			reportContent = item;
		}
		if (KtunaxaConstant.ATTRIBUTE_COMMENT_CREATED_BY.equals(info.getName())) {
			createdBy = item;
		}
		if (KtunaxaConstant.ATTRIBUTE_COMMENT_CREATION_DATE.equals(info.getName())) {
			creationDate = item;
		}
		return item;
	}

	@Override
	public void prepareForm(FormItemList formItems, DataSource source) {
		super.prepareForm(formItems, source);
		includeInReport.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				boolean selected = isIncludeInReportAndDisable();
				if (selected) {
					if (isEmpty(reportContent.getValue())) {
						reportContent.setValue(content.getValue());
					}
				}
			}
		});
		isIncludeInReportAndDisable();
		createdBy.setDisabled(true);
		creationDate.setDisabled(true);
	}

	private boolean isIncludeInReportAndDisable() {
		boolean value = (Boolean) includeInReport.getValue();
		reportContent.setDisabled(!value);
		return value;
	}

	private boolean isEmpty(Object value) {
		return value == null || "".equals(value.toString().trim());
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		isIncludeInReportAndDisable();
		createdBy.setDisabled(true);
		creationDate.setDisabled(true);
	}

	@Override
	public void toForm(String name, Attribute<?> attribute) {
		if (KtunaxaConstant.ATTRIBUTE_COMMENT_POSITION.equals(name)) {
			if (attribute instanceof IntegerAttribute && null == attribute.getValue()) {
				((IntegerAttribute) attribute).setValue(2);
			}
		}
		super.toForm(name, attribute);
	}

	@Override
	public void toForm(AssociationValue value) {
		super.toForm(value);
		if (isEmpty(createdBy.getValue())) {
			createdBy.setValue(UserContext.getInstance().getUser());
		}
	}

}