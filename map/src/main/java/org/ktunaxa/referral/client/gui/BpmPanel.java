/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;

/**
 * Panel that displays the BPM information (my tasks and unassigned tasks).
 *
 * @author Joachim Van der Auwera
 */
public class BpmPanel extends VLayout {

	/** Name of the panel in navigation link. */
	public static final String NAME = "BPM";

	private MyTasksPanel panelMyTasks;
	private UnassignedTasksPanel panelUnassigned;
	private TabSet tabs;

	/** Constructs a new referral panel. */
	public BpmPanel() {
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
