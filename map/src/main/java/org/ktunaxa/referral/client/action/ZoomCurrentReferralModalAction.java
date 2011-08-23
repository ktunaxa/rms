/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.action;

import com.smartgwt.client.widgets.events.ClickEvent;
import org.geomajas.gwt.client.action.ToolbarAction;
import org.geomajas.gwt.client.map.MapView;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.client.gui.MapLayout;

/**
 * Zoom to current referral modal action.
 *
 * @author Joachim Van der Auwera
 */
public class ZoomCurrentReferralModalAction extends ToolbarAction {

	private MapWidget mapWidget;

	public ZoomCurrentReferralModalAction(MapWidget mapWidget) {
		super("[ISOMORPHIC]/images/tool/zoom-ref.png", "Zoom to current referral");
		this.mapWidget = mapWidget;
	}

	public void onClick(ClickEvent event) {
		Feature referral = MapLayout.getInstance().getCurrentReferral();
		if (null != referral) {
			org.geomajas.geometry.Geometry referralGeometry = referral.getGeometry();
			if (null != referralGeometry) {
				Geometry geometry = GeometryConverter.toGwt(referralGeometry);
				mapWidget.getMapModel().getMapView().applyBounds(geometry.getBounds(), MapView.ZoomOption.LEVEL_FIT);
			}
		}
	}

}
