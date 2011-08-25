/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
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
import org.ktunaxa.referral.client.referral.ReferralCreationWizard;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedEvent;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedHandler;
import org.ktunaxa.referral.client.widget.ReferralMapWidget;
import org.ktunaxa.referral.client.widget.ResizableLeftLayout;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * General definition of the main layout for the Ktunaxa mapping component. It defines a left info pane and a map.
 * 
 * @author Jan De Moerloose
 * @author Pieter De Graef
 */
public final class MapLayout extends VLayout {

	private static final MapLayout INSTANCE = new MapLayout();

	private static final String STYLE_BLOCK = "block";

	private static final LocalizedMessages MESSAGES = GWT.create(LocalizedMessages.class);

	private TopBar topBar;

	private MenuBar menuBar;

	private VLayout bodyLayout;

	private ReferralMapWidget mapWidget;

	private ResizableLeftLayout infoPane;

	private LayersPanel layerPanel;

	private ReferralPanel referralPanel;

	private BpmPanel bpmPanel;

	private TaskDto currentTask;

	private ToolStripButton referralButton;

	private org.geomajas.layer.feature.Feature currentReferral;

	private VectorLayer referralLayer;

	private HandlerManager handlerManager;

	public static MapLayout getInstance() {
		return INSTANCE;
	}

	private MapLayout() {
		super();
		handlerManager = new HandlerManager(this);

		setWidth100();
		setHeight100();
		setOverflow(Overflow.HIDDEN);

		// the info pane
		infoPane = new ResizableLeftLayout();
		infoPane.setStyleName(STYLE_BLOCK);

		// the map
		mapWidget = new ReferralMapWidget("mapMain", "app");

		// add layers, referral, GIS panel
		layerPanel = new LayersPanel(mapWidget);
		infoPane.addCard(layerPanel.getName(), "Manage layers", layerPanel);
		referralPanel = new ReferralPanel();
		infoPane.addCard(referralPanel.getName(), "Manage referral", referralPanel);
		referralButton = getLastButton();
		referralButton.setDisabled(true); // no referral at start
		SearchPanel searchPanel = new SearchPanel(this);
		infoPane.addCard(searchPanel.getName(), "Search", searchPanel);
		bpmPanel = new BpmPanel();
		infoPane.addCard(bpmPanel.getName(), "Referral process", bpmPanel);
		// top bar
		topBar = new TopBar();
		// menu bar
		menuBar = new MenuBar();
		for (ToolStripButton button : infoPane.getButtons()) {
			menuBar.addNavigationButton(button);
		}
		ToolStripButton newButton = new ToolStripButton("NEW");
		newButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				createReferral();
			}
		});

		menuBar.addActionButton(newButton);

		// add all
		VLayout mapLayout = new VLayout();
		mapLayout.setSize("100%", "100%");
		mapLayout.setStyleName(STYLE_BLOCK);
		final Toolbar toolbar = new Toolbar(mapWidget);
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

		bodyLayout = new VLayout();
		bodyLayout.setOverflow(Overflow.AUTO);
		HLayout hLayout = new HLayout();
		hLayout.setMargin(LayoutConstant.MARGIN_SMALL);
		hLayout.setWidth100();
		hLayout.setHeight100();
		hLayout.addMember(infoPane);
		hLayout.addMember(mapLayout);
		bodyLayout.addMember(hLayout);
		addMember(bodyLayout);
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

	public TaskDto getCurrentTask() {
		return currentTask;
	}

	public org.geomajas.layer.feature.Feature getCurrentReferral() {
		return currentReferral;
	}

	/**
	 * Set the current referral and current task.
	 * 
	 * @param referral
	 *            referral to select
	 * @param task
	 *            task to select
	 */
	public void setReferralAndTask(@Nullable org.geomajas.layer.feature.Feature referral, @Nullable TaskDto task) {
		currentReferral = referral;
		currentTask = task;
		String title;
		if (null != referral) {
			referralLayer = (VectorLayer) getMap().getMapModel().getLayer(KtunaxaConstant.LAYER_REFERRAL_ID);
			Feature feature = new Feature(referral, referralLayer);
			GWT.log("Referral found: " + feature.getId());
			getReferralPanel().init(referralLayer, feature);
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
			String referralDescription = feature.getAttributeValue(KtunaxaConstant.ATTRIBUTE_PROJECT).toString();
			if (null != task) {
				String taskDescription = task.getDescription();
				title = MESSAGES.referralAndTaskTitle(referral.getId(), referralDescription, taskDescription);
			} else {
				title = MESSAGES.referralTitle(referral.getId(), referralDescription);
			}
			referralButton.setDisabled(false);
		} else {
			title = MESSAGES.mapTitle();
			referralButton.setDisabled(true);
		}
		getTopBar().setLeftTitle(title);

		if (null != referral) {
			// open the referral tab
			infoPane.showCard(ReferralPanel.NAME);
		} else {
			// open the layers tab
			infoPane.showCard(LayersPanel.NAME);
		}
		handlerManager.fireEvent(new CurrentReferralChangedEvent(referral));
	}

	/**
	 * Add a handler which is invoked when the current referral changes.
	 *
	 * @param handler handler for the event
	 * @return handler registration
	 */
	public HandlerRegistration addCurrentReferralChangedHandler(CurrentReferralChangedHandler handler) {
		return handlerManager.addHandler(CurrentReferralChangedHandler.TYPE, handler);
	}

	/**
	 * Put the focus on the current task.
	 */
	public void focusCurrentTask() {
		infoPane.showCard(referralPanel.getName());
		referralPanel.focusCurrentTask();
	}

	/**
	 * Put the focus on the current task.
	 */
	public void focusBpm() {
		infoPane.showCard(bpmPanel.getName());
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

	/** Create a new referral. */
	public void createReferral() {
		final String title = topBar.getLeftTitle();
		final VLayout haze = new VLayout();
		haze.setSize(menuBar.getWidthAsString(), menuBar.getHeightAsString());
		haze.setBackgroundColor("#EEEEEE");
		haze.setOpacity(60);

		final VLayout body = new VLayout();
		body.setMargin(LayoutConstant.MARGIN_SMALL);
		ReferralCreationWizard wizard = new ReferralCreationWizard(new Runnable() {

			public void run() {
				bodyLayout.removeChild(body);
				bodyLayout.getChildren()[0].setVisible(true);
				body.destroy();
				menuBar.removeChild(haze);
				topBar.setLeft(title);
			}
		});
		body.addMember((ReferralCreationWizard.ReferralWizardView) wizard.getView());
		addMember(body);
		wizard.init();
		bodyLayout.addMember(body);
		bodyLayout.getChildren()[0].setVisible(false);

		// Disable all buttons in the toolbar:
		menuBar.addChild(haze);
		topBar.setLeftTitle("Referral Management System - Referral Creation Wizard");
	}

	public VectorLayer getReferralLayer() {
		return referralLayer;
	}
}