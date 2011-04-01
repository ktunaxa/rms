/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.gfx.paintable.GfxGeometry;
import org.geomajas.gwt.client.gfx.style.ShapeStyle;
import org.geomajas.gwt.client.map.MapView.ZoomOption;
import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.WizardPage;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneEvent;
import org.ktunaxa.referral.client.referral.event.FileUploadDoneHandler;
import org.ktunaxa.referral.client.wkt.WktParser;

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
public class AddGeometryPage implements WizardPage {

	private static final String WORLD_PAINTABLE_ID = "referral-geometry";

	private Feature feature;

	private VLayout layout;

	private MapWidget mapWidget;

	private GfxGeometry gfxGeometry;

	private Img busyImg;

	public AddGeometryPage() {
		layout = new VLayout(10);
		layout.addMember(createUploadLayout());

		mapWidget = new MapWidget("mapTestReferral", "app");
		mapWidget.setZoomOnScrollEnabled(false);
		mapWidget.setBorder("1px solid #C0C0CC");
		mapWidget.setSize("600px", "400px");
		mapWidget.setLayoutAlign(Alignment.CENTER);
		mapWidget.setVisible(false);
		layout.addMember(mapWidget);
	}

	public String getTitle() {
		return "Add geometry";
	}

	public String getExplanation() {
		return "Add a geometry to the referral by uploading a shape file.";
	}

	public boolean validate() {
		if (feature != null) {
			return feature.getGeometry() != null;
		}
		return false;
	}

	public Canvas asWidget() {
		return layout;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
		renderFeature();
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void renderFeature() {
		try {
			if (gfxGeometry != null) {
				mapWidget.unregisterWorldPaintable(gfxGeometry);
			}
			ShapeStyle style = new ShapeStyle("#3e74b3", 0.75f, "#278ec8", 1.0f, 2);
			gfxGeometry = new GfxGeometry(WORLD_PAINTABLE_ID, (Geometry) feature.getGeometry().clone(), style);
			mapWidget.setVisible(true);
			mapWidget.registerWorldPaintable(gfxGeometry);
			if (mapWidget.getMapModel().isInitialized()) {
				mapWidget.getMapModel().getMapView()
						.applyBounds(feature.getGeometry().getBounds(), ZoomOption.LEVEL_FIT);
			} else {
				mapWidget.getMapModel().addMapModelHandler(new MapModelHandler() {

					public void onMapModelChange(MapModelEvent event) {
						mapWidget.getMapModel().getMapView()
								.applyBounds(feature.getGeometry().getBounds(), ZoomOption.LEVEL_FIT);
					}
				});
			}
		} catch (IllegalStateException e) {
			// No geometry available. Let it go....
		}
	}

	private VLayout createUploadLayout() {
		VLayout uploadLayout = new VLayout();
		uploadLayout.setLayoutAlign(Alignment.CENTER);
		// uploadLayout.setWidth("75%");
		// uploadLayout.setStyleName("wizardInnerBody");
		HTMLFlow explanation = new HTMLFlow("<h3>Attach a zipped shape file</h3><div><p>In order to attach a geometry"
				+ " to this referral, you have to provide a shape file (zipped) that contains the project area. Please"
				+ " make sure that the zipped file contains at least the .shp, .prj, .dbf and .shx files.</p>"
				+ "<p>Please note that it may take a while to correctly interpret the provided shape file.</p></div>");
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(20);
		final FileUploadForm form = new FileUploadForm("Select a file (.zip)", "uploadGeometry");
		form.setHeight(40);

		HLayout btnLayout = new HLayout(10);
		busyImg = new Img("[ISOMORPHIC]/images/loading.gif", 16, 16);
		busyImg.setVisible(false);
		IButton uploadbutton = new IButton("Apply ShapeFile");
		uploadbutton.setAutoFit(true);
		uploadbutton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				form.submit();
				busyImg.setVisible(true);
			}
		});
		btnLayout.addMember(uploadbutton);
		btnLayout.addMember(busyImg);

		form.addFileUploadDoneHandler(new FileUploadDoneHandler() {

			public void onFileUploadDone(FileUploadDoneEvent event) {
				busyImg.setVisible(false);
				WktParser wktParser = new WktParser(29611);
				Geometry geometry = wktParser.parse(event.getResponse());
				feature.setGeometry(geometry);
				renderFeature();
			}
		});

		uploadLayout.addMember(explanation);
		uploadLayout.addMember(spacer);
		uploadLayout.addMember(form);
		uploadLayout.addMember(btnLayout);

		return uploadLayout;
	}
}