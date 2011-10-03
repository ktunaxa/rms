/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import java.util.LinkedHashMap;
import java.util.Map;

import org.geomajas.configuration.CircleInfo;
import org.geomajas.configuration.SymbolInfo;
import org.geomajas.gwt.client.gfx.paintable.GfxGeometry;
import org.geomajas.gwt.client.gfx.style.ShapeStyle;
import org.geomajas.gwt.client.map.MapView.ZoomOption;
import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.util.HtmlBuilder;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.widget.utility.gwt.client.widget.CardLayout;
import org.geomajas.widget.utility.gwt.client.wizard.WizardPage;
import org.ktunaxa.referral.client.gui.LayoutConstant;
import org.ktunaxa.referral.client.referral.event.GeometryUploadHandler;
import org.ktunaxa.referral.client.referral.event.GeometryUploadSuccessEvent;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * Second step in the referral creation wizard: Attach a geometry to the referral (by means of uploading a compressed
 * shape file).
 * 
 * @author Pieter De Graef
 */
public class AddGeometryPage extends WizardPage<ReferralData> {

	//TODO place in geomajas.css
	public static final String FLOW_NOTE_STYLE = "font-size:12px; line-height:18px;";
	public static final String FLOW_INVALID_STYLE = "color:#AA0000; " + FLOW_NOTE_STYLE;
	public static final String FLOW_TOOL_STYLE = "text-align:right; line-height:32px; font-size:12px;";

	private static final String WORLD_PAINTABLE_ID = "referral-geometry";

	private VLayout vLayout;
	
	private HLayout hLayout;

	private CardLayout<String> uploadLayout;

	private MapWidget mapWidget;

	private GfxGeometry gfxGeometry;
	
	private HTMLFlow invalidTop;

	private Map<String, UploadGeometryPanel> panelMap;

	private UploadNoGeometryPanel noGeometryPanel;
	
	public AddGeometryPage() {
		super();
		mapWidget = new MapWidget(KtunaxaConstant.MAP_CREATE_REFERRAL, KtunaxaConstant.APPLICATION);
		mapWidget.setVisible(false);
		initGui();
	}

	public String getTitle() {
		return "Add geometry";
	}

	public String getExplanation() {
		return "Add a geometry to the referral by one of 3 methods or choose not to add a geometry.";
	}

	public Canvas asWidget() {
		return vLayout;
	}

	public void clear() {
		if (gfxGeometry != null) {
			mapWidget.unregisterWorldPaintable(gfxGeometry);
		}
	}

	@Override
	protected void setWizardData(ReferralData wizardData) {
		super.setWizardData(wizardData);
		for (UploadGeometryPanel panel : panelMap.values()) {
			panel.setFeature(wizardData.getFeature());
		}
	}

	@Override
	protected void show() {
		if (gfxGeometry != null) {
			mapWidget.unregisterWorldPaintable(gfxGeometry);
		}
		if (getWizardData().getFeature().isGeometryLoaded()) {
			final Geometry geometry = getWizardData().getFeature().getGeometry();
			ShapeStyle style = new ShapeStyle("#3e74b3", 0.75f, "#278ec8", 1.0f, 2);
			gfxGeometry = new GfxGeometry(WORLD_PAINTABLE_ID, geometry, style);
			SymbolInfo symbol = new SymbolInfo();
			CircleInfo circle = new CircleInfo();
			circle.setR(5);
			symbol.setCircle(circle);
			gfxGeometry.setSymbolInfo(symbol);
			mapWidget.setVisible(true);
			mapWidget.registerWorldPaintable(gfxGeometry);
			if (mapWidget.getMapModel().isInitialized()) {
				mapWidget.getMapModel().getMapView().applyBounds(geometry.getBounds(), ZoomOption.LEVEL_FIT);
			} else {
				mapWidget.getMapModel().addMapModelHandler(new MapModelHandler() {

					public void onMapModelChange(MapModelEvent event) {
						mapWidget.getMapModel().getMapView().applyBounds(geometry.getBounds(), ZoomOption.LEVEL_FIT);
					}
				});
			}
		}
	}

