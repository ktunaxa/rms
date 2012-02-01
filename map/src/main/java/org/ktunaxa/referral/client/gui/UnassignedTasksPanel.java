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

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Panel to display unassigned tasks.
 *
 * @author Joachim Van der Auwera
 */
public class UnassignedTasksPanel extends VLayout {

	// Candidate group titles, positions need to match {@link #CANDIDATE_CHECKS}
	private static final String[] CANDIDATE_TITLES = {
			"Aquatic evaluator",
			"Archaeology evaluator",
			"Community manager",
			"Cultural evaluator",
			"Terrestrial evaluator",
			"Referral manager",
			"Treaty evaluator"
	};

	// Candidate group string to test, positions need to match {@link #CANDIDATE_TITLES}
	private static final String[][] CANDIDATE_CHECKS = {
			{KtunaxaBpmConstant.ROLE_AQUATIC},
			{KtunaxaBpmConstant.ROLE_ARCHAEOLOGY},
			{KtunaxaBpmConstant.ROLE_COMMUNITY_AKISQNUK, KtunaxaBpmConstant.ROLE_COMMUNITY_LOWER_KOOTENAY,
					KtunaxaBpmConstant.ROLE_COMMUNITY_ST_MARYS, KtunaxaBpmConstant.ROLE_COMMUNITY_TOBACCO_PLAINS},
			{KtunaxaBpmConstant.ROLE_CULTURAL},
			{KtunaxaBpmConstant.ROLE_TERRESTRIAL},
			{KtunaxaBpmConstant.ROLE_REFERRAL_MANAGER},
			{KtunaxaBpmConstant.ROLE_TREATY}
	};

	// index of the referralManager role in the candidate lists
	private static final int MANAGER = 6;

	private SectionStackSection[] sections = new SectionStackSection[CANDIDATE_CHECKS.length];
	private TaskListView[] views = new TaskListView[CANDIDATE_CHECKS.length];
	private List<AbstractCollapsibleListBlock<TaskDto>>[] lists = new List[CANDIDATE_CHECKS.length];

	private SectionStack groups;

	public UnassignedTasksPanel() {
		super();
		setWidth100();
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}

	@Override
	public void show() {
		super.show();
		if (null != groups) {
			removeMember(groups);
		}
		groups = new SectionStack();
		groups.setSize("100%", "100%");
		groups.setOverflow(Overflow.AUTO);
		groups.setVisibilityMode(VisibilityMode.MULTIPLE);
		groups.setPadding(LayoutConstant.MARGIN_SMALL);
		addMember(groups);
		
		UserContext user = UserContext.getInstance();
		for (int i = 0 ; i < CANDIDATE_CHECKS.length ; i++) {
			if (user.hasBpmRole(CANDIDATE_CHECKS[i])) {
				sections[i] = new SectionStackSection(CANDIDATE_TITLES[i]);
				sections[i].setTitle(CANDIDATE_TITLES[i]);
				sections[i].setExpanded(false);
				views[i] = new TaskListView();
				lists[i] = new ArrayList<AbstractCollapsibleListBlock<TaskDto>>();
			}
		}

 		GetTasksRequest request = new GetTasksRequest();
		request.setIncludeUnassignedTasks(true);
		GwtCommand command = new GwtCommand(GetTasksRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetTasksResponse>() {
			public void execute(GetTasksResponse response) {
				UserContext user = UserContext.getInstance();
				for (TaskDto task : response.getTasks()) {
					TaskBlock block = new TaskBlock(task);
					String candidates = task.getCandidates().toString();
					boolean added = false;
					for (int i = 0 ; i < CANDIDATE_CHECKS.length ; i++) {
						for (String role : CANDIDATE_CHECKS[i]) {
							if (candidates.contains(role)) {
								if (user.hasBpmRole(role)) {
									lists[i].add(block);
								}
								added = true;
							}
						}
					}
					if (!added) {
						if (user.isReferralManager()) {
							lists[MANAGER].add(block);
						}
					}
				}
				int sectionToExpand = 0;
				int sectionsToExpandCount = 0;
				for (int i = 0 ; i < CANDIDATE_CHECKS.length ; i++) {
					if (user.hasBpmRole(CANDIDATE_CHECKS[i])) {
						int count = lists[i].size();
						if (count > 0) {
							sections[i].setTitle(CANDIDATE_TITLES[i] +
									"  (<span style=\"font-weight:bold;\">" + count + "</span>)");
							sectionToExpand = i;
							sectionsToExpandCount++;
						}
						views[i].populate(lists[i]);
						sections[i].addItem(views[i]);
						groups.addSection(sections[i]);
					}
				}
				if (1 == sectionsToExpandCount) {
					sections[sectionToExpand].setExpanded(true);
				}
			}
		});
	}
}
