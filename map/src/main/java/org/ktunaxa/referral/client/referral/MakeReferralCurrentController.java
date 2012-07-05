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

import java.util.List;
import java.util.Map;

import org.geomajas.command.dto.SearchByLocationRequest;
import org.geomajas.command.dto.SearchByLocationResponse;
import org.geomajas.geometry.Coordinate;
import org.geomajas.geometry.service.GeometryService;
import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.controller.AbstractGraphicsController;
import org.geomajas.gwt.client.map.RenderSpace;
import org.geomajas.gwt.client.spatial.Mathlib;
import org.geomajas.gwt.client.spatial.WorldViewTransformer;
import org.geomajas.gwt.client.spatial.geometry.Point;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.event.dom.client.MouseUpEvent;

/**
 * Makes a referral current by clicking on it.
 * 
 * @author Jan De Moerloose
 */
public class MakeReferralCurrentController extends AbstractGraphicsController {

	/** Number of pixels that describes the tolerance allowed when trying to select features. */
	private int pixelTolerance;

	public MakeReferralCurrentController(MapWidget mapWidget, int pixelTolerance) {
		super(mapWidget);
		this.pixelTolerance = pixelTolerance;
	}

	/**
	 * On mouse up, execute the search by location, and make the first current.
	 */
	public void onMouseUp(MouseUpEvent event) {
		final Coordinate worldPosition = getLocation(event, RenderSpace.WORLD);
		Point point = mapWidget.getMapModel().getGeometryFactory().createPoint(worldPosition);

		SearchByLocationRequest request = new SearchByLocationRequest();
		request.setLocation(GeometryConverter.toDto(point));
		request.setCrs(mapWidget.getMapModel().getCrs());
		request.setQueryType(SearchByLocationRequest.QUERY_INTERSECTS);
		request.setSearchType(SearchByLocationRequest.SEARCH_FIRST_LAYER);
		request.setBuffer(calculateBufferFromPixelTolerance());
		request.setFeatureIncludes(GeomajasConstant.FEATURE_INCLUDE_ALL);
		request.setLayerIds(new String[] { KtunaxaConstant.LAYER_REFERRAL_SERVER_ID });

		GwtCommand commandRequest = new GwtCommand(SearchByLocationRequest.COMMAND);
		commandRequest.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(commandRequest,
				new AbstractCommandCallback<SearchByLocationResponse>() {

			public void execute(SearchByLocationResponse response) {
				Map<String, List<org.geomajas.layer.feature.Feature>> featureMap = response.getFeatureMap();
				List<org.geomajas.layer.feature.Feature> orgFeatures = featureMap
						.get(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID);
				if (null != orgFeatures && orgFeatures.size() > 0) {
					// take the nearest
					double minDistance = Double.MAX_VALUE;
					org.geomajas.layer.feature.Feature referral = null;
					for (org.geomajas.layer.feature.Feature feature : orgFeatures) {
						double distance = GeometryService.getDistance(feature.getGeometry(), worldPosition);
						if (distance < minDistance) {
							referral = feature;
							minDistance = distance;
						}
					}
					if (referral != null) {
						MapLayout.getInstance().setReferralAndTask(orgFeatures.get(0), null);
					}
				}
			}
		});
	}

	private double calculateBufferFromPixelTolerance() {
		WorldViewTransformer transformer = mapWidget.getMapModel().getMapView().getWorldViewTransformer();
		Coordinate c1 = transformer.viewToWorld(new Coordinate(0, 0));
		Coordinate c2 = transformer.viewToWorld(new Coordinate(pixelTolerance, 0));
		return Mathlib.distance(c1, c2);
	}
}