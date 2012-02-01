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
		SORT_ATTRIBUTES.put("Deadline", new DeadlineComparator());
		SORT_ATTRIBUTES.put("Referral", new ReferralComparator());
		SORT_ATTRIBUTES.put("Creation", new CreationComparator());
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