/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.configuration.CircleInfo;
import org.geomajas.configuration.SymbolInfo;
import org.geomajas.gwt.client.gfx.paintable.GfxGeometry;
import org.geomajas.gwt.client.gfx.style.ShapeStyle;
import org.geomajas.gwt.client.map.MapView.ZoomOption;
import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.wizard.WizardPage;
import org.ktunaxa.referral.client.referral.event.FileUploadCompleteEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;
import org.ktunaxa.referral.client.referral.event.FileUploadFailedEvent;
import org.ktunaxa.referral.client.wkt.WktParser;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Second step in the referral creation wizard: Attach a geometry to the referral (by means of uploading a compressed
 * shape file).
 * 
 * @author Pieter De Graef
 */
public class AddGeometryPage extends WizardPage<ReferralData> {

	private static final String WORLD_PAINTABLE_ID = "referral-geometry";

	private VLayout layout;

	private MapWidget mapWidget;

	private GfxGeometry gfxGeometry;

	private Img busyImg;

	private HTMLFlow errorFlow;

	public AddGeometryPage(MapWidget mapWidget) {
		layout = new VLayout(10);
		layout.addMember(createUploadLayout());
		mapWidget.setZoomOnScrollEnabled(false);
		mapWidget.setBorder("1px solid #C0C0CC");
		mapWidget.setSize("600px", "400px");
		mapWidget.setVisible(true);
		layout.addMember(mapWidget);
		this.mapWidget = mapWidget;
	}

	public String getTitle() {
		return "Add geometry";
	}

	public String getExplanation() {
		return "Add a geometry to the referral by uploading a shape file.";
	}

	public boolean doValidate() {
		if (getWizardData().getFeature().isGeometryLoaded()) {
			errorFlow.setVisible(false);
			return true;
		} else {
			errorFlow.setContents("<div style='color: #AA0000'>Please attach a geometry to the referral !</div>");
			errorFlow.setVisible(true);
			return false;
		}
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

	private VLayout createUploadLayout() {
		VLayout uploadLayout = new VLayout();
		uploadLayout.setLayoutAlign(Alignment.CENTER);
		HTMLFlow explanation = new HTMLFlow("<h3>Attach a zipped shape file</h3><div><p>In order to attach a geometry"
				+ " to this referral, you have to provide a shape file (zipped) that contains the project area. Please"
				+ " make sure that the zipped file contains at least the .shp, .prj, .dbf and .shx files.</p>"
				+ "<p>Please note that it may take a while to correctly interpret the provided shape file.</p>"
				+ "</div>");
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(20);
		final FileUploadForm form = new FileUploadForm("Select a file (.zip)", GWT.getModuleBaseURL()
				+ "../d/upload/referral/geometry/");
		form.setHeight(40);

		HLayout btnLayout = new HLayout(10);
		busyImg = new Img("[ISOMORPHIC]/images/loading.gif", 16, 16);
		busyImg.setVisible(false);
		IButton uploadbutton = new IButton("Upload ShapeFile");
		uploadbutton.setAutoFit(true);
		uploadbutton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				form.submit();
				busyImg.setVisible(true);
			}
		});
		btnLayout.addMember(uploadbutton);
		btnLayout.addMember(busyImg);
		errorFlow = new HTMLFlow();
		errorFlow.setHeight100();
		errorFlow.setWidth100();
		errorFlow.setVisible(false);
		btnLayout.addMember(errorFlow);

		form.addFileUploadDoneHandler(new FileUploadDoneHandler() {

			public void onFileUploadComplete(FileUploadCompleteEvent event) {
				busyImg.setVisible(false);
				WktParser wktParser = new WktParser(29611);
				Geometry geometry = wktParser.parse(event.getString(KtunaxaConstant.FORM_GEOMETRY));
				if (geometry != null) {
					errorFlow.setContents("");
					errorFlow.setVisible(false);
					getWizardData().getFeature().setGeometry(geometry);
					show();
				} else {
					errorFlow.setContents("<div style='color: #AA0000'>Geometry not in this UTM zone, "
							+ "did you pick the wrong shape file ?</div>");
					errorFlow.setVisible(true);
				}
			}

			public void onFileUploadFailed(FileUploadFailedEvent event) {
				busyImg.setVisible(false);
				errorFlow.setContents("<div style='color: #AA0000'>" + event.getErrorMessage() + "</div>");
				errorFlow.setVisible(true);
			}
		});

		uploadLayout.addMember(explanation);
		uploadLayout.addMember(spacer);
		uploadLayout.addMember(form);
		uploadLayout.addMember(btnLayout);

		return uploadLayout;
	}

}