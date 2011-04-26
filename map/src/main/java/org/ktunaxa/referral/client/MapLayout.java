/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client;

import com.google.gwt.user.client.Window;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.SearchFeatureRequest;
import org.geomajas.command.dto.SearchFeatureResponse;
import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.ktunaxa.referral.client.gui.CommentPanel;
import org.ktunaxa.referral.client.gui.DocumentPanel;
import org.ktunaxa.referral.client.gui.LayerPanel;
import org.ktunaxa.referral.client.gui.MainGui;
import org.ktunaxa.referral.client.gui.SearchPanel;
import org.ktunaxa.referral.client.referral.ReferralUtil;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;
import org.ktunaxa.referral.server.command.request.FinishTaskRequest;
import org.ktunaxa.referral.server.command.request.UrlResponse;

/**
 * Default Ktunaxa layout.
 * 
 * @author Pieter De Graef
 */
public class MapLayout extends VLayout {

	private MainGui mainGui;

	private LayerPanel layerPanel;

	private SearchPanel searchPanel;

	private VLayout printPanel;

	private DocumentPanel documentPanel;

	private CommentPanel commentPanel2;

	private VLayout helpPanel;

	private String referralId;

	private String bpmId;

	public MapLayout(String referralId, String bpmId) {
		this.referralId = referralId;
		this.bpmId = bpmId;
		setWidth100();
		setHeight100();

		mainGui = new MainGui();
		addMember(createMenuBar());

		VLayout subHeader = new VLayout();
		subHeader.setSize("100%", "15px");
		subHeader.setStyleName("subHeader");
		addMember(subHeader);

		addMember(mainGui);
		mainGui.getMapWidget().getMapModel().addMapModelHandler(new InitializingHandler());
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
		printPanel = new VLayout();
		helpPanel = new VLayout();
		if (referralId != null) {
			documentPanel = new DocumentPanel();
			commentPanel2 = new CommentPanel();
		}

		// Layer button:
		ToolStripButton layerButton = new ToolStripButton("LAYERS");
		layerButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(layerPanel, "Manage layers");
				mainGui.hideBottomLayout();
			}
		});
		layerButton.setActionType(SelectionType.RADIO);
		layerButton.setRadioGroup("the-only-one");
		menuBar.addMember(layerButton);
		menuBar.addMember(new ToolStripSeparator());

		// Activate Layer stuff
		layerButton.select();
		mainGui.showLeftLayout(layerPanel, "Manage layers");
		mainGui.hideBottomLayout();

		// Search button:
		ToolStripButton searchButton = new ToolStripButton("GIS SEARCH");
		searchButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(searchPanel, "Search the map");
				mainGui.hideBottomLayout();
			}
		});
		searchButton.setActionType(SelectionType.RADIO);
		searchButton.setRadioGroup("the-only-one");
		menuBar.addMember(searchButton);
		menuBar.addMember(new ToolStripSeparator());

		// Print button:
		ToolStripButton printButton = new ToolStripButton("PRINT");
		printButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(printPanel, "Print the map");
				mainGui.hideBottomLayout();
			}
		});
		printButton.setActionType(SelectionType.RADIO);
		printButton.setRadioGroup("the-only-one");
		menuBar.addMember(printButton);
		menuBar.addMember(new ToolStripSeparator());

		// Help button:
		ToolStripButton helpButton = new ToolStripButton("HELP");
		helpButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mainGui.showLeftLayout(helpPanel, "Help");
				mainGui.hideBottomLayout();
			}
		});
		helpButton.setActionType(SelectionType.RADIO);
		helpButton.setRadioGroup("the-only-one");
		menuBar.addMember(helpButton);

		if (referralId != null) {
			menuBar.addMember(new LayoutSpacer());

			// Document button:
			ToolStripButton documentButton = new ToolStripButton("DOCUMENTS");
			documentButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					mainGui.showLeftLayout(documentPanel, "Manage documents");
					mainGui.hideBottomLayout();
				}
			});
			documentButton.setActionType(SelectionType.RADIO);
			documentButton.setRadioGroup("the-only-one");
			menuBar.addMember(documentButton);
			menuBar.addMember(new ToolStripSeparator());

			// Comments button:
			ToolStripButton commentButton = new ToolStripButton("COMMENTS");
			commentButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					mainGui.showLeftLayout(commentPanel2, "Manage comments");
					mainGui.hideBottomLayout();
				}
			});
			commentButton.setActionType(SelectionType.RADIO);
			commentButton.setRadioGroup("the-only-one");
			menuBar.addMember(commentButton);
		}
		if (bpmId != null) {
			menuBar.addMember(new ToolStripSeparator());
			// Finish button:
			ToolStripButton finishButton = new ToolStripButton("FINISH");
			finishButton.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					FinishTaskRequest request = new FinishTaskRequest();
					request.setTaskId(bpmId);
					GwtCommand command = new GwtCommand(FinishTaskRequest.COMMAND);
					command.setCommandRequest(request);
					GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

						public void execute(CommandResponse response) {
							if (response instanceof UrlResponse) {
								Window.Location.assign(((UrlResponse) response).getUrl());
							}
						}
					});
				}
			});
			menuBar.addMember(finishButton);
		}
		return menuBar;
	}

	/**
	 * Handles some initialization that requires the map to be loaded.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class InitializingHandler implements MapModelHandler {

		public void onMapModelChange(MapModelEvent event) {
			layerPanel.init(mainGui.getMapWidget());
			if (referralId != null) {
				final VectorLayer layer = (VectorLayer) mainGui.getMapWidget().getMapModel().getLayer("referralLayer");
				layer.setFilter(ReferralUtil.createFilter(referralId));
				layer.setVisible(true);

				SearchFeatureRequest request = new SearchFeatureRequest();
				request.setBooleanOperator("OR");
				request.setCriteria(ReferralUtil.createCriteria(referralId));
				request.setCrs(layer.getMapModel().getCrs());
				request.setLayerId(layer.getServerLayerId());
				request.setMax(1);
				request.setFilter(layer.getFilter());
				request.setFeatureIncludes(GeomajasConstant.FEATURE_INCLUDE_ALL);

				GwtCommand command = new GwtCommand("command.feature.Search");
				command.setCommandRequest(request);
				GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

					public void execute(CommandResponse response) {
						SearchFeatureResponse sfr = (SearchFeatureResponse) response;
						org.geomajas.layer.feature.Feature[] features = sfr.getFeatures();
						if (features != null && features.length > 0) {
							Feature feature = new Feature(features[0], layer);
							GWT.log("Feature found: " + feature.getId());
							// Now display feature on this page!
						}
					}
				});
			}
		}
	}
}