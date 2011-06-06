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
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.client.widget.ListView;
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			"Ecology evaluator",
			"Evaluate evaluator",
			"Referral manager",
			"Treaty evaluator"
	};

	// Candidate group string to test, positions need to match {@link #CANDIDATE_TITLES}
	private static final String[] CANDIDATE_CHECKS = {
			"aquatic",
			"archaeology",
			"community",
			"cultural",
			"ecology",
			"evaluate",
			"referral",
			"treaty"
	};

	// index of the referralManager role in the candidate lists
	private static final int MANAGER = 6;

	private SectionStack groups;

	private SectionStackSection[] sections = new SectionStackSection[CANDIDATE_CHECKS.length];
	private ListView<TaskDto>[] views = new ListView[CANDIDATE_CHECKS.length];
	private List<AbstractCollapsibleListBlock<TaskDto>>[] lists = new List[CANDIDATE_CHECKS.length];

	public UnassignedTasksPanel() {
		setWidth100();

		groups = new SectionStack();
		groups.setSize("100%", "100%");
		groups.setOverflow(Overflow.AUTO);
		groups.setVisibilityMode(VisibilityMode.MULTIPLE);
		groups.setPadding(5);
		addChild(groups);

		Map<String, Comparator<AbstractCollapsibleListBlock<TaskDto>>> sortAttributes;
		sortAttributes = new HashMap<String, Comparator<AbstractCollapsibleListBlock<TaskDto>>>();
		//sortAttributes.put("date", new DateComparator());

		for (int i = 0 ; i < CANDIDATE_CHECKS.length ; i++) {
			sections[i] = new SectionStackSection(CANDIDATE_TITLES[i]);
			views[i] = new ListView<TaskDto>(false, true, sortAttributes);
			lists[i] = new ArrayList<AbstractCollapsibleListBlock<TaskDto>>();
			sections[i].addItem(views[i]);
			groups.addSection(sections[i]); // @todo @sec only add when the role is assigned to the user
		}
	}

	public void init(VectorLayer referralLayer, Feature referral) {
	}

	@Override
	public void show() {
		super.show();

		for (int i = 0 ; i < CANDIDATE_CHECKS.length ; i++) {
			lists[i].clear();
			views[i].populate(lists[i]);
			sections[i].setExpanded(false);
		}

		GetTasksRequest request = new GetTasksRequest();
		request.setIncludeUnassignedTasks(true);
		GwtCommand command = new GwtCommand(GetTasksRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetTasksResponse>() {
			public void execute(GetTasksResponse response) {
				for (TaskDto task : response.getTasks()) {
					TaskBlock block = new TaskBlock(task);
					String candidates = task.getCandidates().toString();
					boolean added = false;
					for (int i = 0 ; i < CANDIDATE_CHECKS.length ; i++) {
						if (candidates.contains(CANDIDATE_CHECKS[i])) {
							lists[i].add(block);
							added = true;
						}
					}
					if (!added) {
						lists[MANAGER].add(block);
					}
				}
				for (int i = 0 ; i < CANDIDATE_CHECKS.length ; i++) {
					int count = lists[i].size();
					if (count > 0) {
						sections[i].setTitle(CANDIDATE_TITLES[i] +
								"  (<span style=\"font-weight:bold;\">" + count + "</span>)");
						sections[i].setExpanded(true);
					}
					views[i].populate(lists[i]); // @todo @sec only add when the role is assigned to the user
				}
			}
		});
	}
}
