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

import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.action.layertree.LayerTreeModalAction;
import org.geomajas.gwt.client.action.layertree.LayerVisibleModalAction;
import org.geomajas.gwt.client.map.layer.Layer;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * Layer block that display a panel for turning a layer (a real layer) on and off.
 * 
 * @author Pieter De Graef
 */
public class LayerBlock extends VLayout {

	private Layer<?> layer;

	private LayerTreeModalAction modalAction;

	private IButton visibleBtn;

	public LayerBlock(Layer<?> layer) {
		this.layer = layer;
		modalAction = new LayerVisibleModalAction();
		setStyleName("layerBlock");
		setSize("100%", "1");

		HLayout layout = new HLayout();
		layout.setSize("100%", "26px");

		layout.setLayoutLeftMargin(LayoutConstant.MARGIN_LARGE);
		HTMLFlow title = new HTMLFlow("<div style='line-height:26px;'>" + layer.getLabel() + "</div>");
		title.setWidth100();
		title.setLayoutAlign(VerticalAlignment.CENTER);
		layout.addMember(title);

		visibleBtn = new IButton("visible");
		visibleBtn.setLayoutAlign(VerticalAlignment.CENTER);
		visibleBtn.setShowTitle(false);
		visibleBtn.setSize("24", "22");
		visibleBtn.setIconSize(16);
		visibleBtn.setActionType(SelectionType.CHECKBOX);
		visibleBtn.setSelected(layer.getLayerInfo().isVisible());
		visibleBtn.addClickHandler(new SetVisibleHandler());
		updateIcons();
		layout.addMember(visibleBtn);
		addMember(layout);
	}

	private void updateIcons() {
		if (visibleBtn.isSelected()) {
			if (layer.isShowing()) {
				visibleBtn.setIcon(modalAction.getSelectedIcon());
				visibleBtn.setTooltip(modalAction.getSelectedTooltip());
				visibleBtn.setDisabled(false);
			} else {
				visibleBtn.setIcon(modalAction.getSelectedIcon());
				visibleBtn.setTooltip(modalAction.getSelectedTooltip());
				visibleBtn.setDisabled(true);
			}
		} else {
			visibleBtn.setIcon(modalAction.getDeselectedIcon());
			visibleBtn.setTooltip(modalAction.getDeselectedTooltip());
		}
	}

	/**
	 * Toggle whether layer is enabled/visible.
	 *
	 * @param enabled should layer be enabled/visible
	 */
	protected void updateLayerEnabled(boolean enabled) {
		layer.setVisible(enabled);
	}

	/**
	 * Set the layer to visible/invisible.
	 * 
	 * @author Jan De Moerloose
	 */
	private class SetVisibleHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			updateLayerEnabled(visibleBtn.isSelected());
			updateIcons();
		}
	}
}