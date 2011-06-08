/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Base class for building a task form.
 *
 * @author Joachim Van der Auwera
 */
public abstract class AbstractTaskForm extends HLayout {

	private HTMLFlow taskTitle = new HTMLFlow();

	public AbstractTaskForm() {
		setWidth100();
		taskTitle.setWidth100();
		addChild(taskTitle);
	}

	public void refresh(TaskDto task) {
		taskTitle.setContents("<div style=\"font-weight:bold;\">" + task.getName() + "</div>" +
			"<div>" + task.getDescription() + "</div>");
	}

}
