/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral;

import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.TransformGeometryRequest;
import org.geomajas.command.dto.TransformGeometryResponse;
import org.geomajas.geometry.Bbox;
import org.geomajas.geometry.Coordinate;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.CommandExceptionCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.GeometryFactory;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.ktunaxa.referral.client.referral.event.GeometryUploadHandler;
import org.ktunaxa.referral.client.referral.event.GeometryUploadSuccessEvent;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel to upload the referral geometry via an x and y coordinate.
 * 
 * @author Jan De Moerloose
 * 
 */
public class UploadXyCoordinatePanel extends VLayout implements UploadGeometryPanel {

	public static final String NAME = "UploadXyCoordinatePanel";

	private DynamicForm form;

	private IButton applyButton;

	private FloatItem xItem;

	private FloatItem yItem;

	private Feature feature;

	private Img busyImg;

	private HTMLFlow errorFlow;

	private GeometryFactory factory = new GeometryFactory(KtunaxaConstant.LAYER_SRID, 2);

	private HandlerManager handlerManager = new HandlerManager(this);

	public UploadXyCoordinatePanel(Bbox bbox) {
		setLayoutAlign(Alignment.CENTER);
		form = new DynamicForm();

		xItem = new FloatItem("x");
		xItem.setTitle("X");
		xItem.setHint("<nobr>x-coordinate (NAD83/UTM zone 11N)</nobr>");

		yItem = new FloatItem("y");
		yItem.setTitle("Y");
		yItem.setHint("<nobr>y-coordinate (NAD83/UTM zone 11N)</nobr>");

		DataSource dataSource = new DataSource();
		DataSourceFloatField xField = new DataSourceFloatField("x");
		FloatRangeValidator xValidator = new FloatRangeValidator();
		xValidator.setMin((float) bbox.getX());
		xValidator.setMax((float) bbox.getMaxX());
		xField.setValidators(xValidator);

		DataSourceFloatField yField = new DataSourceFloatField("y");
		FloatRangeValidator yValidator = new FloatRangeValidator();
		yValidator.setMin((float) bbox.getY());
		yValidator.setMax((float) bbox.getMaxY());
		yField.setValidators(yValidator);

		dataSource.addField(xField);
		dataSource.addField(yField);
		form.setDataSource(dataSource);
		form.setFields(xItem, yItem);

		busyImg = new Img("[ISOMORPHIC]/images/loading.gif", 16, 16);
		busyImg.setVisible(false);

		applyButton = new IButton("Apply");

		HLayout buttonPanel = new HLayout(10);
		buttonPanel.addMember(applyButton);
		buttonPanel.addMember(busyImg);
		errorFlow = new HTMLFlow();
		errorFlow.setHeight100();
		errorFlow.setWidth100();
		errorFlow.setVisible(false);
		buttonPanel.addMember(errorFlow);

		addMember(form);
		addMember(applyButton);

		applyButton.addClickHandler(new TransformHandler());

	}

	/**
	 * Starts server-side transformation of point.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class TransformHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (form.validate()) {
				// x-y conversion to google
				TransformGeometryRequest request = new TransformGeometryRequest();
				Geometry geometry = factory
						.createPoint(new Coordinate(xItem.getValueAsFloat(), yItem.getValueAsFloat()));
				request.setGeometry(GeometryConverter.toDto(geometry));
				request.setSourceCrs(KtunaxaConstant.LAYER_CRS);
				request.setTargetCrs(KtunaxaConstant.MAP_CRS);
				GwtCommand command = new GwtCommand(TransformGeometryRequest.COMMAND);
				command.setCommandRequest(request);
				GwtCommandDispatcher.getInstance().execute(command, new GeometryHandler());
				busyImg.setVisible(true);
			}
		}

	}

	/**
	 * Handles the transformed geometry.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class GeometryHandler implements CommandCallback<TransformGeometryResponse>, CommandExceptionCallback {

		public void execute(TransformGeometryResponse response) {
			busyImg.setVisible(false);
			if (response.getGeometry() != null) {
				if (feature != null) {
					feature.setGeometry(GeometryConverter.toGwt(response.getGeometry()));
					handlerManager.fireEvent(new GeometryUploadSuccessEvent());
				}
			} else {
				errorFlow.setContents("<div style='color: #AA0000'>Geometry not in this UTM zone, "
						+ "did you pick the wrong coordinates ?</div>");
				errorFlow.setVisible(true);
			}
		}

		public void onCommandException(CommandResponse response) {
			busyImg.setVisible(false);
			errorFlow.setContents("<div style='color: #AA0000'>" + response.getErrorMessages().get(0) + "</div>");
			errorFlow.setVisible(true);
		}

	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public HandlerRegistration addGeometryUploadHandler(GeometryUploadHandler handler) {
		return handlerManager.addHandler(GeometryUploadHandler.TYPE, handler);
	}

}
