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
package org.geomajas.gwt.client.map.layer.configuration;

import org.geomajas.configuration.LayerInfo;
import org.geomajas.configuration.client.ClientRasterLayerInfo;
import org.geomajas.configuration.client.ScaleInfo;
import org.geomajas.geometry.Bbox;
import org.geomajas.gwt.client.map.layer.ClientWmsLayer;
import org.geomajas.layer.LayerType;

/**
 * Configuration object for adding a Client WMS layer to the map.
 *
 * @author Oliver May
 */
public class ClientWmsLayerInfo extends ClientRasterLayerInfo {

	private ClientWmsLayer wmsLayer;


	public ClientWmsLayerInfo(ClientWmsLayer wmsLayer) {
		this.wmsLayer = wmsLayer;
		LayerInfo layerInfo = new LayerInfo();
		layerInfo.setLayerType(LayerType.RASTER);
		setLayerInfo(layerInfo);
	}

	public ClientWmsLayer getWmsLayer() {
		return wmsLayer;
	}

	@Override
	public String getId() {
		return wmsLayer.getId();
	}

	@Override
	public String getLabel() {
		return wmsLayer.getTitle();
	}

	@Override
	public boolean isVisible() {
		return wmsLayer.isMarkedAsVisible();
	}

	@Override
	public void setVisible(boolean visible) {
		wmsLayer.setMarkedAsVisible(visible);
	}

	@Override
	public ScaleInfo getZoomToPointScale() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bbox getMaxExtent() {
		return wmsLayer.getCapabilities().getBoundingBox();
	}

	@Override
	public String getCrs() {
		//throw new UnsupportedOperationException();
		// still return scale value for the moment
		return super.getCrs();
	}

	@Override
	public void setId(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getServerLayerId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setServerLayerId(String serverLayerId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLabel(String label) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setZoomToPointScale(ScaleInfo zoomToPointScale) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMaxExtent(Bbox maxExtent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "ClientWmsLayerInfo{" +
				"id=" + getId() + ", " +
				"layerType=" + getLayerType() + ", " +
				"label=" + getLabel() + ", " +
				"minScale=" + getMinimumScale().getPixelPerUnit() + ", " +
				"maxScale=" + getMaximumScale().getPixelPerUnit() + ", " +
				"style=" + getStyle() + ", " +
				"ClientWmsLayer=" + wmsLayer.toString() +
				'}';
	}
}
