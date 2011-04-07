/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.ktunaxa.referral.client.layer.action;

import org.ktunaxa.referral.client.layer.ReferenceSubLayer;

import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * Handler that refreshes the sublayer.
 * 
 * @author Jan De Moerloose
 * 
 */
public class RefreshLayerHandler implements ClickHandler {

	private ReferenceSubLayer subLayer;

	public RefreshLayerHandler(ReferenceSubLayer subLayer) {
		this.subLayer = subLayer;
	}

	public void onClick(MenuItemClickEvent event) {
	}


}
