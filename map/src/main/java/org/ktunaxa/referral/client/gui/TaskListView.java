/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.client.widget.ListView;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.Comparator;
import java.util.Date;
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
		SORT_ATTRIBUTES.put("deadline", new DeadlineComparator());
		SORT_ATTRIBUTES.put("referral", new ReferralComparator());
		SORT_ATTRIBUTES.put("creation", new CreationComparator());
	}

	public TaskListView() {
		super(false, true, SORT_ATTRIBUTES);
	}

	/**
	 * Comparator for sorting on the completion deadline.
	 *
	 * @author Joachim Van der Auwera
	 */
	private static class DeadlineComparator implements Comparator<AbstractCollapsibleListBlock<TaskDto>> {

		public int compare(AbstractCollapsibleListBlock<TaskDto> left,
				AbstractCollapsibleListBlock<TaskDto> right) {
			String leftValue = left.getObject().getVariables().get(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE);
			String rightValue = right.getObject().getVariables().get(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE);
			return leftValue.compareTo(rightValue);
		}
	}

	/**
	 * Comparator for sorting on the referral id.
	 *
	 * @author Joachim Van der Auwera
	 */
	private static class ReferralComparator implements Comparator<AbstractCollapsibleListBlock<TaskDto>> {

		public int compare(AbstractCollapsibleListBlock<TaskDto> left,
				AbstractCollapsibleListBlock<TaskDto> right) {
			String leftValue = left.getObject().getVariables().get(KtunaxaBpmConstant.VAR_REFERRAL_ID);
			String rightValue = right.getObject().getVariables().get(KtunaxaBpmConstant.VAR_REFERRAL_ID);
			return leftValue.compareTo(rightValue);
		}
	}

	/**
	 * Comparator for sorting on the creation time.
	 *
	 * @author Joachim Van der Auwera
	 */
	private static class CreationComparator implements Comparator<AbstractCollapsibleListBlock<TaskDto>> {

		public int compare(AbstractCollapsibleListBlock<TaskDto> left,
				AbstractCollapsibleListBlock<TaskDto> right) {
			Date leftValue = left.getObject().getCreateTime();
			Date rightValue = right.getObject().getCreateTime();
			return leftValue.compareTo(rightValue);
		}
	}
}