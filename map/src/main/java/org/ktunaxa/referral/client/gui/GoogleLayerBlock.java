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

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.event.LayerChangedHandler;
import org.geomajas.gwt.client.map.event.LayerLabeledEvent;
import org.geomajas.gwt.client.map.event.LayerShownEvent;
import org.geomajas.gwt.client.map.layer.AbstractLayer;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.layer.google.gwt.client.GoogleAddon;
import org.geomajas.layer.google.gwt.client.GoogleAddon.MapType;


/**
 * A Google layer block.
 * 
 * @author Jan De Moerloose
 * 
 */
public class GoogleLayerBlock extends LayerBlock {

	private MapType type;

	private GoogleAddon addon;

	public GoogleLayerBlock(Layer<?> layer, GoogleAddon addon, MapType type) {
		super(layer);
		this.type = type;
		this.addon = addon;
		layer.addLayerChangedHandler(new GoogleHandler());
	}

	/**
	 * Handles button state when a layer is hidden/shown.
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	private class GoogleHandler implements LayerChangedHandler {

		@Override
		public void onVisibleChange(LayerShownEvent event) {
			if (event.getLayer().equals(getLayer())) {
				if (((AbstractLayer<?>) getLayer()).isVisible()) {
					// pass to add-on, the actual layer is empty !
					addon.setMapType(type);
					addon.setVisible(true);
				} else {
					// pass to add-on, the actual layer is empty !
					if (addon.getMapType() == type) {
						addon.setVisible(false);
					}
				}
			}
		}

		@Override
		public void onLabelChange(LayerLabeledEvent event) {

		}

	}

	@Override
	protected void updateLayerEnabled(boolean enabled) {
		super.updateLayerEnabled(enabled);
	}

}
