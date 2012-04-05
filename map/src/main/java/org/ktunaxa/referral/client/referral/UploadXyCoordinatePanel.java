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
package org.ktunaxa.referral.client.referral;

import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.TransformGeometryRequest;
import org.geomajas.command.dto.TransformGeometryResponse;
import org.geomajas.geometry.Coordinate;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.CommandExceptionCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.spatial.geometry.GeometryFactory;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.ktunaxa.referral.client.gui.LayoutConstant;
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
 */
public class UploadXyCoordinatePanel extends VLayout implements UploadGeometryPanel {

	public static final String NAME = "UploadXyCoordinatePanel";

	private DynamicForm form;

	private FloatItem xItem;

	private FloatItem yItem;

	private Feature feature;

	private Img busyImg;

	private HTMLFlow errorFlow;

	private GeometryFactory factory = new GeometryFactory(KtunaxaConstant.LAYER_SRID, 2);

	private HandlerManager handlerManager = new HandlerManager(this);

	private Geometry geometry;

	/**
	 * Construct panel to select point geometry as coordinates.
	 */
	public UploadXyCoordinatePanel() {
		setLayoutAlign(Alignment.CENTER);
		form = new DynamicForm();

		xItem = new FloatItem("x");
		xItem.setTitle("X");
		xItem.setHint("<nobr>x-coordinate (NAD83/UTM zone 11N)</nobr>");
		xItem.setRequired(true);
		xItem.setRequiredMessage("Enter coordinate between "
				+ KtunaxaConstant.KTUNAXA_TERRITORY_MIN_X + " and "
				+ KtunaxaConstant.KTUNAXA_TERRITORY_MAX_X + ".");

		yItem = new FloatItem("y");
		yItem.setTitle("Y");
		yItem.setHint("<nobr>y-coordinate (NAD83/UTM zone 11N)</nobr>");
		yItem.setRequired(true);
		yItem.setRequiredMessage("Enter coordinate between "
				+ KtunaxaConstant.KTUNAXA_TERRITORY_MIN_Y + " and "
				+ KtunaxaConstant.KTUNAXA_TERRITORY_MAX_Y + ".");

		DataSource dataSource = new DataSource();
		DataSourceFloatField xField = new DataSourceFloatField("x");
		FloatRangeValidator xValidator = new FloatRangeValidator();
		xValidator.setMin((float) KtunaxaConstant.KTUNAXA_TERRITORY_MIN_X);
		xValidator.setMax((float) KtunaxaConstant.KTUNAXA_TERRITORY_MAX_X);
		xField.setValidators(xValidator);

		DataSourceFloatField yField = new DataSourceFloatField("y");
		FloatRangeValidator yValidator = new FloatRangeValidator();
		yValidator.setMin((float) KtunaxaConstant.KTUNAXA_TERRITORY_MIN_Y);
		yValidator.setMax((float) KtunaxaConstant.KTUNAXA_TERRITORY_MAX_Y);
		yField.setValidators(yValidator);

		dataSource.addField(xField);
		dataSource.addField(yField);
		form.setDataSource(dataSource);
		form.setFields(xItem, yItem);

		busyImg = new Img(LayoutConstant.LOADING_IMAGE, 16, 16);
		busyImg.setVisible(false);

		IButton applyButton = new IButton("Apply");

		HLayout buttonPanel = new HLayout(LayoutConstant.MARGIN_LARGE);
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

	/** {@inheritDoc} */
	public void setFeature(Feature feature) {
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
	 * Starts server-side transformation of point.
	 *
	 * @author Jan De Moerloose
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
	 */
	class GeometryHandler implements CommandCallback<TransformGeometryResponse>, CommandExceptionCallback {

		public void execute(TransformGeometryResponse response) {
			busyImg.setVisible(false);
			if (response.getGeometry() != null) {
				if (feature != null) {
					geometry = GeometryConverter.toGwt(response.getGeometry());
					feature.setGeometry(geometry);
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

}
