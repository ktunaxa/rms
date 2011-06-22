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
import org.geomajas.gwt.client.widget.CardLayout;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.wizard.WizardPage;
import org.ktunaxa.referral.client.referral.event.GeometryUploadHandler;
import org.ktunaxa.referral.client.referral.event.GeometryUploadSuccessEvent;

import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
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

	private static final String WORLD_PAINTABLE_ID = "referral-geometry";

	private VLayout layout;

	private CardLayout uploadLayout;

	private MapWidget mapWidget;

	private GfxGeometry gfxGeometry;

	private Map<String, UploadGeometryPanel> panelMap;

	public AddGeometryPage(MapWidget mapWidget) {
		super();
		this.mapWidget = mapWidget;
		initGui();
	}

	public String getTitle() {
		return "Add geometry";
	}

	public String getExplanation() {
		return "Add a geometry to the referral by one of 3 methods.";
	}

	public Canvas asWidget() {
		return layout;
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
		layout = new VLayout(10);
		uploadLayout = new CardLayout();
		panelMap = new LinkedHashMap<String, UploadGeometryPanel>();
		panelMap.put(UploadShapePanel.NAME, new UploadShapePanel());
		panelMap.put(UploadGeoMarkUrlPanel.NAME, new UploadGeoMarkUrlPanel());
		panelMap.put(UploadXyCoordinatePanel.NAME, new UploadXyCoordinatePanel());
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
		toolStrip.setMembersMargin(5);

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

		HTMLFlow cmd = new HTMLFlow(
				"<div style='text-align:right; line-height:32px; font-size:12px;'>Choose a method:</div>");
		cmd.setSize("120px", "32px");
		toolStrip.addMember(cmd);
		toolStrip.addMember(shapeButton);
		toolStrip.addMember(geoMarkButton);
		toolStrip.addMember(xyButton);

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

		layout.addMember(toolStrip);
		layout.addMember(uploadLayout);
		mapWidget.setZoomOnScrollEnabled(true);
		mapWidget.setBorder("1px solid #C0C0CC");
		mapWidget.setSize("600px", "400px");
		mapWidget.setVisible(true);
		layout.addMember(mapWidget);
	}

	/**
	 * Shows the new geometry on the map.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class ShowGeometryOnMapHandler implements GeometryUploadHandler {

		public void onUploadSuccess(GeometryUploadSuccessEvent event) {
			if (getWizardData().getFeature().isGeometryLoaded()) {
				show();
			}
		}
	}

	@Override
	protected boolean doValidate() {
		return getWizardData().getFeature().isGeometryLoaded();
	}
}