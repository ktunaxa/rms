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

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.map.event.LayerChangedHandler;
import org.geomajas.gwt.client.map.event.LayerFilteredEvent;
import org.geomajas.gwt.client.map.event.LayerFilteredHandler;
import org.geomajas.gwt.client.map.event.LayerLabeledEvent;
import org.geomajas.gwt.client.map.event.LayerShownEvent;
import org.geomajas.gwt.client.map.layer.AbstractLayer;
import org.geomajas.gwt.client.map.layer.ClientWmsLayer;
import org.geomajas.gwt.client.map.layer.InternalClientWmsLayer;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.map.layer.configuration.ClientWmsLayerInfo;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt2.client.map.layer.LegendUrlSupported;
import org.geomajas.widget.layer.client.widget.CombinedLayertree;
import org.geomajas.widget.layer.configuration.client.ClientAbstractNodeInfo;
import org.geomajas.widget.layer.configuration.client.ClientLayerNodeInfo;
import org.geomajas.widget.layer.configuration.client.ClientLayerTreeInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;

/**
 * Refreshable tree, reacts to layer additions (+supports WMS).
 * 
 * @author Jan De Moerloose
 *
 */
public class RefreshableLayerTree extends CombinedLayertree {

	private final List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	/**
	 * 
	 * @param mapWidget
	 */
	public RefreshableLayerTree(MapWidget mapWidget) {
		super(mapWidget);
	}

	/**
	 * 
	 */
	public void onLeafClick(LeafClickEvent event) {
		if (event.getLeaf() instanceof WmsLegendNode) {
			WmsLegendNode n = (WmsLegendNode) event.getLeaf();
			n.getLayer().setVisible(!n.getLayer().isVisible());
			n.updateIcon();
			mapModel.selectLayer(n.getLayer());
		} else {
			super.onLeafClick(event);
		}
	}

	/**
	 * 
	 */
	public void refresh() {
		// make a new tree, quick and dirty way !
		tree = new MyTree();
		final TreeNode nodeRoot = new TreeNode("ROOT");
		tree.setRoot(nodeRoot); // invisible ROOT node (ROOT node is required)

		ClientLayerTreeInfo layerTreeInfo = (ClientLayerTreeInfo) mapModel.getMapInfo().getWidgetInfo(
				ClientLayerTreeInfo.IDENTIFIER);
		tree.setRoot(nodeRoot); // invisible ROOT node (ROOT node is required)
		if (layerTreeInfo != null) {
			for (ClientAbstractNodeInfo node : layerTreeInfo.getTreeNode().getTreeNodes()) {
				processNode(node, nodeRoot, false);
			}
		}

		treeGrid.setData(tree);
		tree.openFolder(nodeRoot);
		syncNodeState(false);
		// must handle all layers for visibility state !
		onUnload(); // drops previous handlers
		for (Layer<?> layer : mapModel.getLayers()) {
			registrations.add(layer.addLayerChangedHandler(new LayerChangedHandler() {

				public void onLabelChange(LayerLabeledEvent event) {
					GWT.log("Legend: onLabelChange() - " + event.getLayer().getLabel());
					// find the node & update the icon
					for (TreeNode node : tree.getAllNodes()) {
						if (node.getName().equals(event.getLayer().getLabel()) && node instanceof LayerTreeTreeNode) {
							((LayerTreeTreeNode) node).updateIcon();
						}
					}
				}

				public void onVisibleChange(LayerShownEvent event) {
					GWT.log("Legend: onVisibleChange() - " + event.getLayer().getLabel());
					// find the node & update the icon
					for (TreeNode node : tree.getAllNodes()) {
						if (node instanceof LayerTreeTreeNode) {
							LayerTreeTreeNode layerTreeTreeNode = (LayerTreeTreeNode) node;
							if (layerTreeTreeNode.getLayer().equals(event.getLayer())) {
								((LayerTreeTreeNode) node).updateIcon();
							}
						}
					}
					treeGrid.markForRedraw();
				}
			}));

			if (layer instanceof VectorLayer) {
				VectorLayer vl = (VectorLayer) layer;
				registrations.add(vl.addLayerFilteredHandler(new LayerFilteredHandler() {

					public void onFilterChange(LayerFilteredEvent event) {
						GWT.log("Legend: onLayerFilterChange() - " + event.getLayer().getLabel());
						// find the node & update the icon
						for (TreeNode node : tree.getAllNodes()) {
							if (node instanceof LayerTreeTreeNode) {
								LayerTreeTreeNode layerTreeTreeNode = (LayerTreeTreeNode) node;
								if (layerTreeTreeNode.getLayer().equals(event.getLayer())) {
									((LayerTreeTreeNode) node).updateIcon();
								}
							}
						}
						treeGrid.markForRedraw();
					}
				}));
			}
		}

	}

	@Override
	protected void processNode(ClientAbstractNodeInfo treeNode, TreeNode nodeRoot, boolean refresh) {
		if (treeNode instanceof ClientLayerNodeInfo) {
			Layer<?> layer = mapModel.getLayer(((ClientLayerNodeInfo) treeNode).getLayerId());
			// Ignore layers that are not available in the map
			if (layer != null) {
				if (layer.getLayerInfo() instanceof ClientWmsLayerInfo) {
					WmsLegendNode ltln = new WmsLegendNode(this.tree, layer);
					tree.add(ltln, nodeRoot);
					ltln.init();
				} else {
					super.processNode(treeNode, nodeRoot, refresh);
				}
			} else {
				super.processNode(treeNode, nodeRoot, refresh);
			}
		} else {
			super.processNode(treeNode, nodeRoot, refresh);
		}
	}

	/**
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	public class WmsLegendNode extends LayerTreeLegendNode {

		public WmsLegendNode(RefreshableTree tree, Layer<?> layer) {
			super(tree, layer);
			InternalClientWmsLayer wmsLayer = (InternalClientWmsLayer) layer;
			if (wmsLayer.getWmsLayer().getCapabilities().getLayers().isEmpty()) {
				setTitle(wmsLayer.getWmsLayer().getCapabilities().getTitle());
			} else {
				setTitle(wmsLayer.getWmsLayer().getCapabilities().getTitle() + " (all)");
			}
		}

		public AbstractLayer<?> getLayer() {
			return super.getLayer();
		}

		public void init() {
		}

		public RefreshableTree getTree() {
			return tree;
		}
	}

	/**
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	public class WmsLegendItemNode extends LayerTreeTreeNode {

		public WmsLegendItemNode(WmsLegendNode parent, ClientWmsLayer layer) {
			super(parent.getTree(), parent.getLayer());
			setTitle(layer.getTitle());
			setName(parent.getAttribute("id") + "_legend");
			if (layer instanceof LegendUrlSupported) {
				setIcon(((LegendUrlSupported) layer).getLegendImageUrl());
			}
		}
	}

	/**
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	protected class MyTree extends RefreshableTree {

	}

}
