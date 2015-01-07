/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geomajas.configuration.FeatureStyleInfo;
import org.geomajas.configuration.SymbolInfo;
import org.geomajas.configuration.client.ScaleInfo;
import org.geomajas.geometry.Coordinate;
import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.Geomajas;
import org.geomajas.gwt.client.gfx.paintable.GfxGeometry;
import org.geomajas.gwt.client.gfx.style.ShapeStyle;
import org.geomajas.gwt.client.map.MapView;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.LazyLoadCallback;
import org.geomajas.gwt.client.map.layer.ClientWmsLayer;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.map.layer.configuration.ClientWmsLayerInfo;
import org.geomajas.gwt.client.map.store.VectorLayerStore;
import org.geomajas.gwt.client.spatial.Bbox;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.MultiPoint;
import org.geomajas.gwt.client.spatial.geometry.Point;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.Toolbar;
import org.geomajas.gwt2.client.map.MapConfigurationImpl;
import org.geomajas.gwt2.client.map.layer.tile.TileConfiguration;
import org.geomajas.gwt2.plugin.wms.client.WmsClient;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsGetCapabilitiesInfo;
import org.geomajas.gwt2.plugin.wms.client.capabilities.WmsLayerInfo;
import org.geomajas.gwt2.plugin.wms.client.layer.WmsLayerConfiguration;
import org.geomajas.gwt2.plugin.wms.client.layer.WmsServiceVendor;
import org.geomajas.gwt2.plugin.wms.client.service.WmsService.WmsRequest;
import org.geomajas.gwt2.plugin.wms.client.service.WmsService.WmsUrlTransformer;
import org.geomajas.gwt2.plugin.wms.client.service.WmsService.WmsVersion;
import org.geomajas.widget.layer.configuration.client.ClientAbstractNodeInfo;
import org.geomajas.widget.layer.configuration.client.ClientBranchNodeInfo;
import org.geomajas.widget.layer.configuration.client.ClientExtraLayerInfo;
import org.geomajas.widget.layer.configuration.client.ClientLayerNodeInfo;
import org.geomajas.widget.layer.configuration.client.ClientLayerTreeInfo;
import org.ktunaxa.referral.client.i18n.LocalizedMessages;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedEvent;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedHandler;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.security.UserContextChangedEvent;
import org.ktunaxa.referral.client.security.UserContextChangedHandler;
import org.ktunaxa.referral.client.widget.CommunicationHandler;
import org.ktunaxa.referral.client.widget.ResizableLeftLayout;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
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

	private static final double METER_PER_INCH = 0.0254;

	/**
	 * Focus options after refreshing the referral.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	enum Focus {
		REFERRAL_DETAIL, // focus on referral tab /detail
		REFERRAL_TASK, // focus on referral tab/taks
		TASKMANAGER // focus on Task manager tab
	}

	private static final MapLayout INSTANCE = new MapLayout();

	private static final String STYLE_BLOCK = "block";

	private static final LocalizedMessages MESSAGES = GWT.create(LocalizedMessages.class);

	private TopBar topBar;

	private MenuBar menuBar;

	private VLayout bodyLayout;

	private MapWidget mapWidget;

	private ResizableLeftLayout infoPane;

	private LayersPanel layerPanel;

	private ReferralPanel referralPanel;

	private TaskManagerPanel taskManagerPanel;

	private TaskDto currentTask;

	private ToolStripButton referralButton;

	private ToolStripButton newButton;

	private org.geomajas.layer.feature.Feature currentReferral;

	private VectorLayer referralLayer;

	private HandlerManager handlerManager;

	private SearchPanel searchPanel;
	
	private WmsLayerConfiguration rootLayerConfiguration;

	public static MapLayout getInstance() {
		return INSTANCE;
	}

	private MapLayout() {
		super();
		WmsClient.getInstance().getWmsService().setWmsUrlTransformer(new WmsUrlTransformer() {

			@Override
			public String transform(WmsRequest request, String url) {
				if (request == WmsRequest.GETMAP) {
					return url + "&tiled=true";
				} else {
					return url;
				}
			}

		});
		handlerManager = new HandlerManager(this);
		UserContext.getInstance().addUserContextChangedHandler(new UserContextChangedHandler() {

			public void onChanged(UserContextChangedEvent event) {
				// update GUI to reflect user rights
				updateRights();
			}
		});

		setWidth100();
		setHeight100();
		setOverflow(Overflow.HIDDEN);

		// the info pane
		infoPane = new ResizableLeftLayout();
		infoPane.setStyleName(STYLE_BLOCK);

		// the map
		mapWidget = new MapWidget(KtunaxaConstant.MAP_MAIN, KtunaxaConstant.APPLICATION);
		mapWidget.getMapModel().runWhenInitialized(new Runnable() {

			@Override
			public void run() {
				addClientLayers();

			}

		});

		// add layers, referral, GIS panel
		layerPanel = new LayersPanel(mapWidget);
		infoPane.addCard(layerPanel.getName(), "Manage layers", layerPanel);
		referralPanel = new ReferralPanel();
		infoPane.addCard(referralPanel.getName(), "Manage referral", referralPanel);
		referralButton = getLastButton();
		referralButton.setDisabled(true); // no referral at start
		searchPanel = new SearchPanel(this);
		infoPane.addCard(searchPanel.getName(), "Search", searchPanel);
		// top bar
		topBar = new TopBar();
		mapWidget.getMapModel().runWhenInitialized(new Runnable() {

			public void run() {
				referralLayer = (VectorLayer) mapWidget.getMapModel().getLayer(KtunaxaConstant.LAYER_REFERRAL_ID);
				topBar.update();
			}
		});
		// menu bar
		menuBar = new MenuBar();
		for (ToolStripButton button : infoPane.getButtons()) {
			menuBar.addNavigationButton(button);
		}
		newButton = new ToolStripButton("NEW");
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
		toolbar.setButtonSize(WidgetLayout.toolbarLargeButtonSize);
		toolbar.setBorder("none");

		mapLayout.addMember(toolbar);
		mapWidget.setOverflow(Overflow.HIDDEN);
		mapLayout.addMember(mapWidget);

		addMember(topBar);
		addMember(menuBar);
		VLayout subHeader = new VLayout();
		subHeader.setSize("100%", "8px");
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

	protected void addClientLayers() {
		// assuming collocated geoserver !!!
		final String url = Geomajas.getDispatcherUrl().replace("/map/d", "/geoserver/ows");
//		final String url = "http://127.0.0.1:8888/d/proxy?url=http://64.141.44.215:9090/geoserver/ows";
		final Callback<Void, Void> callback = CommunicationHandler.get().startAction("Loading layers...", false);
		WmsClient.getInstance().getWmsService()
				.getCapabilities(url, WmsVersion.V1_3_0, new Callback<WmsGetCapabilitiesInfo, String>() {

					@Override
					public void onSuccess(WmsGetCapabilitiesInfo result) {
						ClientLayerTreeInfo tree = (ClientLayerTreeInfo) mapWidget.getMapModel().getMapInfo()
								.getWidgetInfo(ClientLayerTreeInfo.IDENTIFIER);
						List<ClientWmsLayerInfo> allLayers = new ArrayList<ClientWmsLayerInfo>();
						rootLayerConfiguration = WmsClient.getInstance().createLayerConfig(result.getRootLayer(),
								url, WmsVersion.V1_3_0);
						rootLayerConfiguration.setCrs(KtunaxaConstant.MAP_CRS);
						rootLayerConfiguration.setWmsServiceVendor(WmsServiceVendor.GEOSERVER_WMS);

						for (WmsLayerInfo layer : result.getRootLayer().getLayers()) {
							if(layer.getLayers().size() > 0) {
								ClientAbstractNodeInfo branch  = parseRecursively(allLayers, layer);								
								tree.getTreeNode().getTreeNodes().add(branch);
							}						
						}
//						Collections.reverse(allLayers);
						for (ClientWmsLayerInfo clientWmsLayerInfo : allLayers) {
							mapWidget.getMapModel().addLayer(clientWmsLayerInfo);
						}
						getLayerPanel().getTree().refresh();
						callback.onSuccess(null);
					}

					@Override
					public void onFailure(String reason) {
						SC.say("Could not reach geoserver on "+url);
						callback.onSuccess(null);
					}
				});

	}
	
	protected ClientAbstractNodeInfo parseRecursively(List<ClientWmsLayerInfo> allLayers, WmsLayerInfo layer) {
		final String url = Geomajas.getDispatcherUrl().replace("/map/d", "/geoserver/ows");
//		final String url = "http://127.0.0.1:8888/d/proxy?url=http://64.141.44.215:9090/geoserver/ows";
		ClientLayerNodeInfo node = null;
		ClientBranchNodeInfo branch =null;
		if(layer.getName() != null) {
			// create info and add layer
			WmsLayerConfiguration wmsConfig = WmsClient.getInstance().createLayerConfig(layer,
					url, WmsVersion.V1_3_0);
			wmsConfig.setCrs(KtunaxaConstant.MAP_CRS);

			TileConfiguration tileConfig = new TileConfiguration(512, 512, new Coordinate(
					-20037508.343, -20037508.343), mapWidget.getMapModel().getMapView()
					.getResolutions());

			ClientWmsLayer wmsLayer = new ClientWmsLayer(layer.getName(), mapWidget.getMapModel()
					.getMapInfo().getCrs(), wmsConfig, tileConfig, layer);
			ClientWmsLayerInfo wmsLayerInfo = new ClientWmsLayerInfo(wmsLayer);
			wmsLayerInfo.setMaximumScale(new ScaleInfo(1/wmsConfig.getMinimumResolution()));
			wmsLayerInfo.setMinimumScale(new ScaleInfo(1/wmsConfig.getMaximumResolution()));
			ClientExtraLayerInfo extra = new ClientExtraLayerInfo();
			extra.setLegendUrl(wmsLayer.getLegendImageUrl());
			extra.setLegendUrlTitle("Legend");
			wmsLayerInfo.getWidgetInfo().put(ClientExtraLayerInfo.IDENTIFIER, extra);
			wmsLayerInfo.setVisible(false);
			allLayers.add(wmsLayerInfo);
			node = new ClientLayerNodeInfo();
			node.setLayerId(wmsLayerInfo.getId());
		}
		if(layer.getLayers().size() > 0) {
			branch = new ClientBranchNodeInfo();
			branch.setLabel(layer.getTitle());
			if(node != null) {
				branch.getTreeNodes().add(node);
			}
			for (WmsLayerInfo info : layer.getLayers()) {
				ClientAbstractNodeInfo child = parseRecursively(allLayers ,info);
				branch.getTreeNodes().add(child);
			}
			return branch;
		}
		return node;
	}
	
	public WmsLayerConfiguration getRootLayerConfiguration() {
		return rootLayerConfiguration;
	}

	public double toResolution(double scaleDenominator) {
		double pixelsPerUnit =  METER_PER_INCH / MapConfigurationImpl.DEFAULT_DPI;
		return pixelsPerUnit * scaleDenominator;
	}


	protected void updateRights() {
		if (!(UserContext.getInstance().isGuest() || UserContext.getInstance().isDataEntry())) {
			if (taskManagerPanel == null) {
				taskManagerPanel = new TaskManagerPanel();
				infoPane.addCard(taskManagerPanel.getName(), "Referral process", taskManagerPanel);
				menuBar.addNavigationButton(getLastButton());
			}
		}
		newButton.setVisible(!UserContext.getInstance().isGuest());
	}

	public MapWidget getMap() {
		return mapWidget;
	}

	public TopBar getTopBar() {
		return topBar;
	}

	public LayersPanel getLayerPanel() {
		return layerPanel;
	}

	public List<String> getSelectedReferrals() {
		return searchPanel.getSelectedReferrals();
	}

	/**
	 * Get the current task.
	 * 
	 * @return current task
	 */
	public TaskDto getCurrentTask() {
		return currentTask;
	}

	/**
	 * Get the current referral.
	 * 
	 * @return current referral
	 */
	public org.geomajas.layer.feature.Feature getCurrentReferral() {
		return currentReferral;
	}

	/**
	 * Set the current referral and current task.
	 * 
	 * @param referral referral to select
	 * @param task task to select
	 */
	public void setReferralAndTask(@Nullable org.geomajas.layer.feature.Feature referral, @Nullable TaskDto task) {
		currentReferral = referral;
		currentTask = task;
		String title;
		if (null != referral) {
			Feature feature = new Feature(referral, referralLayer);
			referralPanel.init(referralLayer, feature);
			Geometry geometry = feature.getGeometry();
			if (null != geometry && !geometry.isEmpty()) {
				Bbox bounds = new Bbox(geometry.getBounds());
				if (geometry instanceof MultiPoint || geometry instanceof Point) {
					// Zoom to 1/23000!
					getMap().getMapModel().getMapView().setCenterPosition(bounds.getCenterPoint());
					getMap().getMapModel().getMapView()
							.setCurrentScale(0.10466131637343458, MapView.ZoomOption.LEVEL_CLOSEST);
				} else {
					// Now display feature on this page!
					getMap().getMapModel().getMapView().applyBounds(bounds, MapView.ZoomOption.LEVEL_FIT);
				}
			}
			// highlight the feature
			SymbolInfo symbolInfo = null;
			if (feature.getStyleId() != null) {
				for (FeatureStyleInfo style : feature.getLayer().getLayerInfo().getNamedStyleInfo().getFeatureStyles()) {
					if (feature.getStyleId().equals(style.getStyleId())) {
						symbolInfo = style.getSymbol();
						break;
					}
				}
			}
			GfxGeometry highlight = new GfxGeometry("referral-highlight", geometry, new ShapeStyle("#FF00FF", 0.5f,
					"#FF00FF", 0.8f, 1), symbolInfo);
			getMap().unregisterWorldPaintable(highlight);
			getMap().registerWorldPaintable(highlight);
			String referralDescription = feature.getAttributeValue(KtunaxaConstant.ATTRIBUTE_PROJECT).toString();
			String referralId = ReferralUtil.createId(referral);
			if (null != task) {
				String taskDescription = task.getDescription();
				title = MESSAGES.referralAndTaskTitle(referralId, referralDescription, taskDescription);
				focusCurrentTask();
			} else {
				title = MESSAGES.referralTitle(referralId, referralDescription);
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
		handlerManager.fireEvent(new CurrentReferralChangedEvent(referral, task));
	}

	/**
	 * Refresh the referral, optionally clearing the task.
	 * 
	 * @param clearTask
	 */
	public void refreshReferral(final boolean clearTask) {
		refreshReferral(clearTask, Focus.REFERRAL_TASK);
	}

	/**
	 * Refresh the referral, optionally clearing the task and focussing on tasks.
	 * 
	 * @param clearTask
	 */
	public void refreshReferral(final boolean clearTask, final Focus focus) {
		if (currentReferral != null) {
			final Window window = CommunicationHandler.get().createWindow("Reloading referral...", true);
			VectorLayerStore store = mapWidget.getMapModel().getVectorLayer(KtunaxaConstant.LAYER_REFERRAL_ID)
					.getFeatureStore();
			store.removeFeature(currentReferral.getId());
			store.getFeature(currentReferral.getId(), GeomajasConstant.FEATURE_INCLUDE_ALL, new LazyLoadCallback() {

				public void execute(List<org.geomajas.gwt.client.map.feature.Feature> response) {
					if (response.size() > 0) {
						setReferralAndTask(response.get(0).toDto(), clearTask ? null : currentTask);
						switch (focus) {
							case REFERRAL_DETAIL:
								focusReferralDetail();
								break;
							case REFERRAL_TASK:
								focusReferralTask();
								break;
							case TASKMANAGER:
								focusTaskManager();
								break;

						}
					}
					window.destroy();
				}
			});
		}
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
		infoPane.showCard(ReferralPanel.NAME);
		referralPanel.focusCurrentTask();
	}

	/**
	 * Put the focus on the task manager.
	 */
	public void focusTaskManager() {
		infoPane.showCard(taskManagerPanel.getName());
	}

	/**
	 * Put the focus on the current referral detail.
	 */
	public void focusReferralDetail() {
		infoPane.showCard(ReferralPanel.NAME);
		referralPanel.focusDetail();
	}

	/**
	 * Put the focus on the current task.
	 */
	public void focusReferralTask() {
		infoPane.showCard(ReferralPanel.NAME);
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

	/** Create a new referral. */
	public void createReferral() {
		final String title = topBar.getLeftTitle();
		final VLayout haze = new VLayout();
		haze.setSize(menuBar.getWidthAsString(), menuBar.getHeightAsString());
		haze.setBackgroundColor("#EEEEEE");
		haze.setOpacity(60);

		final VLayout wizardBody = new VLayout();
		wizardBody.setMargin(LayoutConstant.MARGIN_SMALL);
		ReferralCreationWizard wizard = new ReferralCreationWizard(new Runnable() {

			// resets the main window after completing/closing the wizard.
			public void run() {
				bodyLayout.removeChild(wizardBody);
				bodyLayout.getChildren()[0].setVisible(true);
				wizardBody.destroy();
				menuBar.removeChild(haze);
				topBar.setLeft(title);
				MapLayout.getInstance().refreshReferral(false, Focus.REFERRAL_DETAIL);
			}
		});
		wizardBody.addMember((ReferralCreationWizard.ReferralWizardView) wizard.getView());
		wizard.init();
		bodyLayout.addMember(wizardBody);
		bodyLayout.getChildren()[0].setVisible(false);

		// Disable all buttons in the toolbar:
		menuBar.addChild(haze);
		topBar.setLeftTitle("Referral Management System - Referral Creation Wizard");
	}

	public VectorLayer getReferralLayer() {
		return referralLayer;
	}

	public void refreshSearch() {
		searchPanel.refreshSearch();
	}

}