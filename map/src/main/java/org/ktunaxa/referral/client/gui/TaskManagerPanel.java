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

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

/**
 * Panel that displays the BPM information (my tasks and unassigned tasks).
 *
 * @author Joachim Van der Auwera
 */
public class TaskManagerPanel extends VLayout {

	/** Name of the panel in navigation link. */
	public static final String NAME = "TASK MANAGER";

	private MyTasksPanel panelMyTasks;
	private UnassignedTasksPanel panelUnassigned;
	private TabSet tabs;

	/** Constructs a new referral panel. */
	public TaskManagerPanel() {
		super();
		setSize("100%", "100%");

		tabs = new TabSet();
		tabs.setSize("100%", "100%");
		Tab tabMyTasks = new Tab("My Tasks");
		Tab tabUnassigned = new Tab("Unassigned");

		panelMyTasks = new MyTasksPanel();
		panelUnassigned = new UnassignedTasksPanel();
		tabMyTasks.setPane(panelMyTasks);
		tabUnassigned.setPane(panelUnassigned);
		tabs.setTabs(tabMyTasks, tabUnassigned);
		addMember(tabs);
		tabs.addTabSelectedHandler(new TabSelectedHandler() {
			public void onTabSelected(TabSelectedEvent tabSelectedEvent) {
				// ensure the latest information is displayed in the pane
				tabSelectedEvent.getTab().getPane().show();
			}
		});
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		panelMyTasks.init(referralLayer, referral);
		panelUnassigned.init(referralLayer, referral);
	}

	public String getName() {
		return NAME;
	}

	@Override
	public void show() {
		super.show();
		tabs.getSelectedTab().getPane().show(); // assure selected tab is refreshed
	}
}
