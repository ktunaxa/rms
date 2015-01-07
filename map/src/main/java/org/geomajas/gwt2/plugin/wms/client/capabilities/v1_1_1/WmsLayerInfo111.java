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

package org.geomajas.gwt2.plugin.wms.client.capabilities.v1_1_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geomajas.geometry.Bbox;
import org.geomajas.gwt2.client.service.AbstractXmlNodeWrapper;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerMetadataUrlInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerStyleInfo;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

/**
 * Implementation of the {@link org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo} for WMS version 1.1.1.
 *
 * @author Pieter De Graef
 */
public class WmsLayerInfo111 extends AbstractXmlNodeWrapper implements WmsLayerInfo {

	private static final long serialVersionUID = 100L;

	private String name;

	private String title;

	private String abstractt;

	private final List<String> keywords = new ArrayList<String>();

	private final List<String> crs = new ArrayList<String>();

	private Bbox latlonBoundingBox;

	private final Map<String, Bbox> boundingBoxes = new HashMap<String, Bbox>(); // key = boundingBoxCrs

	private List<WmsLayerMetadataUrlInfo> metadataUrls = new ArrayList<WmsLayerMetadataUrlInfo>();

	private List<WmsLayerStyleInfo> styleInfo = new ArrayList<WmsLayerStyleInfo>();

	private List<WmsLayerInfo> layers = new ArrayList<WmsLayerInfo>();

	private double minScaleDenominator = -1;

	private double maxScaleDenominator = -1;

	private boolean queryable;
	
	private transient boolean parsed;

	public WmsLayerInfo111(Node node) {
		super(node);
	}

	public String getName() {
		if (!parsed) {
			parse(getNode());
		}
		return name;
	}

	public String getTitle() {
		if (!parsed) {
			parse(getNode());
		}
		return title;
	}

	public String getAbstract() {
		if (!parsed) {
			parse(getNode());
		}
		return abstractt;
	}

	public List<String> getKeywords() {
		if (!parsed) {
			parse(getNode());
		}
		return keywords;
	}

	public List<String> getCrs() {
		if (!parsed) {
			parse(getNode());
		}
		return crs;
	}

	public Bbox getLatlonBoundingBox() {
		if (!parsed) {
			parse(getNode());
		}
		return latlonBoundingBox;
	}

	@Override
	public String getBoundingBoxCrs() {
		if (!parsed) {
			parse(getNode());
		}
		if (null != this.crs && !this.crs.isEmpty() && null != boundingBoxes.get(this.crs.get(0))) {
			return this.crs.get(0);
		}
		return null;
	}

	@Override
	public Bbox getBoundingBox(String boundingBoxCrs) {
		if (!parsed) {
			parse(getNode());
		}
		return boundingBoxes.get(boundingBoxCrs);
	}

	@Override
	public Bbox getBoundingBox() {
		if (!parsed) {
			parse(getNode());
		}
		if (null != this.crs && !this.crs.isEmpty() && null != boundingBoxes.get(this.crs.get(0))) {
			return boundingBoxes.get(this.crs.get(0));
		}
		return null;
	}

	public List<WmsLayerMetadataUrlInfo> getMetadataUrls() {
		if (!parsed) {
			parse(getNode());
		}
		return metadataUrls;
	}

	public List<WmsLayerStyleInfo> getStyleInfo() {
		if (!parsed) {
			parse(getNode());
		}
		return styleInfo;
	}
	
	public List<WmsLayerInfo> getLayers() {
		if (!parsed) {
			parse(getNode());
		}
		return layers;
	}

	public double getMinScaleDenominator() {
		if (!parsed) {
			parse(getNode());
		}
		return minScaleDenominator;
	}

	public double getMaxScaleDenominator() {
		if (!parsed) {
			parse(getNode());
		}
		return maxScaleDenominator;
	}

	public boolean isQueryable() {
		if (!parsed) {
			parse(getNode());
		}
		return queryable;
	}

