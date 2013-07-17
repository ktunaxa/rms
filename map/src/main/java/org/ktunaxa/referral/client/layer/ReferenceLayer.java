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
package org.ktunaxa.referral.client.layer;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.LayerChangedHandler;
import org.geomajas.gwt.client.map.event.LayerFilteredEvent;
import org.geomajas.gwt.client.map.event.LayerLabeledEvent;
import org.geomajas.gwt.client.map.event.LayerShownEvent;
import org.geomajas.gwt.client.map.event.MapViewChangedEvent;
import org.geomajas.gwt.client.map.event.MapViewChangedHandler;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * A reference layer is a wrapper of the Geomajas layer that takes care of handling the sub layers as if they were
 * individual Geomajas layers.
 * 
 * @author Jan De Moerloose
 */
public class ReferenceLayer {

	private VectorLayer layer;

	private MapModel mapModel;

	private List<ReferenceSubLayer> subLayers = new ArrayList<ReferenceSubLayer>();

	private List<ReferenceLayerTypeDto> layerTypes = new ArrayList<ReferenceLayerTypeDto>();

	private HandlerManager handlerManager;

	public ReferenceLayer(VectorLayer layer, List<ReferenceLayerDto> subLayerDtos,
			List<ReferenceLayerTypeDto> layerTypeDtos, boolean isBase) {
		this.layer = layer;
		for (ReferenceLayerTypeDto layerType : layerTypeDtos) {
			if (isBase == layerType.isBaseLayer()) {
				layerTypes.add(layerType);
			}
		}
		handlerManager = new HandlerManager(this);
		// forward layer changed events
		this.layer.addLayerChangedHandler(new LayerChangedForwarder());
		mapModel = layer.getMapModel();
		mapModel.getMapView().addMapViewChangedHandler(new LayerShowingHandler());
		for (ReferenceLayerDto referenceLayerDto : subLayerDtos) {
			if (isBase == referenceLayerDto.getType().isBaseLayer()) {
				subLayers.add(new ReferenceSubLayer(this, referenceLayerDto));
			}
		}
		updateScaleBounds(layer);
		updateShowing();
	}

	/**
	 * Add a handler that registers changes in layer status.
	 * 
	 * @param handler
	 *            The new handler to be added.
	 * @return handler registration
	 */
	public HandlerRegistration addLayerChangedHandler(LayerChangedHandler handler) {
		return handlerManager.addHandler(LayerChangedHandler.TYPE, handler);
	}

	public double getCurrentScale() {
		return mapModel.getMapView().getCurrentScale();
	}

	public double getPixelLength() {
		return mapModel.getMapInfo().getPixelLength();
	}

	public void updateShowing() {
		List<Long> layerIds = new ArrayList<Long>();
		for (ReferenceSubLayer subLayer : subLayers) {
			subLayer.updateShowing();
			if (subLayer.isShowing()) {
				layerIds.add(subLayer.getCode());
			}
		}
		StringBuilder builder = null;
		if (layerIds.size() > 0) {
			for (Long id : layerIds) {
				if (builder == null) {
					builder = new StringBuilder();
				} else {
					builder.append(" or ");
				}
				builder.append("layer_id = ");
				builder.append(id);
			}
		}
		String filter = (builder == null ? "EXCLUDE" : builder.toString());
		layer.setFilter(filter);
		if (!layer.isVisible()) {
			layer.setVisible(true);
		}
		// indicates show status has changed for one or more sub layers
		handlerManager.fireEvent(new LayerShownEvent(layer));
	}

	private void updateScaleBounds(VectorLayer layer) {
		double minScale = Double.MAX_VALUE;
		double maxScale = 0;
		for (ReferenceSubLayer subLayer : subLayers) {
			if (subLayer.getMinScale() < minScale) {
				minScale = subLayer.getMinScale();
			}
			if (subLayer.getMaxScale() > maxScale) {
				maxScale = subLayer.getMaxScale();
			}
		}
		layer.getLayerInfo().getMinimumScale().setPixelPerUnit(minScale);
		layer.getLayerInfo().getMaximumScale().setPixelPerUnit(maxScale);
	}

	/**
	 * Updates the show status when the map view changes.
	 * 
	 * @author Jan De Moerloose
	 */
	public class LayerShowingHandler implements MapViewChangedHandler {

		public void onMapViewChanged(MapViewChangedEvent event) {
			if (!event.isPanDragging()) {
				updateShowing();
			}
		}
	}

	public List<ReferenceSubLayer> getSubLayers() {
		return subLayers;
	}

	public List<ReferenceLayerTypeDto> getLayerTypes() {
		return layerTypes;
	}

	/**
	 * Forwards layer changed events to our listeners.
	 * 
	 * @author Jan De Moerloose
	 */
	public class LayerChangedForwarder implements LayerChangedHandler {

		public void onVisibleChange(LayerShownEvent event) {
			handlerManager.fireEvent(event);
		}

		public void onLabelChange(LayerLabeledEvent event) {
			handlerManager.fireEvent(event);
		}

		public void onFilterChange(LayerFilteredEvent event) {
			handlerManager.fireEvent(event);
		}

	}

}
