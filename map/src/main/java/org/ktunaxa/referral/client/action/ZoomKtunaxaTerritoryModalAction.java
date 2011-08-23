/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
