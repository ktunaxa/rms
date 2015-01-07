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

package org.geomajas.gwt2.plugin.wms.client.capabilities.v1_3_0;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.geomajas.gwt2.client.service.AbstractXmlNodeWrapper;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsGetCapabilitiesInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsRequestInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.v1_1_1.WmsRequestInfo111;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

/**
 * Implementation of the {@link org.geomajas.gwt2.plugin.wms.client.capabilities.WmsGetCapabilitiesInfo} for WMS version
 * 1.3.0.
 *
 * @author Pieter De Graef
 */
public class WmsGetCapabilitiesInfo130 extends AbstractXmlNodeWrapper implements WmsGetCapabilitiesInfo {

	private static final long serialVersionUID = 100L;

	private List<WmsRequestInfo> requests;

	private List<WmsLayerInfo> layers;

	private WmsLayerInfo rootLayer;

	public WmsGetCapabilitiesInfo130(Node node) {
		super(node);
	}

	public List<WmsRequestInfo> getRequests() {
		if (requests == null) {
			parse(getNode());
		}
		return requests;
	}

	public List<WmsLayerInfo> getLayers() {
		if (layers == null) {
			parse(getNode());
		}
		return layers;
	}

	@Override
	public WmsLayerInfo getRootLayer() {
		if (rootLayer == null) {
			parse(getNode());
		}
		return rootLayer;
	}

	protected void parse(Node node) {
		if (node instanceof Element) {
			Element element = (Element) node;

			requests = new ArrayList<WmsRequestInfo>(5);
			NodeList requestParentNode = element.getElementsByTagName("Request");
			NodeList requestNodes = requestParentNode.item(0).getChildNodes();
			for (int i = 0; i < requestNodes.getLength(); i++) {
				Node requestNode = requestNodes.item(i);
				WmsRequestInfo111 requestInfo = new WmsRequestInfo130(requestNode);
				if (requestInfo.getRequestType() != null) {
					requests.add(requestInfo);
				}
			}

			NodeList layerNodes = element.getElementsByTagName("Layer");
			layers = new ArrayList<WmsLayerInfo>();
			Map<Node, WmsLayerInfo130> layersByNode = new LinkedHashMap<Node, WmsLayerInfo130>();
			for (int i = 0; i < layerNodes.getLength(); i++) {
				Node layerNode = layerNodes.item(i);
				WmsLayerInfo130 layer = new WmsLayerInfo130(layerNode);
				layersByNode.put(layerNode, layer);
				if (layer.getName() != null) {
					layers.add(layer);
				}
			}
			// assuming each layer only has one parent
			for (WmsLayerInfo130 wmsLayerInfo : layersByNode.values()) {
				Node parent = wmsLayerInfo.getNode().getParentNode();
				if (parent != null && layersByNode.containsKey(parent)) {
					layersByNode.get(parent).getLayers().add(wmsLayerInfo);
				} else {
					rootLayer = wmsLayerInfo;
				}
			}
			if (rootLayer == null) {
				throw new IllegalArgumentException("Capabilities has no root layer !");
			}
		}
	}
}
