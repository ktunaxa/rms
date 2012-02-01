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

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel to display tasks for the current referral.
 *
 * @author Joachim Van der Auwera
 */
public class ReferralTasksPanel extends VLayout {

	private static final String STYLE_BLOCK = "sectionList";

	// Candidate group titles, positions need to match {@link #CANDIDATE_CHECKS}
	private static final String[] GROUP_TITLES = {
			"Current task",
			"Open tasks",
			"Finished tasks",
	};

	private static final int GROUP_CURRENT = 0;
	private static final int GROUP_OPEN = 1;
	private static final int GROUP_FINISHED = 2;

	private CurrentTaskBlock currentTaskBlock = new CurrentTaskBlock();
	private SectionStackSection[] sections = new SectionStackSection[GROUP_TITLES.length];
	private TaskListView[] views = new TaskListView[GROUP_TITLES.length];
	private List<AbstractCollapsibleListBlock<TaskDto>>[] lists = new List[GROUP_TITLES.length];

	/**
	 * Construct a {@link ReferralTasksPanel}.
	 */
	public ReferralTasksPanel() {
		super();
		setWidth100();
		currentTaskBlock.setStyleName(STYLE_BLOCK);

		SectionStack groups = new SectionStack();
		groups.setWidth100();
		groups.setOverflow(Overflow.AUTO);
		groups.setVisibilityMode(VisibilityMode.MULTIPLE);
		
		for (int i = 0 ; i < GROUP_TITLES.length ; i++) {
			sections[i] = new SectionStackSection(GROUP_TITLES[i]);
			views[i] = new TaskListView();
			views[i].setStyleName(STYLE_BLOCK);
			lists[i] = new ArrayList<AbstractCollapsibleListBlock<TaskDto>>();
			if (GROUP_CURRENT == i) {
				sections[i].addItem(currentTaskBlock);
			} else {
				sections[i].addItem(views[i]);
			}
			sections[i].setExpanded(false);
			groups.addSection(sections[i]);
		}
		addMember(groups);
	}

	/**
	 * Refresh the tasks displayed in the pane.
	 */
	public void refresh() {
		MapLayout mapLayout = MapLayout.getInstance();
		org.geomajas.layer.feature.Feature referral = mapLayout.getCurrentReferral();
		if (null != referral) {
			GetTasksRequest request = new GetTasksRequest();
			request.setReferralId(ReferralUtil.createId(referral));
			GwtCommand command = new GwtCommand(GetTasksRequest.COMMAND);
			command.setCommandRequest(request);
			GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetTasksResponse>() {
				public void execute(GetTasksResponse response) {
					refresh(response.getTasks());
				}
			});
		} else {
			refresh(null);
		}
	}

	private void refresh(List<TaskDto> tasks) {
		UserContext user = UserContext.getInstance();

		for (int i = 0 ; i < GROUP_TITLES.length ; i++) {
			if (GROUP_CURRENT != i) {
				sections[i].setTitle(GROUP_TITLES[i]);
				lists[i].clear(); // clear again to avoid double AJAX calls causing duplicates
				views[i].populate(lists[i]);
			}
		}
		if (null != tasks) {
			for (TaskDto task : tasks) {
				if ((user.isReferralManager() && task.isHistory() ) || user.hasBpmRole(task.getCandidates())) {
					TaskBlock block = new TaskBlock(task);
					if (!task.isHistory()) {
						lists[GROUP_OPEN].add(block);
					} else {
						lists[GROUP_FINISHED].add(block);
					}
				}
			}
			for (int i = 0 ; i < GROUP_TITLES.length ; i++) {
				if (GROUP_CURRENT != i) {
					int count = lists[i].size();
					if (count > 0) {
						sections[i].setTitle(GROUP_TITLES[i] +
								"  (<span style=\"font-weight:bold;\">" + count + "</span>)");
					}
					views[i].populate(lists[i]);
				}
			}
		}
		TaskDto task = MapLayout.getInstance().getCurrentTask();
		currentTaskBlock.refresh(task);
		if (null != task) {
			sections[GROUP_CURRENT].setExpanded(true);
		} else {
			sections[GROUP_OPEN].setExpanded(true);
		}

	}
}
