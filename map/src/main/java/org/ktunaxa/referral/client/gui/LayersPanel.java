/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

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

	public LayersPanel(MapWidget mapWidget) {
		setSize("100%", "100%");

		TabSet tabs = new TabSet();
		tabs.setSize("100%", "100%");
		Tab tabBase = new Tab("Base layers");
		Tab tabValue = new Tab("Ktunaxa Values");
		
		valueStack = new SectionStack();
		valueStack.setSize("100%", "100%");
		valueStack.setOverflow(Overflow.AUTO);
		valueStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		valueStack.setPadding(5);
		tabValue.setPane(valueStack);

		baseStack = new SectionStack();
		baseStack.setSize("100%", "100%");
		baseStack.setOverflow(Overflow.AUTO);
		baseStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		baseStack.setPadding(5);
		tabBase.setPane(baseStack);
		
		tabs.setTabs(tabBase, tabValue);
		addMember(tabs);
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
			((VLayout) stack.getSection(type.getId() + "").getItems()[0]).addMember(new LayerBlock(subLayer));
		}
	}


}