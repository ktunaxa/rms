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

package org.geomajas.gwt2.plugin.wms.client.capabilities.v1_3_0;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.geomajas.geometry.Bbox;
import org.geomajas.gwt2.client.service.AbstractXmlNodeWrapper;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsGetCapabilitiesInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerMetadataUrlInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerStyleInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsRequestInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.v1_1_1.WmsRequestInfo111;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

/**
 * Implementation of the {@link org.geomajas.gwt2.plugin.wms.client.capabilities.WmsGetCapabilitiesInfo}
 * for WMS version 1.3.0.
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
				if(parent != null && layersByNode.containsKey(parent)){
					layersByNode.get(parent).getLayers().add(wmsLayerInfo);
				} else {
					rootLayer = wmsLayerInfo;
				}
			}
			if(rootLayer == null) {
				throw new IllegalArgumentException("Capabilities has no root layer !");
			}
		}
	}
}
