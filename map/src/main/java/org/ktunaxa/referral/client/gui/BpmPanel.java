package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;

/**
 * Panel that displays the BPM information (my tasks and unassigned tasks).
 *
 * @author Joachim Van der Auwera
 */
public class BpmPanel extends VLayout {

	/**
	 * Name of the panel in navigation link.
	 */
	public static final String NAME = "BPM";

	private MyTasksPanel panelMyTasks;
	private UnassignedTasksPanel panelUnassigned;

	/**
	 * Constructs a new referral panel.
	 */
	public BpmPanel() {
		setSize("100%", "100%");

		TabSet tabs = new TabSet();
		tabs.setSize("100%", "100%");
		Tab tabMyTasks = new Tab("My Tasks");
		Tab tabUnassigned = new Tab("Unassigned");

		panelMyTasks = new MyTasksPanel();
		panelUnassigned = new UnassignedTasksPanel();
		tabMyTasks.setPane(panelMyTasks);
		tabUnassigned.setPane(panelUnassigned);
		tabs.setTabs(tabMyTasks, tabUnassigned);
		addMember(tabs);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		panelMyTasks.init(referralLayer, referral);
		panelUnassigned.init(referralLayer, referral);
	}

	public String getName() {
		return NAME;
	}


}
