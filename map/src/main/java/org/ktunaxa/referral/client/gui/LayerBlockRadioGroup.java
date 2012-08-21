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
import org.geomajas.gwt.client.map.event.LayerLabeledEvent;
import org.geomajas.gwt.client.map.event.LayerShownEvent;
import org.geomajas.gwt.client.map.layer.AbstractLayer;

/**
 * Lets layer blocks behave like a radio group.
 * 
 * @author Jan De Moerloose
 * 
 */
public class LayerBlockRadioGroup implements LayerChangedHandler {

	private List<LayerBlock> blocks = new ArrayList<LayerBlock>();

	public void addLayerBlock(LayerBlock block) {
		blocks.add(block);
	}

	public void init() {
		for (LayerBlock block : blocks) {
			block.getLayer().addLayerChangedHandler(this);
		}
	}

	@Override
	public void onVisibleChange(LayerShownEvent event) {
		// if a layer becomes visible, all others of the group should become invisible
		if (((AbstractLayer<?>) event.getLayer()).isVisible()) {
			for (LayerBlock block : blocks) {
				if (event.getLayer() != block.getLayer()) {
					block.getLayer().setVisible(false);
				}
			}
		}
	}

	@Override
	public void onLabelChange(LayerLabeledEvent event) {
		// TODO Auto-generated method stub

	}

}
