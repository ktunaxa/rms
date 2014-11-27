/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2014 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.geomajas.gwt.client.map.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geomajas.geometry.Coordinate;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.MapView;
import org.geomajas.gwt.client.spatial.Bbox;
import org.geomajas.gwt.client.util.Log;
import org.geomajas.gwt2.client.animation.NavigationAnimation;
import org.geomajas.gwt2.client.map.MapConfigurationImpl;
import org.geomajas.gwt2.client.map.View;
import org.geomajas.gwt2.client.map.ViewPort;
import org.geomajas.gwt2.client.map.ViewPortTransformationService;
import org.geomajas.gwt2.client.map.ZoomOption;
import org.geomajas.gwt2.client.map.layer.tile.TileConfiguration;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo;
import org.geomajas.gwt2.plugin.wms.client.layer.WmsLayerConfiguration;
import org.geomajas.gwt2.plugin.wms.client.layer.WmsLayerImpl;

/**
 * SmartGWT implementation of the client WMS layer. This is an extension of the GWT2 wms layer adding support for the
 * SmartGWT map.
 *
 * @author Oliver May
 */
public class ClientWmsLayer extends WmsLayerImpl {

	private static final double METER_PER_INCH = 0.0254;


	/**
	 * Create a new Client WMS layer.
	 *
	 * @param title the title
	 * @param wmsConfig the wms configuration
	 * @param tileConfig the tile configuration
	 */
	public ClientWmsLayer(String title, String crs, WmsLayerConfiguration wmsConfig, TileConfiguration tileConfig) {
		this(title, crs, wmsConfig, tileConfig, null);

	}

	/**
	 * Create a new Client WMS layer.
	 *
	 * @param title the title
	 * @param wmsConfig the wms configuration
	 * @param tileConfig the tile configuration
	 * @param layerCapabilities the layer capabilities or null
	 */
	public ClientWmsLayer(String title, String crs, WmsLayerConfiguration wmsConfig, TileConfiguration tileConfig,
			WmsLayerInfo layerCapabilities) {
		// we may need a SmartGwt MapConfiguration here !
		super(title, crs, wmsConfig, tileConfig, layerCapabilities);
	}

	/**
	 * Set the map model on this layer.
	 *
	 * @param mapModel the mapModel.
	 */
	public void setMapModel(MapModel mapModel) {
		setViewPort(new SmartGwtViewport(mapModel));
	}

	@Override
	public String toString() {
		return "ClientWmsLayer{" +
				"id=" + getId() + ", " +
				"title=" + getTitle() + ", " +
				"capabilities=" + getCapabilities() + ", " +
				"config=" + getConfiguration() + ", " +
				"legendImageUrl=" + getLegendImageUrl() + ", " +
				"opacity=" + getOpacity() +
				'}';
	}

	/**
	 * SmartGwt implementation of the GWT2 viewport. This is intended for internal use in the client WMS layer, as it
	 * does not implement all ViewPort methods. It can however be extended to fully support everything.
	 */
	private class SmartGwtViewport implements ViewPort {

		private final MapModel mapModel;

		private final List<Double> fixedResolutions = new ArrayList<Double>();

		public SmartGwtViewport(MapModel mapModel) {
			this.mapModel = mapModel;

			//Calculate fixed scales based on the resolutions.
			//FIXME: what to do when no fixed resolutions exist.
			if (mapModel.getMapView().getResolutions() == null || mapModel.getMapView().getResolutions().size() < 1) {
				RuntimeException e = new RuntimeException("Error while adding Client WMS layer, " +
						"the map should define a list of resolutions.");
				Log.logError("Error while adding Client WMS layer.", e);
				throw e;
			}
			for (Double resolution : mapModel.getMapView().getResolutions()) {
				fixedResolutions.add(resolution);
			}
			Collections.sort(fixedResolutions);
		}

		@Override
		public org.geomajas.geometry.Bbox getMaximumBounds() {
			return mapModel.getMapView().getMaxBounds().toDtoBbox();
		}

