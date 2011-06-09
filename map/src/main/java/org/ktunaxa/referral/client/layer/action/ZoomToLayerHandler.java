/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.layer.action;

import org.ktunaxa.referral.client.layer.ReferenceSubLayer;

import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * Handler that zooms to the extent of the sublayer.
 *
 * @author Jan De Moerloose
 */
public class ZoomToLayerHandler implements ClickHandler {

	private ReferenceSubLayer subLayer;

	public ZoomToLayerHandler(ReferenceSubLayer subLayer) {
		this.subLayer = subLayer;
	}

	public void onClick(MenuItemClickEvent event) {
		// @todo
	}

}
