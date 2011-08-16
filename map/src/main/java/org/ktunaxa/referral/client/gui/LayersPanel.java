/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
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

	private ReferenceLayer baseLayer;

	private ReferenceLayer valueLayer;

	private SectionStack baseStack;

	private SectionStack valueStack;

	public static final String NAME = "LAYERS";

	public LayersPanel(final MapWidget mapWidget) {
		super();

		setSize("100%", "100%");

		TabSet tabs = new TabSet();
		tabs.setSize("100%", "100%");
		Tab tabBase = new Tab("Base layers");
		Tab tabValue = new Tab("Ktunaxa Values");
		Tab tabBackGround = new Tab("Background");
		Tab tabReferrals = new Tab("Referrals");
		Tab tabLegend = new Tab("Legend");

		valueStack = new SectionStack();
		valueStack.setSize("100%", "100%");
		valueStack.setOverflow(Overflow.AUTO);
		valueStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		valueStack.setPadding(LayoutConstant.MARGIN_SMALL);
		tabValue.setPane(valueStack);

		baseStack = new SectionStack();
		baseStack.setSize("100%", "100%");
		baseStack.setOverflow(Overflow.AUTO);
		baseStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		baseStack.setPadding(LayoutConstant.MARGIN_SMALL);
		tabBase.setPane(baseStack);

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

		Legend legend = new Legend(mapWidget.getMapModel());
		legend.setHeight100();
        legend.setWidth100();
		tabLegend.setPane(legend);

		tabs.setTabs(tabLegend, tabBackGround, tabBase, tabValue, tabReferrals);
		tabs.selectTab(tabValue);
		addMember(tabs);

		mapWidget.getMapModel().addMapModelHandler(new MapModelHandler() {

			public void onMapModelChange(MapModelEvent event) {
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
				LayerBlock referralClock = new LayerBlock(referral);
				VLayout referralStackLayout = new VLayout();
				referralStackLayout.addMember(referralClock);

				SectionStackSection sectionStackSection = new SectionStackSection("Referral");
				sectionStackSection.setExpanded(true);
				sectionStackSection.setCanCollapse(false);
				sectionStackSection.addItem(referralStackLayout);
				referralStack.addSection(sectionStackSection);
			}
		});
	}

	public void setBaseLayer(ReferenceLayer baseLayer) {
		this.baseLayer = baseLayer;
		copyToStack(baseLayer, baseStack);
	}

	public void setValueLayer(ReferenceLayer valueLayer) {
		this.valueLayer = valueLayer;
		copyToStack(valueLayer, valueStack);
	}

	public ReferenceLayer getBaseLayer() {
		return baseLayer;
	}

	public ReferenceLayer getValueLayer() {
		return valueLayer;
	}

	public String getName() {
		return NAME;
	}

	private void copyToStack(ReferenceLayer layer, SectionStack stack) {
		for (ReferenceLayerTypeDto type : layer.getLayerTypes()) {
			// Create section in stack:
			SectionStackSection section = new SectionStackSection(type.getDescription());
			section.setExpanded(false);
			section.addItem(new VLayout());
			section.setID(type.getId() + "");
			stack.addSection(section);
		}
		for (ReferenceSubLayer subLayer : layer.getSubLayers()) {
			ReferenceLayerTypeDto type = subLayer.getDto().getType();
			((VLayout) stack.getSection(type.getId() + "").getItems()[0]).addMember(new ReferenceLayerBlock(subLayer));
		}
	}
}