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

import org.geomajas.command.CommandResponse;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.client.layer.ReferenceLayer;
import org.ktunaxa.referral.client.layer.ReferenceSubLayer;
import org.ktunaxa.referral.client.layer.action.ShowMetadataHandler;
import org.ktunaxa.referral.server.command.request.GetLayersRequest;
import org.ktunaxa.referral.server.command.request.GetLayersResponse;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Panel that displays a layer tree and a legend.
 * 
 * @author Pieter De Graef
 */
public class LayerPanel extends VLayout {

	private VLayout panelValue;
	
	private ReferenceLayer referenceLayer;
	
	private SectionStack baseStack;

	public LayerPanel(MapWidget mapWidget) {
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

	public void init(MapWidget mapWidget) {
		final MapModel mapModel = mapWidget.getMapModel();
		GwtCommand command = new GwtCommand("command.GetLayers");
		command.setCommandRequest(new GetLayersRequest());
		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

			public void execute(CommandResponse response) {
				if (response instanceof GetLayersResponse) {
					GetLayersResponse glr = (GetLayersResponse) response;
					VectorLayer layer = (VectorLayer) mapModel.getLayer("referenceBaseLayer");
					referenceLayer = new ReferenceLayer(layer, glr.getLayers(), glr.getLayerTypes());
					for (ReferenceLayerTypeDto type : glr.getLayerTypes()) {
						if (type.isBaseLayer()) {
							// Create section in stack:
							SectionStackSection section = new SectionStackSection(type.getDescription());
							section.setExpanded(true);
							section.addItem(new VLayout());
							baseStack.addSection(section);
						}
					}

					for (ReferenceSubLayer subLayer : referenceLayer.getSubLayers()) {
						ReferenceLayerTypeDto type = subLayer.getDto().getType();
						if (type.isBaseLayer()) {
							((VLayout) baseStack.getSection((int) type.getId() - 1).getItems()[0])
									.addMember(new LayerBlock(subLayer));
						} else {
							panelValue.addMember(new HTMLFlow(subLayer.getDto().getName()));
						}
					}
				}
			}
		});
	}

}