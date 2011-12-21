/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import org.geomajas.gwt.client.util.WidgetLayout;

import java.util.LinkedHashMap;

/**
 * Form which allows you to set build a referral filter on a date.
 *
 * @author Joachim Van der Auwera
 */
public class ReferralDateFilterForm extends HLayout {

	private static final String BEFORE = "before";
	private static final String AFTER = "after";

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
			DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
			return fieldName + " " + compare.getValue() + " " + format.format(date.getValueAsDate()) + "T00:00:00Z";
		}
		return "";
	}

}
