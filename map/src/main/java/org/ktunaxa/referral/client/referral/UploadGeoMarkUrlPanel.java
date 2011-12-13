/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral;

import org.geomajas.command.CommandResponse;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.CommandExceptionCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.ktunaxa.referral.client.gui.LayoutConstant;
import org.ktunaxa.referral.client.referral.event.GeometryUploadHandler;
import org.ktunaxa.referral.client.referral.event.GeometryUploadSuccessEvent;
import org.ktunaxa.referral.server.command.dto.GetGeomarkRequest;
import org.ktunaxa.referral.server.command.dto.GetGeomarkResponse;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel to upload the referral geometry via a GeoMark URL.
 * 
 * @author Jan De Moerloose
 */
public class UploadGeoMarkUrlPanel extends VLayout implements UploadGeometryPanel {

	public static final String NAME = "UploadGeoMarkUrlPanel";

	private Img busyImg;

	private HTMLFlow errorFlow;

	private DynamicForm form;

	private TextItem textItem;

	private Feature feature;
	
	private Geometry geometry;

	private HandlerManager handlerManager = new HandlerManager(this);

	/**
	 * Construct panel to upload geometry using GeoMark URL.
	 */
	public UploadGeoMarkUrlPanel() {
		setLayoutAlign(Alignment.CENTER);
		HTMLFlow explanation = new HTMLFlow(
				"<h3>Enter a Geomark URL</h3><div><p>Enter the Geomark id ('gm-uuid') or the complete URL</p>"
						+ "<p>The service will try to download the shape file format. If that does not work,"
						+ " the WKT format will be tried.</p>" + "</div>");
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(20);
		busyImg = new Img(LayoutConstant.LOADING_IMAGE,
				LayoutConstant.LOADING_IMAGE_WIDTH, LayoutConstant.LOADING_IMAGE_HEIGHT);
		busyImg.setVisible(false);

		form = new DynamicForm();

		textItem = new TextItem();
		textItem.setTitle("GeoMark URL");
		textItem.setHint("<nobr>Enter a url</nobr>");
		textItem.setRequired(true);

		form.setItems(textItem);

		IButton downloadButton = new IButton("Download");

		HLayout buttonPanel = new HLayout(LayoutConstant.MARGIN_LARGE);
		buttonPanel.addMember(downloadButton);
		buttonPanel.addMember(busyImg);
		errorFlow = new HTMLFlow();
		errorFlow.setHeight100();
		errorFlow.setWidth100();
		errorFlow.setVisible(false);
		buttonPanel.addMember(errorFlow);

		addMember(explanation);
		addMember(spacer);
		addMember(form);
		addMember(buttonPanel);
		downloadButton.addClickHandler(new DownloadHandler());
	}

	/** {@inheritDoc} */
	public void setFeature(Feature feature) {
		geometry = null;
		this.feature = feature;
	}

	/** {@inheritDoc} */
	public HandlerRegistration addGeometryUploadHandler(GeometryUploadHandler handler) {
		return handlerManager.addHandler(GeometryUploadHandler.TYPE, handler);
	}

	/** {@inheritDoc} */
	public boolean validate() {
		feature.setGeometry(geometry);
		return null != geometry;
	}

	/**
	 * Starts download of geometry from Geomark URL.
	 * 
	 * @author Jan De Moerloose
	 */
	class DownloadHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (form.validate()) {
				GetGeomarkRequest request = new GetGeomarkRequest();
				request.setGeomark(textItem.getValueAsString());
				GwtCommand command = new GwtCommand(GetGeomarkRequest.COMMAND);
				command.setCommandRequest(request);
				GwtCommandDispatcher.getInstance().execute(command, new GeometryHandler());
				busyImg.setVisible(true);
			}
		}
	}

	/**
	 * Handles downloaded geometry from Geomark URL.
	 * 
	 * @author Jan De Moerloose
	 */
	class GeometryHandler implements CommandCallback<GetGeomarkResponse>, CommandExceptionCallback {

		public void execute(GetGeomarkResponse response) {
			busyImg.setVisible(false);
			if (response.getGeometry() != null) {
				Geometry geomarkGeometry = GeometryConverter.toGwt(response.getGeometry());
				errorFlow.setContents("");
				errorFlow.setVisible(false);
				feature.setGeometry(geomarkGeometry);
				geometry = geomarkGeometry;
				handlerManager.fireEvent(new GeometryUploadSuccessEvent());
			} else {
				errorFlow.setContents("<div style='color: #AA0000'>Geometry not in this UTM zone, "
						+ "did you pick the wrong shape file ?</div>");
				errorFlow.setVisible(true);
			}
		}

		public void onCommandException(CommandResponse response) {
			busyImg.setVisible(false);
			errorFlow.setContents("<div style='color: #AA0000'>Invalid id " + response.getErrorMessages().get(0)
					+ "</div>");
			errorFlow.setVisible(true);
		}
	}

}
