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
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.layer.google.gwt.client.GoogleAddon;
import org.geomajas.widget.layer.client.widget.CombinedLayertree;
import org.ktunaxa.referral.client.layer.ReferenceLayer;
import org.ktunaxa.referral.client.layer.ReferenceSubLayer;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Panel that displays the base and value layer trees.
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 */
public class LayersPanel extends VLayout {

	private final Tab tabBase;

	private final Tab tabValue;

	public static final String NAME = "LAYERS";

	public LayersPanel(final MapWidget mapWidget) {
		super();

		setSize("100%", "100%");

		TabSet tabs = new TabSet();
		tabs.setSize("100%", "100%");
		tabBase = new Tab("Base layers");
		tabValue = new Tab("Ktunaxa Values");
		final Tab tabBackGround = new Tab("Background");
		final Tab tabReferrals = new Tab("Referrals");
		Tab tabLegend = new Tab("Legend");

		CombinedLayertree legend = new CombinedLayertree(mapWidget);
		legend.setHeight100();
		legend.setWidth100();
		tabLegend.setPane(legend);

		tabs.setTabs(tabLegend, tabReferrals);
		tabs.selectTab(tabLegend);
		addMember(tabs);

		mapWidget.getMapModel().addMapModelChangedHandler(new MapModelChangedHandler() {

			public void onMapModelChanged(MapModelChangedEvent event) {
				final SectionStack bgStack = new SectionStack();
				bgStack.setSize("100%", "100%");
				bgStack.setOverflow(Overflow.AUTO);
				bgStack.setVisibilityMode(VisibilityMode.MUTEX);
				bgStack.setPadding(LayoutConstant.MARGIN_SMALL);
				tabBackGround.setPane(bgStack);

				final SectionStack referralStack = new SectionStack();
				referralStack.setSize("100%", "100%");
				referralStack.setOverflow(Overflow.AUTO);
				referralStack.setVisibilityMode(VisibilityMode.MUTEX);
				referralStack.setPadding(LayoutConstant.MARGIN_SMALL);
				tabReferrals.setPane(referralStack);
				
				// initialize the google addon
				GoogleAddon addon = new GoogleAddon("google", mapWidget, GoogleAddon.MapType.NORMAL);
				mapWidget.registerMapAddon(addon);
				addon.setVisible(false);

				Layer<?> osmLayer = mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_OSM_ID);
				LayerBlock osmBlock = new LayerBlock(osmLayer);
				Layer<?> normalGoogleLayer = mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_GOOGLE_NORMAL_ID);
				LayerBlock normalGoogleBlock = new GoogleLayerBlock(normalGoogleLayer, addon,
						GoogleAddon.MapType.NORMAL);
				Layer<?> satelliteGoogleLayer = mapWidget.getMapModel().getLayer(
						KtunaxaConstant.LAYER_GOOGLE_SATELLITE_ID);
				LayerBlock satelliteGoogleBlock = new GoogleLayerBlock(satelliteGoogleLayer, addon,
						GoogleAddon.MapType.SATELLITE);
				Layer<?> physicalGoogleLayer = mapWidget.getMapModel().getLayer(
						KtunaxaConstant.LAYER_GOOGLE_PHYSICAL_ID);
				LayerBlock physicalGoogleBlock = new GoogleLayerBlock(physicalGoogleLayer, addon,
						GoogleAddon.MapType.PHYSICAL);
				
				// osm and google in radiogroup
				LayerBlockRadioGroup group = new LayerBlockRadioGroup();
				group.addLayerBlock(osmBlock);
				group.addLayerBlock(normalGoogleBlock);
				group.addLayerBlock(satelliteGoogleBlock);
				group.addLayerBlock(physicalGoogleBlock);
				group.init();
				
				Layer<?> aspectLayer = mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_ASPECT_ID);
				LayerBlock aspectBlock = new LayerBlock(aspectLayer);
				Layer<?> slopeLayer = mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_SLOPE_ID);
				LayerBlock slopeBlock = new LayerBlock(slopeLayer);
				Layer<?> hillShadeLayer = mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_HILL_SHADE_ID);
				LayerBlock hillShadeBlock = new LayerBlock(hillShadeLayer);
				VLayout sectionStackLayout = new VLayout();
				sectionStackLayout.addMember(osmBlock);
				sectionStackLayout.addMember(normalGoogleBlock);
				sectionStackLayout.addMember(satelliteGoogleBlock);
				sectionStackLayout.addMember(physicalGoogleBlock);
				sectionStackLayout.addMember(hillShadeBlock);
				sectionStackLayout.addMember(aspectBlock);
				sectionStackLayout.addMember(slopeBlock);

				SectionStackSection section = new SectionStackSection("Background raster");
				section.setExpanded(true);
				section.setCanCollapse(false);
				section.addItem(sectionStackLayout);
				bgStack.addSection(section);

				Layer<?> referral = mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_REFERRAL_ID);
				LayerBlock referralBlock = new ReferralLayerBlock(referral);
				VLayout referralStackLayout = new VLayout();
				referralStackLayout.addMember(referralBlock);

				SectionStackSection sectionStackSection = new SectionStackSection("Referral");
				sectionStackSection.setExpanded(true);
				sectionStackSection.setCanCollapse(false);
				sectionStackSection.addItem(referralStackLayout);
				referralStack.addSection(sectionStackSection);
			}
		});
	}

	public void setBaseLayer(ReferenceLayer baseLayer) {
		tabBase.setPane(copyToStack(baseLayer));
	}

	public void setValueLayer(ReferenceLayer valueLayer) {
		tabValue.setPane(copyToStack(valueLayer));
	}

	public String getName() {
		return NAME;
	}

	private SectionStack copyToStack(ReferenceLayer layer) {
		SectionStack stack = new SectionStack();
		stack.setSize("100%", "100%");
		stack.setOverflow(Overflow.AUTO);
		stack.setVisibilityMode(VisibilityMode.MULTIPLE);
		stack.setPadding(LayoutConstant.MARGIN_SMALL);
		for (ReferenceLayerTypeDto type : layer.getLayerTypes()) {
			// Create section in stack:
			SectionStackSection section = new SectionStackSection(type.getDescription());
			section.setExpanded(false);
			section.addItem(new VLayout());
			section.setID(Long.toString(type.getId()));
			stack.addSection(section);
		}
		for (ReferenceSubLayer subLayer : layer.getSubLayers()) {
			ReferenceLayerTypeDto type = subLayer.getDto().getType();
			((VLayout) stack.getSection(Long.toString(type.getId())).getItems()[0]).
					addMember(new ReferenceLayerBlock(subLayer));
		}
		return stack;
	}
}