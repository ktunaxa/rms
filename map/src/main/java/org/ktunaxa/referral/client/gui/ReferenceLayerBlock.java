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

import org.geomajas.gwt.client.map.event.LayerChangedHandler;
import org.geomajas.gwt.client.map.event.LayerLabeledEvent;
import org.geomajas.gwt.client.map.event.LayerShownEvent;
import org.geomajas.gwt.client.util.WidgetLayout;
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

/**
 * Widget that represents a layer in the layer tree layout.
 * 
 * @author Pieter De Graef
 */
public class ReferenceLayerBlock extends HLayout {

	private ReferenceSubLayer subLayer;

	private IButton visibleBtn;

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

		setLayoutLeftMargin(LayoutConstant.MARGIN_LARGE);
		HTMLFlow title = new HTMLFlow("<div style='line-height:26px;'>" + subLayer.getDto().getName() + "</div>");
		title.setWidth100();
		title.setLayoutAlign(VerticalAlignment.CENTER);
		addMember(title);

		visibleBtn = new IButton("visible");
		visibleBtn.setLayoutAlign(VerticalAlignment.CENTER);
		visibleBtn.setShowTitle(false);
		visibleBtn.setSize("24", "22");
		visibleBtn.setIcon(WidgetLayout.iconLayerShow);
		visibleBtn.setIconSize(16);
		visibleBtn.setActionType(SelectionType.CHECKBOX);
		visibleBtn.setSelected(subLayer.isVisible());
		visibleBtn.addClickHandler(new SetVisibleHandler());
		addMember(visibleBtn);

		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(10);
		MenuItem item1 = new MenuItem("Show meta-data", WidgetLayout.iconLabelsShow);
		menu.setItems(item1);
		item1.addClickHandler(new ShowMetadataHandler(subLayer));
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
					visibleBtn.setIcon(WidgetLayout.iconLayerShowLabeled);
				} else {
					visibleBtn.setIcon(null);
					visibleBtn.setIcon(WidgetLayout.iconLayerShow);
				}
			} else {
				visibleBtn.setIcon(null);
				visibleBtn.setIcon(WidgetLayout.iconLayerHide);
			}
		} else {
			visibleBtn.setIcon(null);
			visibleBtn.setIcon(WidgetLayout.iconLayerInvisible);
		}
	}

	/**
	 * Updates status icons.
	 * 
	 * @author Jan De Moerloose
	 */
	public class StatusUpdater implements LayerChangedHandler {

		/** {@inheritDoc} */
		public void onVisibleChange(LayerShownEvent event) {
			updateIcons();
		}

		/** {@inheritDoc} */
		public void onLabelChange(LayerLabeledEvent event) {
			// nothing to do // NOSONAR
		}

	}

	/**
	 * Set the layer to visible/invisible.
	 * 
	 * @author Jan De Moerloose
	 */
	public class SetVisibleHandler implements ClickHandler {

		/** {@inheritDoc} */
		public void onClick(ClickEvent event) {
			subLayer.setVisible(visibleBtn.isSelected());
			updateIcons();
		}

	}

}
