/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Panel to display tasks for the current referral.
 *
 * @author Joachim Van der Auwera
 */
public class ReferralTasksPanel extends VLayout {

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

	public ReferralTasksPanel() {
		super();
		setWidth100();

		SectionStack groups = new SectionStack();
		groups.setSize("100%", "100%");
		groups.setOverflow(Overflow.AUTO);
		groups.setVisibilityMode(VisibilityMode.MULTIPLE);
		groups.setPadding(5);
		addChild(groups);

		for (int i = 0 ; i < GROUP_TITLES.length ; i++) {
			sections[i] = new SectionStackSection(GROUP_TITLES[i]);
			views[i] = new TaskListView();
			lists[i] = new ArrayList<AbstractCollapsibleListBlock<TaskDto>>();
			if (GROUP_CURRENT == i) {
				sections[i].addItem(currentTaskBlock);
			} else {
				sections[i].addItem(views[i]);
			}
			if (i == GROUP_CURRENT || UserContext.getInstance().isReferralAdmin()) {
				// only add current task unless logged in user is referral administrator
				groups.addSection(sections[i]);
			}
		}
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}

	@Override
	public void show() {
		super.show();

		for (int i = 0 ; i < GROUP_TITLES.length ; i++) {
			sections[i].setExpanded(false);
			if (GROUP_CURRENT != i) {
				sections[i].setTitle(GROUP_TITLES[i]);
				lists[i].clear();
				views[i].populate(lists[i]);
			}
		}

		MapLayout mapLayout = MapLayout.getInstance();
		TaskDto task = mapLayout.getCurrentTask();
		currentTaskBlock.refresh(task);

		if (null != task) {
			sections[GROUP_CURRENT].setExpanded(true);
		} else {
			sections[GROUP_OPEN].setExpanded(true);
		}

		org.geomajas.layer.feature.Feature referral = mapLayout.getCurrentReferral();
		if (null != referral) {
			GetTasksRequest request = new GetTasksRequest();
			request.setReferralId(ReferralUtil.createId(referral));
			GwtCommand command = new GwtCommand(GetTasksRequest.COMMAND);
			command.setCommandRequest(request);
			GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetTasksResponse>() {
				public void execute(GetTasksResponse response) {
					for (TaskDto task : response.getTasks()) {
						// @todo @sec only when allowed to see this task
						TaskBlock block = new TaskBlock(task);
						if (null != task.getCreateTime()) { // @todo check is wrong
							lists[GROUP_OPEN].add(block);
						} else {
							lists[GROUP_FINISHED].add(block);
						}
					}
					for (int i = 0 ; i < GROUP_TITLES.length ; i++) {
						if (GROUP_CURRENT != i) {
							int count = lists[i].size();
							if (count > 0) {
								sections[i].setTitle(GROUP_TITLES[i] +
										"  (<span style=\"font-weight:bold;\">" + count + "</span>)");
							}
							views[i].populate(lists[i]); // @todo @sec only add when the role is assigned to the user
						}
					}
				}
			});
		}
	}
}
