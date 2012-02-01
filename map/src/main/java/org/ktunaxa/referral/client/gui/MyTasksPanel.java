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
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

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
		addMember(view);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}

	@Override
	public void show() {
		super.show();

		list.clear();
		view.populate(list);
		view.collapse();

		GetTasksRequest request = new GetTasksRequest();
		request.setAssignee(UserContext.getInstance().getUser());
		GwtCommand command = new GwtCommand(GetTasksRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetTasksResponse>() {
			public void execute(GetTasksResponse response) {
				list.clear(); // clear again to avoid double AJAX calls causing duplicates
				for (TaskDto task : response.getTasks()) {
					TaskBlock block = new TaskBlock(task);
					list.add(block);
				}
				view.populate(list);
			}
		});
	}

}