	// ------------------------------------------------------------------------
	// AbstractNodeInfo implementation:
	// ------------------------------------------------------------------------

	protected void parse(Node node) {
		queryable = isQueryable(node);
		styleInfo.clear();

		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			String nodeName = child.getNodeName();
			if ("Name".equalsIgnoreCase(nodeName)) {
				name = getValueRecursive(child);
			} else if ("Title".equalsIgnoreCase(nodeName)) {
				title = getValueRecursive(child);
			} else if ("Abstract".equalsIgnoreCase(nodeName)) {
				abstractt = getValueRecursive(child);
			} else if ("KeywordList".equalsIgnoreCase(nodeName)) {
				addKeyWords(child);
			} else if ("SRS".equalsIgnoreCase(nodeName)) {
				crs.add(getValueRecursive(child));
			} else if ("BoundingBox".equalsIgnoreCase(nodeName)) {
				addBoundingBox(child);
			} else if ("LatLonBoundingBox".equalsIgnoreCase(nodeName)) {
				addLatLonBoundingBox(child);
			} else if ("MetadataURL".equalsIgnoreCase(nodeName)) {
				metadataUrls.add(new WmsLayerMetadataUrlInfo111(child));
			} else if ("Style".equalsIgnoreCase(nodeName)) {
				styleInfo.add(new WmsLayerStyleInfo111(child));
			} else if ("ScaleHint".equalsIgnoreCase(nodeName)) {
				if (hasAttribute(child, "min")) {
					minScaleDenominator = getAttributeAsDouble(child, "min");
				}
				if (hasAttribute(child, "max")) {
					maxScaleDenominator = getAttributeAsDouble(child, "max");
				}
			}
		}
		parsed = true;
	}

	private boolean isQueryable(Node layerNode) {
		NamedNodeMap attributes = layerNode.getAttributes();
		Node q = attributes.getNamedItem("queryable");
		return q != null && "1".equals(q.getNodeValue());
	}

	private void addKeyWords(Node keywordListNode) {
		Element keywordListEl = (Element) keywordListNode;
		NodeList keywordList = keywordListEl.getElementsByTagName("Keyword");
		for (int i = 0; i < keywordList.getLength(); i++) {
			Node keywordNode = keywordList.item(i);
			keywords.add(getValueRecursive(keywordNode));
		}
	}

	private void addBoundingBox(Node bboxNode) {
		NamedNodeMap attributes = bboxNode.getAttributes();
		Node crs = attributes.getNamedItem("SRS");
		String boundingBoxCrs = getValueRecursive(crs);

		Node minx = attributes.getNamedItem("minx");
		Node miny = attributes.getNamedItem("miny");
		Node maxx = attributes.getNamedItem("maxx");
		Node maxy = attributes.getNamedItem("maxy");

		double x = getValueRecursiveAsDouble(minx);
		double y = getValueRecursiveAsDouble(miny);
		double width = getValueRecursiveAsDouble(maxx) - x;
		double height = getValueRecursiveAsDouble(maxy) - y;
		Bbox boundingBox = new Bbox(x, y, width, height);
		this.boundingBoxes.put(boundingBoxCrs, boundingBox);
	}

	private void addLatLonBoundingBox(Node bboxNode) {
		// TODO use super.getBoundingBox();
		NamedNodeMap attributes = bboxNode.getAttributes();

		Node minx = attributes.getNamedItem("minx");
		Node miny = attributes.getNamedItem("miny");
		Node maxx = attributes.getNamedItem("maxx");
		Node maxy = attributes.getNamedItem("maxy");

		double x = getValueRecursiveAsDouble(minx);
		double y = getValueRecursiveAsDouble(miny);
		double width = getValueRecursiveAsDouble(maxx) - x;
		double height = getValueRecursiveAsDouble(maxy) - y;
		latlonBoundingBox = new Bbox(x, y, width, height);
	}
}
