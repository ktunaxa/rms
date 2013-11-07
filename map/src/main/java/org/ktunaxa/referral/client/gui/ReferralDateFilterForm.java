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

import java.util.LinkedHashMap;

import org.geomajas.gwt.client.util.WidgetLayout;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

/**
 * Form which allows you to set build a referral filter on a date.
 * 
 * @author Joachim Van der Auwera
 */
public class ReferralDateFilterForm extends HLayout {

	private static final String BEFORE = "before";

	private static final String AFTER = "after";

	private static final String CQL_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZ";

	private final DateItem date = new DateItem();

	private final ComboBoxItem field = new ComboBoxItem();

	private final RadioGroupItem compare = new RadioGroupItem();

	/**
	 * Constructor.
	 * 
	 * @param changedHandler changed handler which updates the layer filter (probably using {@link #getFilter()}
	 */
	public ReferralDateFilterForm(ChangedHandler changedHandler) {
		super();

		setMargin(WidgetLayout.marginSmall);
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setSize("50px", "1px");
		addMember(spacer);

		LinkedHashMap<String, String> fieldValues = new LinkedHashMap<String, String>();
		fieldValues.put("", "none");
		fieldValues.put("receiveDate", "Receive date");
		fieldValues.put("createDate", "Creation date");
		fieldValues.put("responseDeadline", "Response deadline");
		fieldValues.put("responseDate", "Response date");
		field.setTitle("Date");
		field.setValueMap(fieldValues);
		field.setValue("");
		field.addChangedHandler(changedHandler);

		LinkedHashMap<String, String> compareValues = new LinkedHashMap<String, String>();
		compareValues.put(AFTER, "after");
		compareValues.put(BEFORE, "before");
		compare.setTitle("Status");
		compare.setValue(AFTER);
		compare.setValueMap(compareValues);
		compare.addChangedHandler(changedHandler);
		compare.setVertical(false);

		date.setTitle("");
		date.addChangedHandler(changedHandler);

		DynamicForm form = new DynamicForm();
		form.setPadding(WidgetLayout.marginSmall);
		form.setBorder("1px dashed #CCCCCC");
		form.setFields(field, compare, date);

		addMember(form);
	}

	/**
	 * Get filter which is built in this form.
	 * 
	 * @return filter string, empty string when no filter defined
	 */
	public String getFilter() {
		String fieldName = field.getValueAsString();
		if (!"".equals(fieldName)) {
			DateTimeFormat format = DateTimeFormat.getFormat(CQL_TIME_FORMAT);
			String filter = fieldName + " " + compare.getValue();
			String valueString = format.format(date.getValueAsDate());
			if ("AFTER".equals(compare.getValue())) {
				// we can't discriminate between date and timestamp values yet,
				// use end of day for now
				filter += " " + valueString.replaceAll("\\d\\d:\\d\\d:\\d\\d", "23:59:59");
			} else {
				// we can't discriminate between date and timestamp values yet,
				// use start of day for now
				filter += " " + valueString.replaceAll("\\d\\d:\\d\\d:\\d\\d", "00:00:00");
			}
			return filter;
		}
		return "";
	}
}
