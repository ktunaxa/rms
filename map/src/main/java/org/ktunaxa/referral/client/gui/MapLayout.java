/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.google.gwt.core.client.GWT;
import org.geomajas.gwt.client.gfx.paintable.GfxGeometry;
import org.geomajas.gwt.client.gfx.style.ShapeStyle;
import org.geomajas.gwt.client.map.MapView;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.spatial.Bbox;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.MultiPoint;
import org.geomajas.gwt.client.spatial.geometry.Point;
import org.geomajas.gwt.client.widget.Toolbar;
import org.ktunaxa.referral.client.i18n.LocalizedMessages;
import org.ktunaxa.referral.client.widget.ReferralMapWidget;
import org.ktunaxa.referral.client.widget.ResizableLeftLayout;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.List;

/**
 * General definition of the main layout for the Ktunaxa mapping component. It
 * defines a left infopane and a map.
 * 
 * @author Jan De Moerloose
 * @author Pieter De Graef
 */
public final class MapLayout extends VLayout {

	private static final MapLayout INSTANCE = new MapLayout();

	private static final String STYLE_BLOCK = "block";

	private LocalizedMessages messages = GWT.create(LocalizedMessages.class);

	private TopBar topBar;

	private ReferralMapWidget mapWidget;

	private ResizableLeftLayout infoPane;

	private LayersPanel layerPanel;

	private ReferralPanel referralPanel;

	private ToolStripButton newButton;

	private TaskDto currentTask;

	private ToolStripButton referralButton;

	private org.geomajas.layer.feature.Feature currentReferral;

	public static MapLayout getInstance() {
		return INSTANCE;
	}

	private MapLayout() {
		setWidth100();
		setHeight100();
		// the info pane
		infoPane = new ResizableLeftLayout();
		infoPane.setStyleName(STYLE_BLOCK);
		
		// the map
		mapWidget = new ReferralMapWidget("mainMap", "app");

		// add layers, referral, GIS panel
		layerPanel = new LayersPanel(mapWidget);
		infoPane.addCard(layerPanel.getName(), "Manage layers", layerPanel);
		referralPanel = new ReferralPanel();
		infoPane.addCard(referralPanel.getName(), "Manage referral", referralPanel);
		referralButton = getLastButton();
		referralButton.setDisabled(true); // no referral at start
		AnalysisPanel analysisPanel = new AnalysisPanel(mapWidget);
		infoPane.addCard(analysisPanel.getName(), "GIS Analysis", analysisPanel);
		BpmPanel bpmPanel = new BpmPanel();
		infoPane.addCard(bpmPanel.getName(), "Referral process", bpmPanel);
		// top bar
		topBar = new TopBar();
		// menu bar
		MenuBar menuBar = new MenuBar();
		for (ToolStripButton button : infoPane.getButtons()) {
			menuBar.addNavigationButton(button);
		}
		newButton = new ToolStripButton("NEW");
		menuBar.addActionButton(newButton);

		// add all
		VLayout mapLayout = new VLayout();
		mapLayout.setSize("100%", "100%");
		mapLayout.setStyleName(STYLE_BLOCK);
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

	public LayersPanel getLayerPanel() {
		return layerPanel;
	}
	
	public ReferralPanel getReferralPanel() {
		return referralPanel;
	}

	public ToolStripButton getNewButton() {
		return newButton;
	}

	public TaskDto getCurrentTask() {
		return currentTask;
	}

	public org.geomajas.layer.feature.Feature getCurrentReferral() {
		return currentReferral;
	}

	/**
	 * Set the current referral and current task.
	 *
	 * @param referral referral to select
	 * @param task task to select
	 */
	public void setReferralAndTask(org.geomajas.layer.feature.Feature referral, TaskDto task) {
		currentReferral = referral;
		currentTask = task;
		String title;
		if (null != referral) {
			VectorLayer layer = (VectorLayer) getMap().getMapModel()
					.getLayer(KtunaxaConstant.REFERRAL_LAYER_ID);
			Feature feature = new Feature(referral, layer);
			GWT.log("Referral found: " + feature.getId());
			getReferralPanel().init(layer, feature);
			Geometry geometry = feature.getGeometry();
			Bbox bounds = new Bbox(geometry.getBounds());
			if (geometry instanceof MultiPoint || geometry instanceof Point) {
				bounds = new Bbox(0, 0, 500, 500);
				bounds.setCenterPoint(geometry.getBounds().getCenterPoint());
			}
			// Now display feature on this page!
			getMap().getMapModel().getMapView().applyBounds(bounds, MapView.ZoomOption.LEVEL_FIT);
			// highlight the feature
			GfxGeometry highlight = new GfxGeometry("referral-highlight", geometry, new ShapeStyle("#FF00FF", 0.5f,
					"#FF00FF", 0.8f, 1));
			getMap().registerWorldPaintable(highlight);
			String referralDescription = feature.getAttributeValue("projectName").toString();
			if (null != task) {
				String taskDescription = task.getDescription();
				title = messages.referralAndTaskTitle(referral.getId(), referralDescription,
						taskDescription);
			} else {
				title = messages.referralTitle(referral.getId(), referralDescription);
			}
			referralButton.setDisabled(false);
		} else {
			title = messages.mapTitle();
			referralButton.setDisabled(true);
		}
		getTopBar().setLeftTitle(title);

		if (null != referral) {
			// open the referral tab
			infoPane.showCard(ReferralPanel.NAME);
		} else {
			// open the referral tab
			infoPane.showCard(LayersPanel.NAME);
		}

	}

	/**
	 * Put the focus on the current task.
	 */
	public void focusCurrentTask() {
		infoPane.showCard(referralPanel.getName());
		referralPanel.focusCurrentTask();
	}

	/**
	 * Get the button for the last card which was added to the info pane.
	 *
	 * @return last button from info pane
	 */
	public ToolStripButton getLastButton() {
		List<ToolStripButton> buttons = infoPane.getButtons();
		return buttons.get(buttons.size() - 1);
	}
}