	private void initGui() {
		vLayout = new VLayout(LayoutConstant.MARGIN_LARGE);
		hLayout = new HLayout(LayoutConstant.MARGIN_LARGE);
		uploadLayout = new CardLayout<String>();
		invalidTop = createInvalid();
		uploadLayout.addMember(invalidTop);
		panelMap = new LinkedHashMap<String, UploadGeometryPanel>();
		panelMap.put(UploadShapePanel.NAME, new UploadShapePanel());
		panelMap.put(UploadGeoMarkUrlPanel.NAME, new UploadGeoMarkUrlPanel());
		panelMap.put(UploadXyCoordinatePanel.NAME, new UploadXyCoordinatePanel());
		noGeometryPanel = new UploadNoGeometryPanel();
		panelMap.put(UploadNoGeometryPanel.NAME, noGeometryPanel);
		ShowGeometryOnMapHandler handler = new ShowGeometryOnMapHandler();
		for (Map.Entry<String, UploadGeometryPanel> entry : panelMap.entrySet()) {
			entry.getValue().addGeometryUploadHandler(handler);
			uploadLayout.addCard(entry.getKey(), (Canvas) entry.getValue());
		}
		uploadLayout.showCard(UploadShapePanel.NAME);

		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setBackgroundImage("");
		toolStrip.setBorder("none");
		toolStrip.setSize("100%", "32");
		toolStrip.setMembersMargin(LayoutConstant.MARGIN_SMALL);

		ToolStripButton shapeButton = new ToolStripButton("Shape");
		shapeButton.setActionType(SelectionType.RADIO);
		shapeButton.setRadioGroup("geometry");
		shapeButton.setSelected(true);

		ToolStripButton geoMarkButton = new ToolStripButton("Geomark");
		geoMarkButton.setActionType(SelectionType.RADIO);
		geoMarkButton.setRadioGroup("geometry");

		ToolStripButton xyButton = new ToolStripButton("X:Y");
		xyButton.setActionType(SelectionType.RADIO);
		xyButton.setRadioGroup("geometry");
		
		ToolStripButton noneButton = new ToolStripButton("No geometry");
		noneButton.setActionType(SelectionType.RADIO);
		noneButton.setRadioGroup("geometry");

		HTMLFlow cmd = new HTMLFlow(
				HtmlBuilder.divStyle(FLOW_TOOL_STYLE, "Choose a method:"));
		cmd.setSize("120px", "32px");
		toolStrip.addMember(cmd);
		toolStrip.addMember(shapeButton);
		toolStrip.addMember(geoMarkButton);
		toolStrip.addMember(xyButton);
		toolStrip.addMember(noneButton);

		shapeButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				uploadLayout.showCard(UploadShapePanel.NAME);
			}
		});

		geoMarkButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				uploadLayout.showCard(UploadGeoMarkUrlPanel.NAME);
			}
		});

		xyButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				uploadLayout.showCard(UploadXyCoordinatePanel.NAME);
			}
		});
		
		noneButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				uploadLayout.showCard(UploadNoGeometryPanel.NAME);
			}
		});
		uploadLayout.setSize("50%", "100%");
		hLayout.addMember(uploadLayout);
		mapWidget.setZoomOnScrollEnabled(true);
		mapWidget.setBorder("1px solid #C0C0CC");
		mapWidget.setSize("50%", "100%");
		mapWidget.setVisible(true);
		hLayout.setSize("100%", "100%");
		hLayout.addMember(mapWidget);
		
		vLayout.addMember(toolStrip);
		vLayout.addMember(hLayout);
	}

	/**
	 * Shows the new geometry on the map.
	 * 
	 * @author Jan De Moerloose
	 */
	class ShowGeometryOnMapHandler implements GeometryUploadHandler {

		public void onUploadSuccess(GeometryUploadSuccessEvent event) {
			if (getWizardData().getFeature().isGeometryLoaded()) {
				show();
			}
		}
	}
	
	private HTMLFlow createInvalid() {
		HTMLFlow flow = new HTMLFlow(
				HtmlBuilder.divStyle(AddGeometryPage.FLOW_INVALID_STYLE, 
						"Provide a geometry using one of the 3 methods or choose to not add a geometry."));
		flow.setWidth100();
		flow.setVisible(false);
		return flow;
	}
	
	@Override
	protected boolean doValidate() {
		boolean validate = getWizardData().getFeature().isGeometryLoaded();
		if (!validate) {
			validate = noGeometryPanel.validate();
		}
		invalidTop.setVisible(!validate); 
		return validate;
	}
}