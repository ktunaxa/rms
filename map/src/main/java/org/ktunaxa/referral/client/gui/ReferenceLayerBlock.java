/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.event.LayerChangedHandler;
import org.geomajas.gwt.client.map.event.LayerFilteredEvent;
import org.geomajas.gwt.client.map.event.LayerLabeledEvent;
import org.geomajas.gwt.client.map.event.LayerShownEvent;
import org.ktunaxa.referral.client.layer.ReferenceSubLayer;
import org.ktunaxa.referral.client.layer.action.RefreshLayerHandler;
import org.ktunaxa.referral.client.layer.action.ShowMetadataHandler;
import org.ktunaxa.referral.client.layer.action.ZoomToLayerHandler;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;

/**
 * Widget that represents a layer in the layer tree layout.
 * 
 * @author Pieter De Graef
 */
public class ReferenceLayerBlock extends HLayout {

	private ReferenceSubLayer subLayer;

	private IButton visibleBtn;

	private HTMLFlow title;

	/**
	 * Testing it all.
	 * 
	 * @param subLayer the layer that you call sublayer
	 */
	public ReferenceLayerBlock(ReferenceSubLayer subLayer) {
		super();

		this.subLayer = subLayer;
		setSize("100%", "26px");
		setStyleName("layerBlock");

		setLayoutLeftMargin(10);
		title = new HTMLFlow("<div style='line-height:26px;'>" + subLayer.getDto().getName() + "</div>");
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
		visibleBtn.setSelected(subLayer.isVisible());
		visibleBtn.addClickHandler(new SetVisibleHandler());
		addMember(visibleBtn);

		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);
		MenuItem item1 = new MenuItem("Refresh layer", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
		MenuItem item2 = new MenuItem("Zoom to layer", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
		MenuItem item3 = new MenuItem("Show meta-data", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
		menu.setItems(item1, item2, item3);
		item1.addClickHandler(new RefreshLayerHandler(subLayer));
		item2.addClickHandler(new ZoomToLayerHandler(subLayer));
		item3.addClickHandler(new ShowMetadataHandler(subLayer));
		IMenuButton menuButton = new IMenuButton("", menu);
		menuButton.setLayoutAlign(VerticalAlignment.CENTER);
		menuButton.setShowTitle(false);
		menuButton.setSize("22", "22");
		addMember(menuButton);
		subLayer.getReferenceLayer().addLayerChangedHandler(new StatusUpdater());
		updateIcons();
	}

	private void updateIcons() {
		if (visibleBtn.isSelected()) {
			if (subLayer.isShowing()) {
				if (subLayer.isLabeled()) {
					visibleBtn.setIcon(null);
					visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-show-labeled.png");
				} else {
					visibleBtn.setIcon(null);
					visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-show.png");
				}
			} else {
				visibleBtn.setIcon(null);
				visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-hide.png");
			}
		} else {
			visibleBtn.setIcon(null);
			visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-invisible.png");
		}
	}

	/**
	 * Updtes status icons.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class StatusUpdater implements LayerChangedHandler {

		public void onVisibleChange(LayerShownEvent event) {
			updateIcons();
		}

		public void onLabelChange(LayerLabeledEvent event) {
		}

		public void onFilterChange(LayerFilteredEvent event) {
			updateIcons();
		}

	}

	/**
	 * Set the layer to visible/invisible.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class SetVisibleHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			subLayer.setVisible(visibleBtn.isSelected());
			updateIcons();
		}

	}

}
