package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;

/**
 * Panel to display unassigned tasks.
 *
 * @author Joachim Van der Auwera
 */
public class UnassignedTasksPanel extends VLayout {

	public UnassignedTasksPanel() {
		setWidth100();
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}
}
