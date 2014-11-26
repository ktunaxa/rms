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
package org.ktunaxa.referral.client.action;

import org.geomajas.gwt.client.action.ToolbarModalAction;
import org.geomajas.gwt.client.i18n.I18nProvider;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.MapWidget;

import com.smartgwt.client.widgets.events.ClickEvent;


public class WmsFeatureInfoModalAction extends ToolbarModalAction {

	private MapWidget mapWidget;

	private WmsFeatureInfoController controller;

	/** Number of pixels that describes the tolerance allowed when trying to select features. */
	private int pixelTolerance = 5;

	// Constructor:

	public WmsFeatureInfoModalAction(MapWidget mapWidget) {
		super(WidgetLayout.iconInfo, I18nProvider.getToolbar().featureInfoTitle(), I18nProvider
				.getToolbar().featureInfoTooltip());
		this.mapWidget = mapWidget;
		controller = new WmsFeatureInfoController(mapWidget, pixelTolerance);
	}

	// ToolbarModalAction implementation:

	@Override
	public void onSelect(ClickEvent event) {
		mapWidget.setController(controller);
	}

	@Override
	public void onDeselect(ClickEvent event) {
		mapWidget.setController(null);
	}

	// Getters and setters:

	public int getPixelTolerance() {
		return pixelTolerance;
	}

	public void setPixelTolerance(int pixelTolerance) {
		this.pixelTolerance = pixelTolerance;
	}
}
