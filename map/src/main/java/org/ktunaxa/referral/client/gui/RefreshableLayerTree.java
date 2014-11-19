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

import org.geomajas.gwt.client.map.event.MapModelChangedEvent;
import org.geomajas.gwt.client.map.event.MapModelChangedHandler;
import org.geomajas.gwt.client.map.layer.AbstractLayer;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.map.layer.configuration.ClientWmsLayerInfo;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.widget.layer.client.widget.CombinedLayertree;
import org.geomajas.widget.layer.configuration.client.ClientAbstractNodeInfo;
import org.geomajas.widget.layer.configuration.client.ClientLayerNodeInfo;
import org.geomajas.widget.layer.configuration.client.ClientLayerTreeInfo;

import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;

/**
 * Refreshable tree, reacts to layer additions (+supports WMS).
 * 
 * @author Jan De Moerloose
 *
 */
public class RefreshableLayerTree extends CombinedLayertree {

	public RefreshableLayerTree(MapWidget mapWidget) {
		super(mapWidget);
		mapWidget.getMapModel().addMapModelChangedHandler(new MapModelChangedHandler() {

			@Override
			public void onMapModelChanged(MapModelChangedEvent event) {
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
			}
		});
	}

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

	public class WmsLegendNode extends LayerTreeTreeNode {

		public WmsLegendNode(RefreshableTree tree, Layer<?> layer) {
			super(tree, layer);
		}

		public AbstractLayer<?> getLayer() {
			return super.getLayer();
		}

		public void init() {
			// TODO: WMS legend
		}
	}

	protected class MyTree extends RefreshableTree {

	}

}
