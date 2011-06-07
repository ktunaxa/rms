/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.widget.CardLayout;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Block which displays the current task with the ability to set variables and finish the task.
 *
 * @author Joachim Van der Auwera
 */
public class CurrentTaskBlock extends CardLayout {

	private static final String KEY_NO = "no";
	private static final String KEY_CURRENT = "curr";

	public CurrentTaskBlock() {
		VLayout noTask = new VLayout();
		HTMLFlow noTaskText = new HTMLFlow("No current task, please select one.");
		noTaskText.setWidth100();
		noTask.addChild(noTaskText);
		VLayout currentTask = new VLayout();
		currentTask.addChild(new Button("Finish task"));

		addCard(KEY_NO, noTask);
		addCard(KEY_CURRENT, currentTask);
		showCard(KEY_NO);
	}

	public void refresh(TaskDto task) {
		if (null == task) {
			showCard(KEY_NO);
		} else {
			showCard(KEY_CURRENT);
		}
	}
}
