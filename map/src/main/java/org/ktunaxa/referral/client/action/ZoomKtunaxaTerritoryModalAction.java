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

import com.smartgwt.client.widgets.events.ClickEvent;
import org.geomajas.gwt.client.action.ToolbarAction;
import org.geomajas.gwt.client.map.MapView;
import org.geomajas.gwt.client.spatial.Bbox;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

/**
 * Tool to zoom to Ktunaxa territory.
 *
 * @author Joachim Van der Auwera
 */
public class ZoomKtunaxaTerritoryModalAction extends ToolbarAction {

	private MapWidget mapWidget;
	private Bbox territoryBounds;

	public ZoomKtunaxaTerritoryModalAction(MapWidget mapWidget) {
		super("[ISOMORPHIC]/images/tool/zoom-ktu.png", "Zoom to Ktunaxa territory");
		this.mapWidget = mapWidget;
		territoryBounds = new Bbox(
				KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MIN_X,
				KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MIN_Y,
				KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MAX_X - KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MIN_X,
				KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MAX_Y - KtunaxaConstant.KTUNAXA_TERRITORY_MERCATOR_MIN_Y);
	}

	public void onClick(ClickEvent event) {
		mapWidget.getMapModel().getMapView().applyBounds(territoryBounds, MapView.ZoomOption.LEVEL_FIT);
	}

}
