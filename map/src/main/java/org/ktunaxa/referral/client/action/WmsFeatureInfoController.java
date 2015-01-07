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

import org.geomajas.geometry.Coordinate;
import org.geomajas.gwt.client.controller.AbstractGraphicsController;
import org.geomajas.gwt.client.map.layer.InternalClientWmsLayer;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt2.plugin.wms.client.WmsClient;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo;
import org.geomajas.gwt2.plugin.wms.client.layer.WmsLayer;
import org.geomajas.gwt2.plugin.wms.client.layer.WmsLayerConfiguration;
import org.geomajas.gwt2.plugin.wms.client.layer.WmsLayerImpl;
import org.geomajas.gwt2.plugin.wms.client.service.WmsService;
import org.ktunaxa.referral.client.gui.MapLayout;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

/**
 * 
 * @author Jan De Moerloose
 *
 */
public class WmsFeatureInfoController extends AbstractGraphicsController {

	/** Number of pixels that describes the tolerance allowed when trying to select features. */
	private int pixelTolerance;

	public WmsFeatureInfoController(MapWidget mapWidget, int pixelTolerance) {
		super(mapWidget);
		this.pixelTolerance = pixelTolerance;
	}

	/**
	 * On mouse up, execute the search by location, and display a
	 * {@link org.geomajas.gwt.client.widget.FeatureAttributeWindow} if a result is found.
	 */
	public void onMouseUp(MouseUpEvent event) {
		Coordinate worldPosition = getWorldPosition(event);
		final Window window = new ClosingWindow();
		window.setWidth(MapLayout.getInstance().getLayerPanel().getWidth());
		window.setHeight(MapLayout.getInstance().getLayerPanel().getHeight());
		window.setCanDragReposition(true);
		window.setCanDragResize(true);
		window.setKeepInParentRect(true);
		window.addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				window.markForDestroy();
			}
		});
		StringBuilder sb = null;
		WmsLayerConfiguration layerConfig = MapLayout.getInstance().getRootLayerConfiguration();
		for (Layer<?> layer : mapWidget.getMapModel().getLayers()) {
			if (layer.isShowing() && layer instanceof InternalClientWmsLayer) {
				InternalClientWmsLayer l = (InternalClientWmsLayer) layer;
				if (!l.getWmsLayer().getCapabilities().getLayers().isEmpty()) {
					for (WmsLayerInfo info : l.getWmsLayer().getCapabilities().getLayers()) {
						if (info.isQueryable()) {
							if (sb == null) {
								sb = new StringBuilder();
							} else {
								sb.append(",");
							}
							sb.append(info.getName());
						}
					}
				} else if (l.getWmsLayer().getCapabilities().isQueryable()) {
					if (sb == null) {
						sb = new StringBuilder();
					} else {
						sb.append(",");
					}
					sb.append(layer.getId());
				}
			}
		}
		if (sb != null) {
			layerConfig.setLayers(sb.toString());
			WmsLayer dummy = new WmsLayerImpl("all", layerConfig.getCrs(), layerConfig, null, null);
			final String url = WmsClient
					.getInstance()
					.getWmsService()
					.getFeatureInfoUrl(dummy, worldPosition,
							mapWidget.getMapModel().getMapView().getBounds().toDtoBbox(),
							1. / mapWidget.getMapModel().getMapView().getCurrentScale(),
							WmsService.GetFeatureInfoFormat.HTML.toString(), 10);
			window.setTitle("Features found for " + sb.toString() + ":");
			HTMLPane htmlPane = new HTMLPane();
			htmlPane.setContentsURL(url);
			htmlPane.setContentsType(ContentsType.PAGE);
			window.addItem(htmlPane);
			if (!window.isDrawn()) {
				MapLayout.getInstance().addChild(window);
			}
		} else {
			SC.say("No queryable layers found !");
		}

	}

	/**
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	class ClosingWindow extends Window {

		public ClosingWindow() {
			super();
			addCloseClickHandler(new CloseClickHandler() {

				@Override
				public void onCloseClick(CloseClickEvent event) {
					close();
				}
			});
		}

		@Override
		public void close() {
			clear();
			hide();
			markForDestroy();
		}

	}

}
