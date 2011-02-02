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

import org.ktunaxa.referral.client.gui.LayerPanel;
import org.ktunaxa.referral.client.gui.MainGui;
import org.ktunaxa.referral.client.gui.SearchPanel;
import org.ktunaxa.referral.client.i18n.LocalizedMessages;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

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

	private MainGui mainGui;
	
	private LayerPanel layerPanel;
	
	private SearchPanel searchPanel;

	public void onModuleLoad() {
		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();

		mainGui = new MainGui();

		layout.addMember(createHeader());
		layout.addMember(createMenuBar());
		
		VLayout subHeader = new VLayout();
		subHeader.setSize("100%", "15px");
		subHeader.setStyleName("subHeader");
		layout.addMember(subHeader);

		layout.addMember(mainGui);
		layout.draw();
	}

	private Canvas createHeader() {
		HLayout header = new HLayout();
		header.setSize("100%", "44");
		header.setStyleName("header");

		HTMLFlow rfaLabel = new HTMLFlow(messages.applicationTitle(RFA_ID, RFA_TITLE));
		rfaLabel.setStyleName("headerText");
		rfaLabel.setTooltip(RFA_DESCRIPTION);
		rfaLabel.setHoverWidth(500);
		rfaLabel.setWidth100();
		header.addMember(rfaLabel);

		LayoutSpacer spacer = new LayoutSpacer();
		header.addMember(spacer);

		ToolStrip headerBar = new ToolStrip();
		headerBar.setMembersMargin(2);
		headerBar.setSize("445", "44");
		headerBar.addFill();
		headerBar.setStyleName("headerRight");

		headerBar.addMember(new ToolStripButton("Select"));
		headerBar.addMember(new ToolStripButton("Finish"));
		headerBar.addMember(new ToolStripButton("Tasks"));
		headerBar.addMember(new ToolStripButton("Request"));
		headerBar.addSpacer(10);
		header.addMember(headerBar);

		return header;
	}

	private Canvas createMenuBar() {
		ToolStrip menuBar = new ToolStrip();
		menuBar.setMembersMargin(5);
		menuBar.setSize("100%", "36");
		menuBar.addSpacer(6);
		menuBar.setBorder("none");
		
		// Create the panels:
		layerPanel = new LayerPanel(mainGui.getMapWidget());
		searchPanel = new SearchPanel(mainGui.getMapWidget());

		// Layer button:
		ToolStripButton layerButton = new ToolStripButton("Layers");
		layerButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(layerPanel, "Manage layers", "250");
				mainGui.hideBottomLayout();
			}
		});
		layerButton.setActionType(SelectionType.RADIO);
		layerButton.setRadioGroup("the-only-one");
		menuBar.addMember(layerButton);
		menuBar.addMember(new ToolStripSeparator());

		// Activate Layer stuff
		layerButton.select();
		mainGui.showLeftLayout(layerPanel, "Manage layers", "250");
		mainGui.hideBottomLayout();

		// Search button:
		ToolStripButton searchButton = new ToolStripButton("Search");
		searchButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showBottomLayout(searchPanel, "Search the map", "250");
				mainGui.hideLeftLayout();
			}
		});
		searchButton.setActionType(SelectionType.RADIO);
		searchButton.setRadioGroup("the-only-one");
		menuBar.addMember(searchButton);
		menuBar.addMember(new ToolStripSeparator());

		// Check button:
		ToolStripButton checkButton = new ToolStripButton("Checks");
		checkButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(new VLayout(), "Execute checks", "400");
				mainGui.hideBottomLayout();
			}
		});
		checkButton.setActionType(SelectionType.RADIO);
		checkButton.setRadioGroup("the-only-one");
		menuBar.addMember(checkButton);
		menuBar.addMember(new ToolStripSeparator());

		// Print button:
		ToolStripButton printButton = new ToolStripButton("Print");
		printButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(new VLayout(), "Print the map", "300");
				mainGui.hideBottomLayout();
			}
		});
		printButton.setActionType(SelectionType.RADIO);
		printButton.setRadioGroup("the-only-one");
		menuBar.addMember(printButton);
		menuBar.addMember(new ToolStripSeparator());

		// Document button:
		ToolStripButton documentButton = new ToolStripButton("Documents");
		documentButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(new VLayout(), "Manage documents", "100%");
				mainGui.hideBottomLayout();
			}
		});
		documentButton.setActionType(SelectionType.RADIO);
		documentButton.setRadioGroup("the-only-one");
		menuBar.addMember(documentButton);
		menuBar.addMember(new ToolStripSeparator());

		// Comments button:
		ToolStripButton commentButton = new ToolStripButton("Comments");
		commentButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(new VLayout(), "Manage comments", "100%");
				mainGui.hideBottomLayout();
			}
		});
		commentButton.setActionType(SelectionType.RADIO);
		commentButton.setRadioGroup("the-only-one");
		menuBar.addMember(commentButton);

		return menuBar;
	}
}
