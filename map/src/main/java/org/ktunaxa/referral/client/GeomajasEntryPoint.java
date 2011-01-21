/*
 * This file is part of Geomajas, a component framework for building
 * rich Internet applications (RIA) with sophisticated capabilities for the
 * display, analysis and management of geographic information.
 * It is a building block that allows developers to add maps
 * and other geographic data capabilities to their web applications.
 *
 * Copyright 2008-2010 Geosparc, http://www.geosparc.com, Belgium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.client;

import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.Toolbar;
import org.ktunaxa.referral.client.gui.CloseableLayout;
import org.ktunaxa.referral.client.gui.LayerTreePanel;
import org.ktunaxa.referral.client.gui.CloseableLayout.LayoutAlignment;
import org.ktunaxa.referral.client.i18n.LocalizedMessages;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * Entry point and main class for GWT application. This class defines the layout and functionality of this application.
 * 
 * @author Pieter De Graef
 */
public class GeomajasEntryPoint implements EntryPoint {

	private static final String RFA_ID = "MIN001";

	private static final String RFA_TITLE = "Mining SoilDigger";

	private static final String RFA_DESCRIPTION = "SoilDigger wants to create a copper mine. "
			+ "It will provide work for 12 permanent and sometimes up to 20 people. "
			+ "Twenty tons will be excavated daily. And I need more text because I want to see it being truncated. "
			+ "Even when the screen is large and the display font for this description is small.";

	private LocalizedMessages messages = GWT.create(LocalizedMessages.class);

	private MapWidget mapWidget;

	public void onModuleLoad() {
		VLayout layout = new VLayout(5);
		layout.setWidth100();
		layout.setHeight100();

		layout.addMember(createTitleBar());

		HLayout mainLayout = new HLayout(5);
		mainLayout.addMember(createLeftPanel());
		Canvas rightPanel = createRightPanel();
		mainLayout.addMember(rightPanel);

		layout.addMember(mainLayout);
		layout.draw();

		// Add the layer tree to the layout:
		LayerTreePanel panel = new LayerTreePanel(mapWidget, rightPanel);
		rightPanel.addChild(panel);
	}

	private Canvas createLeftPanel() {
		CloseableLayout layout = new CloseableLayout(LayoutAlignment.LEFT, "Referral management");
		layout.setSize("350px", "100%");

		TabSet tabs = new TabSet();
		Tab tabSearch = new Tab("Search", "[ISOMORPHIC]/geomajas/silk/find.png");
		Tab tabDocs = new Tab("Documents", "[ISOMORPHIC]/images/page.png");
		Tab tabComments = new Tab("Comments", "[ISOMORPHIC]/images/comments.png");
		tabs.addTab(tabSearch);
		tabs.addTab(tabDocs);
		tabs.addTab(tabComments);

		layout.setPanel(tabs);
		return layout;
	}

	private Canvas createRightPanel() {
		VLayout layout = new VLayout();
		layout.setSize("100%", "100%");
		layout.setStyleName("mapLayout");

		mapWidget = new MapWidget("mainMap", "app");
		Toolbar toolbar = new Toolbar(mapWidget);
		toolbar.setButtonSize(Toolbar.BUTTON_SIZE_BIG);
		toolbar.setBackgroundImage("");
		toolbar.setBackgroundColor("#647386");
		toolbar.setBorder("0px");

		layout.addMember(toolbar);
		layout.addMember(mapWidget);

		return layout;
	}

	private Canvas createTitleBar() {
		ToolStrip topBar = new ToolStrip();
		topBar.setMembersMargin(2);
		topBar.setHeight(32);
		topBar.setWidth100();
		topBar.addSpacer(6);
		topBar.setBackgroundImage("");
		topBar.setBackgroundColor("#B6B6B6");
		topBar.setBorder("");

		Img icon = new Img("[ISOMORPHIC]/images/ktunaxa_logo.png", 24, 18);
		icon.setSize(24);
		topBar.addMember(icon);
		topBar.addSpacer(6);

		HTMLFlow rfaLabel = new HTMLFlow("<div class=\"sgwtTitle\">" + messages.applicationTitle(RFA_ID, RFA_TITLE)
				+ "</div>");
		rfaLabel.setTooltip(RFA_DESCRIPTION);
		rfaLabel.setHoverWidth(500);
		rfaLabel.setWidth100();

		topBar.addMember(rfaLabel);
		topBar.addFill();
		topBar.addMember(new ToolStripButton("Select"));
		topBar.addMember(new ToolStripButton("Print"));
		topBar.addMember(new ToolStripButton("Finish"));
		topBar.addMember(new ToolStripButton("Tasks"));
		topBar.addMember(new ToolStripButton("Request"));
		return topBar;
	}
}
