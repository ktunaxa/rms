/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.event.LayerChangedHandler;
import org.geomajas.gwt.client.map.event.LayerFilteredEvent;
import org.geomajas.gwt.client.map.event.LayerLabeledEvent;
import org.geomajas.gwt.client.map.event.LayerShownEvent;
import org.ktunaxa.referral.client.layer.ReferenceSubLayer;
import org.ktunaxa.referral.client.layer.action.ShowMetadataHandler;

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
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * Widget that represents a layer in the layer tree layout.
 * 
 * @author Pieter De Graef
 */
public class LayerBlock extends HLayout {

	private ReferenceSubLayer subLayer;
	
	private IButton visibleBtn;
	
	private HTMLFlow title;

	public LayerBlock(ReferenceSubLayer subLayer) {
		this.subLayer = subLayer;
		setSize("100%", "26px");
		setStyleName("layerBlock");

		setLayoutLeftMargin(10);
		title = new HTMLFlow("<div style='line-height:26px;'>" + subLayer.getDto().getName() + "</div>");
		title.setWidth100();
		title.setLayoutAlign(VerticalAlignment.CENTER);
		addMember(title);
		

		visibleBtn = new IButton("");
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
		MenuItem item1 = new MenuItem("Toggle labels", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
		MenuItem item2 = new MenuItem("Refresh layer", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
		MenuItem item3 = new MenuItem("Zoom to layer", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
		MenuItem item4 = new MenuItem("Show meta-data", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
		menu.setItems(item1, item2, item3, item4);
		item3.addClickHandler(new ZoomToLayerHandler());
		item4.addClickHandler(new ShowMetadataHandler(subLayer));
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
					visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-show-labeled.png");
				} else {
					visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-show.png");
				}
			} else {
				visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-hide.png");
			}
		} else {
			visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-invisible.png");
		}
	}

	/**
	 * Status updater.
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
	 * Set visible handler.
	 */
	public class SetVisibleHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			subLayer.setVisible(visibleBtn.isSelected());
			updateIcons();
		}

	}

	/**
	 * Zoom to layer handler.
	 */
	public static class ZoomToLayerHandler implements com.smartgwt.client.widgets.menu.events.ClickHandler {

		public void onClick(MenuItemClickEvent event) {
			
		}

	}

}
