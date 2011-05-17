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
import com.smartgwt.client.widgets.HTMLFlow;
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

	private VLayout panelValue;

	private ReferenceLayer referenceLayer;

	private SectionStack baseStack;

	public static final String NAME = "LAYERS";

	public LayersPanel(MapWidget mapWidget) {
		setSize("100%", "100%");

		TabSet tabs = new TabSet();
		tabs.setSize("100%", "100%");
		Tab tabBase = new Tab("Base layers");
		Tab tabValue = new Tab("Ktunaxa Values");
		panelValue = new VLayout();
		panelValue.setOverflow(Overflow.AUTO);
		tabValue.setPane(panelValue);
		tabs.setTabs(tabBase, tabValue);
		addMember(tabs);

		baseStack = new SectionStack();
		baseStack.setSize("100%", "100%");
		baseStack.setOverflow(Overflow.AUTO);
		baseStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		baseStack.setPadding(5);
		tabBase.setPane(baseStack);
	}

	public void setReferenceLayer(ReferenceLayer referenceLayer) {
		this.referenceLayer = referenceLayer;
		for (ReferenceLayerTypeDto type : referenceLayer.getLayerTypes()) {
			if (type.isBaseLayer()) {
				// Create section in stack:
				SectionStackSection section = new SectionStackSection(
						type.getDescription());
				section.setExpanded(true);
				section.addItem(new VLayout());
				baseStack.addSection(section);
			}
		}

		for (ReferenceSubLayer subLayer : referenceLayer.getSubLayers()) {
			ReferenceLayerTypeDto type = subLayer.getDto().getType();
			if (type.isBaseLayer()) {
				((VLayout) baseStack.getSection((int) type.getId() - 1)
						.getItems()[0]).addMember(new LayerBlock(subLayer));
			} else {
				panelValue.addMember(new HTMLFlow(subLayer.getDto().getName()));
			}
		}
	}

	public ReferenceLayer getReferenceLayer() {
		return referenceLayer;
	}

	public String getName() {
		return NAME;
	}

}