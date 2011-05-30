/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;

/**
 * Panel to display tasks which are assigned to me.
 *
 * @author Joachim Van der Auwera
 */
public class MyTasksPanel extends VLayout {

	public MyTasksPanel() {
		setWidth100();
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// nothing to do for now
	}
}
