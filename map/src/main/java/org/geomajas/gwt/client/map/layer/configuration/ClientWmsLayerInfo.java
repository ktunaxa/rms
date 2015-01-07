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

	/**
	 * 
	 * @param wmsLayer
	 */
	public ClientWmsLayerInfo(ClientWmsLayer wmsLayer) {
		this.wmsLayer = wmsLayer;
		LayerInfo layerInfo = new LayerInfo();
		layerInfo.setLayerType(LayerType.RASTER);
		setLayerInfo(layerInfo);
	}

	/**
	 * 
	 * @return
	 */
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
	public ScaleInfo getMinimumScale() {
		return new ScaleInfo(1 / wmsLayer.getConfiguration().getMaximumResolution());
	}

	@Override
	public ScaleInfo getMaximumScale() {
		return new ScaleInfo(1 / wmsLayer.getConfiguration().getMinimumResolution());
	}

	@Override
	public String getCrs() {
		// throw new UnsupportedOperationException();
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
		return "ClientWmsLayerInfo{" + "id=" + getId() + ", " + "layerType=" + getLayerType() + ", " + "label="
				+ getLabel() + ", " + "minScale=" + getMinimumScale().getPixelPerUnit() + ", " + "maxScale="
				+ getMaximumScale().getPixelPerUnit() + ", " + "style=" + getStyle() + ", " + "ClientWmsLayer="
				+ wmsLayer.toString() + '}';
	}
}
