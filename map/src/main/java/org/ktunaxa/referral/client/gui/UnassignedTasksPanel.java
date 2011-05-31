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
import org.ktunaxa.referral.server.command.dto.GetTasksRequest;
import org.ktunaxa.referral.server.command.dto.GetTasksResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Panel to display unassigned tasks.
 *
 * @author Joachim Van der Auwera
 */
public class UnassignedTasksPanel extends VLayout {

	private SectionStack groups;

	public UnassignedTasksPanel() {
		setWidth100();

		groups = new SectionStack();
		groups.setSize("100%", "100%");
		groups.setOverflow(Overflow.AUTO);
		groups.setVisibilityMode(VisibilityMode.MULTIPLE);
		groups.setPadding(5);
		addChild(groups);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}

	@Override
	public void show() {
		super.show();

		groups.clear();
		final SectionStackSection aquatic = new SectionStackSection("Aquatic");
		groups.addSection(aquatic);
		final SectionStackSection cultural = new SectionStackSection("Cultural");
		groups.addSection(cultural);

		GetTasksRequest request = new GetTasksRequest();
		request.setIncludeUnassignedTasks(true);
		GwtCommand command = new GwtCommand(GetTasksRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetTasksResponse>() {
			public void execute(GetTasksResponse response) {
				for (TaskDto task : response.getTasks()) {
					TaskBlock block = new TaskBlock(task);
					aquatic.addItem(block);
				}
			}
		});
	}
}
