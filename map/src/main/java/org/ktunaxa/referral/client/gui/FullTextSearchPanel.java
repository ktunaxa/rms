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

import org.geomajas.gwt.client.map.layer.AbstractLayer;
import org.geomajas.gwt.client.map.layer.InternalClientWmsLayer;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo;
import org.geomajas.widget.searchandfilter.client.widget.configuredsearch.ConfiguredSearchPanel;
import org.geomajas.widget.searchandfilter.search.dto.AttributeCriterion;
import org.geomajas.widget.searchandfilter.search.dto.Criterion;
import org.geomajas.widget.searchandfilter.search.dto.OrCriterion;

/**
 * 
 * @author Jan De Moerloose
 *
 */
public class FullTextSearchPanel extends ConfiguredSearchPanel {

	public FullTextSearchPanel(MapWidget mapWidget) {
		super(mapWidget);
	}

	@Override
	public Criterion getFeatureSearchCriterion() {
		Criterion and = super.getFeatureSearchCriterion();
		// hack in the visible layers as an or-criterion
		Criterion or = new OrCriterion();
		for (Layer<?> layer : mapWidget.getMapModel().getLayers()) {
			if (((AbstractLayer) layer).isVisible() && layer instanceof InternalClientWmsLayer) {
				InternalClientWmsLayer wmsLayer = (InternalClientWmsLayer) layer;
				if (wmsLayer.getWmsLayer().getCapabilities().getLayers().isEmpty()) {
					addLayer(or, layer.getId());
				} else {
					for (WmsLayerInfo info : wmsLayer.getWmsLayer().getCapabilities().getLayers()) {
						addLayer(or, info.getName());
					}
				}
			}
		}
		if (!or.getCriteria().isEmpty()) {
			and.getCriteria().add(or);
		}
		return and;
	}

	private void addLayer(Criterion or, String layerName) {
		if (layerName.contains(":")) {
			layerName = layerName.substring(layerName.indexOf(":") + 1);
		}
		or.getCriteria().add(new AttributeCriterion("layerFtsLayers", "layerName", "=", layerName));
	}

}
