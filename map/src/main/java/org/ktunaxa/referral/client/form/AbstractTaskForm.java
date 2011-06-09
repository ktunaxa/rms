/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HLayout;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.Map;

/**
 * Base class for building a task form.
 *
 * @author Joachim Van der Auwera
 */
public abstract class AbstractTaskForm extends HLayout {

	private HTMLFlow taskTitle = new HTMLFlow();
	private DynamicForm form = new DynamicForm();

	public AbstractTaskForm() {
		super();

		setWidth100();
		taskTitle.setWidth100();
		addChild(taskTitle);

		form.setWidth100();
		addChild(form);
	}

	public void refresh(TaskDto task) {
		taskTitle.setContents("<div style=\"font-weight:bold;\">" + task.getName() + "</div>" +
				"<div>" + task.getDescription() + "</div>");
	}

	/**
	 * Set the form field items.
	 *
	 * @param formItems form field items
	 */
	public void setFields(FormItem... formItems) {
		form.setFields(formItems);
	}

	/**
	 * Get the variables from the form.
	 *
	 * @return form values
	 */
	public abstract Map<String, String> getVariables();

	/**
	 * Convert object to string in a way that works when the object is null.
	 *
	 * @param object object to convert to string
	 * @return string for the object or null if null was passed
	 */
	protected String nullSafeToString(Object object) {
		if (null == object) {
			return null;
		}
		return object.toString();
	}

}
