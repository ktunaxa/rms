/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.configuration.client.ClientLayerInfo;
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
public class LayerBlock extends HLayout {

	private Layer<?> layer;

	private LayerTreeModalAction modalAction;

	private IButton visibleBtn;

	private HTMLFlow title;

	public LayerBlock(Layer<?> layer) {
		this.layer = layer;
		modalAction = new LayerVisibleModalAction();

		setSize("100%", "26px");
		setStyleName("layerBlock");

		setLayoutLeftMargin(LayoutConstant.MARGIN_LARGE);
		title = new HTMLFlow("<div style='line-height:26px;'>" + layer.getLabel() + "</div>");
		title.setWidth100();
		title.setLayoutAlign(VerticalAlignment.CENTER);
		addMember(title);

		visibleBtn = new IButton("visible");
		visibleBtn.setLayoutAlign(VerticalAlignment.CENTER);
		visibleBtn.setShowTitle(false);
		visibleBtn.setSize("24", "22");
		visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-show.png");
		visibleBtn.setIconSize(16);
		visibleBtn.setActionType(SelectionType.CHECKBOX);
		visibleBtn.setSelected(((ClientLayerInfo) layer.getLayerInfo()).isVisible());
		visibleBtn.addClickHandler(new SetVisibleHandler());
		addMember(visibleBtn);
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
	 * Set the layer to visible/invisible.
	 * 
	 * @author Jan De Moerloose
	 */
	private class SetVisibleHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			layer.setVisible(visibleBtn.isSelected());
			updateIcons();
		}
	}
}