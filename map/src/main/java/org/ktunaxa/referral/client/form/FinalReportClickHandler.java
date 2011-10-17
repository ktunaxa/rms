/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import org.geomajas.configuration.client.ClientMapInfo;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.plugin.rasterizing.client.image.ImageUrlService;
import org.geomajas.plugin.rasterizing.client.image.ImageUrlServiceImpl;
import org.geomajas.plugin.reporting.command.dto.PrepareReportingRequest;
import org.geomajas.plugin.reporting.command.dto.PrepareReportingResponse;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

/**
 * Click handler to display (a preview of) the final report in a separate tab/window.
 *
 * @author Joachim Van der Auwera
 */
public class FinalReportClickHandler implements ClickHandler {

	private static final String REPORT_NAME = "finalReport";
	private static final String FORMAT = "pdf";

	private final ImageUrlService imageUrlService = new ImageUrlServiceImpl();

	public void onClick(ClickEvent event) {
		MapWidget mapWidget = MapLayout.getInstance().getMap();

		VectorLayer layer = mapWidget.getMapModel().getVectorLayer(KtunaxaConstant.LAYER_REFERRAL_ID);

		PrepareReportingRequest request = new PrepareReportingRequest();
		request.setImageWidth(500);
		request.setImageHeight(500);
		request.setLegendWidth(200);
		request.setLegendHeight(500);
		request.setMinimumGeometrySize(500.0); // make geometry at least 500M wide/high
		request.setMargin(200);
		request.setDpi(300);
		imageUrlService.makeRasterizable(mapWidget);

		ClientMapInfo mapInfo = mapWidget.getMapModel().getMapInfo();
		request.setClientMapInfo(mapInfo);
		request.setLayerId(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID);
		request.setFilter(layer.getFilter());
		request.setFeatureIds(new String[] {MapLayout.getInstance().getCurrentReferral().getId()});
		GwtCommand command = new GwtCommand(PrepareReportingRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new ReportCallback());
	}

	/**
	 * Callback to open report.
	 *
	 * @author Joachim Van der Auwera
	 */
	public class ReportCallback extends AbstractCommandCallback<PrepareReportingResponse> {

		public void execute(PrepareReportingResponse response) {
			String url = GWT.getHostPageBaseURL() + "d/reporting/c/" + KtunaxaConstant.LAYER_REFERRAL_SERVER_ID + "/" +
					REPORT_NAME + "." + FORMAT + "?key=" + response.getKey();
			com.google.gwt.user.client.Window.open(url, "_blank", null);
		}
	}

}
