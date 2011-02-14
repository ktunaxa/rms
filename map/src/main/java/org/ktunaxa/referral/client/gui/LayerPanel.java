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
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.server.command.request.GetLayersRequest;
import org.ktunaxa.referral.server.command.request.GetLayersResponse;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
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

		final SectionStack baseStack = new SectionStack();
		baseStack.setSize("100%", "100%");
		baseStack.setOverflow(Overflow.AUTO);
		baseStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		baseStack.setPadding(5);
		tabBase.setPane(baseStack);

		GwtCommand command = new GwtCommand("command.GetLayers");
		command.setCommandRequest(new GetLayersRequest());
		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

			public void execute(CommandResponse response) {
				if (response instanceof GetLayersResponse) {
					GetLayersResponse glr = (GetLayersResponse) response;
					for (ReferenceLayerTypeDto type : glr.getLayerTypes()) {
						if (type.isBaseLayer()) {
							// Create section in stack:
							SectionStackSection section = new SectionStackSection(type.getDescription());
							section.setExpanded(true);
							section.addItem(new VLayout());
							baseStack.addSection(section);
						}
					}

					for (ReferenceLayerDto layer : glr.getLayers()) {
						ReferenceLayerTypeDto type = layer.getType();
						if (type.isBaseLayer()) {
							((VLayout) baseStack.getSection((int) type.getId() - 1).getItems()[0])
									.addMember(new LayerBlock(layer));
						} else {
							panelValue.addMember(new HTMLFlow(layer.getName()));
						}
					}
				}
			}
		});
	}

	/**
	 * Widget that represents a layer in the layer tree layout.
	 * 
	 * @author Pieter De Graef
	 */
	private class LayerBlock extends HLayout {

		public LayerBlock(ReferenceLayerDto layer) {
			super();
			setSize("100%", "30px");
			setStyleName("layerBlock");

			setLayoutLeftMargin(10);
			HTMLFlow title = new HTMLFlow("<div style='line-height:30px;'>" + layer.getName() + "</div>");
			title.setWidth100();
			title.setLayoutAlign(VerticalAlignment.CENTER);
			addMember(title);

			IButton visibleBtn = new IButton("");
			visibleBtn.setLayoutAlign(VerticalAlignment.CENTER);
			visibleBtn.setShowTitle(false);
			visibleBtn.setSize("24", "24");
			visibleBtn.setIcon("[ISOMORPHIC]/geomajas/widget/layertree/layer-show.png");
			visibleBtn.setIconSize(16);
			visibleBtn.setActionType(SelectionType.CHECKBOX);
			addMember(visibleBtn);

			Menu menu = new Menu();
			menu.setShowShadow(true);
			menu.setShadowDepth(10);
			MenuItem item1 = new MenuItem("Toggle labels", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
			MenuItem item2 = new MenuItem("Refresh layer", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
			MenuItem item3 = new MenuItem("Zoom to layer", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
			MenuItem item4 = new MenuItem("Show meta-data", "[ISOMORPHIC]/geomajas/widget/layertree/labels-show.png");
			menu.setItems(item1, item2, item3, item4);

			IMenuButton menuButton = new IMenuButton("", menu);
			menuButton.setLayoutAlign(VerticalAlignment.CENTER);
			menuButton.setShowTitle(false);
			menuButton.setSize("24", "24");
			addMember(menuButton);
		}
	}
}