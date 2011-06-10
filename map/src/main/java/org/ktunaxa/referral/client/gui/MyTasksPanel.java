/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel to display tasks which are assigned to me.
 *
 * @author Joachim Van der Auwera
 */
public class MyTasksPanel extends VLayout {

	private TaskListView view;
	private List<AbstractCollapsibleListBlock<TaskDto>> list = new ArrayList<AbstractCollapsibleListBlock<TaskDto>>();

	public MyTasksPanel() {
		super();

		setWidth100();
		view = new TaskListView();
		addChild(view);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}

	@Override
	public void show() {
		super.show();

		list.clear();
		view.populate(list);

		GetTasksRequest request = new GetTasksRequest();
		request.setAssignee(UserContext.getInstance().getUser());
		GwtCommand command = new GwtCommand(GetTasksRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetTasksResponse>() {
			public void execute(GetTasksResponse response) {
				for (TaskDto task : response.getTasks()) {
					TaskBlock block = new TaskBlock(task);
					list.add(block);
				}
				view.populate(list);
			}
		});
	}

}
