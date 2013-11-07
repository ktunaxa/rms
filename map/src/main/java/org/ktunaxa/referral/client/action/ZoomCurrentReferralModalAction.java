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
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.MultiPoint;
import org.geomajas.gwt.client.spatial.geometry.Point;
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
				if (geometry instanceof MultiPoint || geometry instanceof Point) {
					// Zoom to 1/23000!
					mapWidget.getMapModel().getMapView().setCenterPosition(geometry.getBounds().getCenterPoint());
					mapWidget.getMapModel().getMapView()
							.setCurrentScale(0.8372905309874766, MapView.ZoomOption.LEVEL_CLOSEST);
				} else {
					// Now display feature on this page!
					mapWidget.getMapModel().getMapView()
							.applyBounds(geometry.getBounds(), MapView.ZoomOption.LEVEL_FIT);
				}
			}
		}
	}

}
