/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.widget.Toolbar;
import org.ktunaxa.referral.client.widget.ReferralMapWidget;
import org.ktunaxa.referral.client.widget.ResizableLeftLayout;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * General definition of the main layout for the Ktunaxa mapping component. It
 * defines a left infopane and a map.
 * 
 * @author Jan De Moerloose
 * @author Pieter De Graef
 */
public class MapLayout extends VLayout {

	private TopBar topBar;

	private MenuBar menuBar;

	private ReferralMapWidget mapWidget;

	private ResizableLeftLayout infoPane;

	private LayersPanel layerPanel;

	private ReferralPanel referralPanel;

	private AnalysisPanel analysisPanel;

	private ToolStripButton newButton;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public MapLayout(String referralId, String taskId) {
		setWidth100();
		setHeight100();
		// the info pane
		infoPane = new ResizableLeftLayout();
		infoPane.setStyleName("block");
		
		// the map
		mapWidget = new ReferralMapWidget("mainMap", "app", referralId, taskId);

		// add layers, referral, GIS panel
		layerPanel = new LayersPanel(mapWidget);
		infoPane.addCard("LAYERS", "Manage layers", layerPanel);
		referralPanel = new ReferralPanel(mapWidget);
		infoPane.addCard("REFERRAL", "Manage referral", referralPanel);
		analysisPanel = new AnalysisPanel(mapWidget);
		infoPane.addCard("ANALYSIS", "GIS Analysis", analysisPanel);
		// top bar
		topBar = new TopBar();
		// menu bar
		menuBar = new MenuBar();
		for (ToolStripButton button : infoPane.getButtons()) {
			menuBar.addNavigationButton(button);
		}
		newButton = new ToolStripButton("NEW");
		menuBar.addActionButton(newButton);

		// add all
		VLayout mapLayout = new VLayout();
		mapLayout.setSize("100%", "100%");
		mapLayout.setStyleName("block");
		Toolbar toolbar = new Toolbar(mapWidget);
		toolbar.setButtonSize(Toolbar.BUTTON_SIZE_BIG);
		toolbar.setBorder("none");

		mapLayout.addMember(toolbar);
		mapLayout.addMember(mapWidget);

		addMember(topBar);
		addMember(menuBar);
		VLayout subHeader = new VLayout();
		subHeader.setSize("100%", "15px");
		subHeader.setStyleName("subHeader");
		addMember(subHeader);

		HLayout hLayout = new HLayout();
		hLayout.setMargin(5);
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.addMember(infoPane);
		hLayout.addMember(mapLayout);
		addMember(hLayout);
	}

	public ReferralMapWidget getMap() {
		return mapWidget;
	}

	public TopBar getTopBar() {
		return topBar;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public ResizableLeftLayout getInfoPane() {
		return infoPane;
	}

	public LayersPanel getLayerPanel() {
		return layerPanel;
	}

	public ToolStripButton getNewButton() {
		return newButton;
	}
	
	

}