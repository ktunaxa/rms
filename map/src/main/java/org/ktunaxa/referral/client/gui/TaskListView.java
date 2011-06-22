/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.client.widget.ListView;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link ListView} for looking at and sorting {@link TaskDto} instances.
 *
 * @author Joachim Van der Auwera
 */
public class TaskListView extends ListView<TaskDto> {

	private static final Map<String, Comparator<AbstractCollapsibleListBlock<TaskDto>>> SORT_ATTRIBUTES;

	static {
		SORT_ATTRIBUTES = new HashMap<String, Comparator<AbstractCollapsibleListBlock<TaskDto>>>();
		//SORT_ATTRIBUTES.put("date", new DateComparator());
	}

	public TaskListView() {
		super(false, true, SORT_ATTRIBUTES);
	}
}