		@Override
		public double getMaximumResolution() {
			if (fixedResolutions.size() == 0) {
				return Double.MAX_VALUE;
			}
			return fixedResolutions.get(fixedResolutions.size() - 1);
		}

		@Override
		public double getMinimumResolution() {
			if (fixedResolutions.size() == 0) {
				return 0;
			}
			return fixedResolutions.get(0);
		}

		@Override
		public int getResolutionCount() {
			return fixedResolutions.size();
		}

		@Override
		public double getResolution(int index) {
			if (index < 0) {
				throw new IllegalArgumentException("Resolution index cannot be found.");
			}
			if (index >= fixedResolutions.size()) {
				throw new IllegalArgumentException("Resolution index cannot be found.");
			}
			return fixedResolutions.get(index);
		}

		@Override
		public int getResolutionIndex(double resolution) {
			double maximumResolution = getMaximumResolution();
			if (resolution >= maximumResolution) {
				return fixedResolutions.size() - 1;
			}
			double minimumResolution = getMinimumResolution();
			if (resolution <= minimumResolution) {
				return 0;
			}

			for (int i = 0; i < fixedResolutions.size(); i++) {
				double lower = fixedResolutions.get(i);
				double upper = fixedResolutions.get(i + 1);
				if (resolution <= upper && resolution > lower) {
					if (Math.abs(upper - resolution) >= Math.abs(lower - resolution)) {
						return i;
					} else {
						return i + 1;
					}
				}
			}
			return 0;
		}

		@Override
		public int getMapWidth() {
			return mapModel.getMapView().getWidth();
		}

		@Override
		public int getMapHeight() {
			return mapModel.getMapView().getWidth();
		}

		@Override
		public String getCrs() {
			return mapModel.getCrs();
		}

		@Override
		public Coordinate getPosition() {
			return mapModel.getMapView().getPanOrigin();
		}

		@Override
		public double getResolution() {
			return 1 / mapModel.getMapView().getCurrentScale();
		}

		@Override
		public View getView() {
			return new View(getPosition(), getResolution());
		}

		@Override
		public org.geomajas.geometry.Bbox getBounds() {
			return mapModel.getMapView().getBounds().toDtoBbox();
		}

		@Override
		public void registerAnimation(NavigationAnimation navigationAnimation) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void applyPosition(Coordinate coordinate) {
			mapModel.getMapView().setCenterPosition(coordinate);
		}

		@Override
		public void applyResolution(double r) {
			applyResolution(r, ZoomOption.FREE);
		}

		private MapView.ZoomOption convertZoomOption(ZoomOption zoomOption) {
			switch (zoomOption) {
				case FREE:
					return MapView.ZoomOption.EXACT;
				case LEVEL_FIT:
					return MapView.ZoomOption.LEVEL_FIT;
				case LEVEL_CLOSEST:
				default:
					return MapView.ZoomOption.LEVEL_CLOSEST;
			}
		}

		@Override
		public void applyResolution(double r, ZoomOption zoomOption) {
			mapModel.getMapView().setCurrentScale(1 / r, convertZoomOption(zoomOption));
		}

		@Override
		public void applyBounds(org.geomajas.geometry.Bbox bbox) {
			applyBounds(bbox, ZoomOption.FREE);
		}

		@Override
		public void applyBounds(org.geomajas.geometry.Bbox bbox, ZoomOption zoomOption) {
			mapModel.getMapView().applyBounds(new Bbox(bbox), convertZoomOption(zoomOption));
		}

		@Override
		public void applyView(View view) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void applyView(View view, ZoomOption zoomOption) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ViewPortTransformationService getTransformationService() {
			throw new UnsupportedOperationException();
		}

		@Override
		public double toResolution(double scaleDenominator) {
			double pixelsPerUnit =  METER_PER_INCH / MapConfigurationImpl.DEFAULT_DPI;
			return pixelsPerUnit * scaleDenominator;
		}

		@Override
		public org.geomajas.geometry.Bbox asBounds(View view) {
			throw new UnsupportedOperationException();
		}

		@Override
		public View asView(org.geomajas.geometry.Bbox bbox, ZoomOption zoomOption) {
			throw new UnsupportedOperationException();
		}
	}

}
