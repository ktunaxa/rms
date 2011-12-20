/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.event.MapModelChangedEvent;
import org.geomajas.gwt.client.map.event.MapModelChangedHandler;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.widget.Legend;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.client.layer.ReferenceLayer;
import org.ktunaxa.referral.client.layer.ReferenceSubLayer;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

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

		Legend legend = new Legend(mapWidget.getMapModel());
		legend.setHeight100();
		legend.setWidth100();
		tabLegend.setPane(legend);

		tabs.setTabs(tabLegend, tabBackGround, tabBase, tabValue, tabReferrals);
		tabs.selectTab(tabValue);
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

				Layer<?> layer = mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_OSM_ID);
				LayerBlock osmBlock = new LayerBlock(layer);
				VLayout sectionStackLayout = new VLayout();
				sectionStackLayout.addMember(osmBlock);

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