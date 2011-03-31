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
package org.ktunaxa.referral.client.layer;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.MapViewChangedEvent;
import org.geomajas.gwt.client.map.event.MapViewChangedHandler;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;

/**
 * A reference layer is a wrapper of the Geomajas layer that takes care of handling the sublayers as if they were
 * individual Geomajas layers.
 * 
 * @author Jan De Moerloose
 * 
 */
public class ReferenceLayer {

	private VectorLayer layer;

	private MapModel mapModel;

	private List<ReferenceSubLayer> subLayers = new ArrayList<ReferenceSubLayer>();

	private List<ReferenceLayerTypeDto> layerTypes;

	public ReferenceLayer(VectorLayer layer, List<ReferenceLayerDto> subLayerDtos,
			List<ReferenceLayerTypeDto> layerTypes) {
		this.layer = layer;
		this.layerTypes = layerTypes;
		mapModel = layer.getMapModel();
		mapModel.getMapView().addMapViewChangedHandler(new LayerShowingHandler());
		for (ReferenceLayerDto referenceLayerDto : subLayerDtos) {
			subLayers.add(new ReferenceSubLayer(this, referenceLayerDto));
		}
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
				layerIds.add(subLayer.getId());
			}
		}
		StringBuilder builder = null;
		if (layerIds.size() > 0) {
			builder = new StringBuilder("(");
			for (Long id : layerIds) {
				builder.append(id + ",");
			}
			builder.replace(builder.length() - 1, builder.length(), ")");
		}
		String oldFilter = layer.getFilter();
		String newFilter = (builder == null ? "" : "layer.id in " + builder.toString());
		if (!newFilter.equals(oldFilter)) {
			layer.setFilter(newFilter);
		}
		// forces refresh, should be done by setFilter
		layer.setVisible(true);
	}

	/**
	 * Updates the show status when the map view changes.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class LayerShowingHandler implements MapViewChangedHandler {

		public void onMapViewChanged(MapViewChangedEvent event) {
			updateShowing();
		}
	}

	public List<ReferenceSubLayer> getSubLayers() {
		return subLayers;
	}

	public List<ReferenceLayerTypeDto> getLayerTypes() {
		return layerTypes;
	}

}
