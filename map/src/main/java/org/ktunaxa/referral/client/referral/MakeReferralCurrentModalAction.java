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

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.action.ToolbarModalAction;
import org.geomajas.gwt.client.widget.MapWidget;

import com.smartgwt.client.widgets.events.ClickEvent;

/**
 * Sets a controller that makes a referral current by clicking on it.
 * 
 * @author Joachim Van der Auwera
 * @author Pieter De Graef
 * @author Jan De Moerloose
 */
public class MakeReferralCurrentModalAction extends ToolbarModalAction {

	/** Number of pixels that describes the tolerance allowed when trying to select features. */
	private static final int PIXEL_TOLERANCE = 5;

	private MapWidget mapWidget;

	private MakeReferralCurrentController controller;

	// Constructor:

	public MakeReferralCurrentModalAction(MapWidget mapWidget) {
		super("[ISOMORPHIC]/images/selectReferral.png", "Select referral and make current");
		this.mapWidget = mapWidget;
		controller = new MakeReferralCurrentController(mapWidget, PIXEL_TOLERANCE);
	}

	@Override
	public void onSelect(ClickEvent event) {
		mapWidget.setController(controller);
	}

	@Override
	public void onDeselect(ClickEvent event) {
		mapWidget.setController(null);
	}
}
