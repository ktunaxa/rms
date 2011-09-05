/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client;

import org.geomajas.gwt.client.action.ToolCreator;
import org.geomajas.gwt.client.action.ToolbarBaseAction;
import org.geomajas.gwt.client.action.toolbar.ToolbarRegistry;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.DataSourceFieldFactory;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.FormItemFactory;
import org.geomajas.widget.searchandfilter.client.util.GsfLayout;
import org.ktunaxa.referral.client.action.ZoomCurrentReferralModalAction;
import org.ktunaxa.referral.client.action.ZoomKtunaxaTerritoryModalAction;
import org.ktunaxa.referral.client.gui.DocumentItem;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.layer.ReferenceLayer;
import org.ktunaxa.referral.client.widget.ReferralMapWidget;
import org.ktunaxa.referral.client.widget.ReferralMapWidget.MapCallback;
import org.ktunaxa.referral.server.command.dto.GetReferralMapResponse;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

/**
 * Entry point and main class for GWT application. This class defines the layout and functionality of this application.
 * 
 * @author Pieter De Graef
 */
public class KtunaxaEntryPoint implements EntryPoint {

	public static final String TOOL_ZOOM_KTUNAXA_TERRITORY = "ZoomKtunaxaTerritory";
	public static final String TOOL_ZOOM_CURRENT_REFERRAL = "ZoomCurrentReferral";

	public void onModuleLoad() {
		// force a fixed height to feature attribute windows, preventing them to become too big (and add scroll bars)
		WidgetLayout.featureAttributeWindowHeight =
				Integer.toString( Window.getClientHeight() - WidgetLayout.windowOffset * 4 );
		WidgetLayout.featureAttributeWindowWidth = "470";
		GsfLayout.geometricSearchPanelTabWidth = "100%";

		ToolbarRegistry.put(TOOL_ZOOM_KTUNAXA_TERRITORY, new ToolCreator() {

			public ToolbarBaseAction createTool(MapWidget mapWidget) {
				return new ZoomKtunaxaTerritoryModalAction(mapWidget);
			}
		});
		ToolbarRegistry.put(TOOL_ZOOM_CURRENT_REFERRAL, new ToolCreator() {

			public ToolbarBaseAction createTool(MapWidget mapWidget) {
				return new ZoomCurrentReferralModalAction(mapWidget);
			}
		});

		// Register custom text area item
		registerTextAreaFormItem();
		// Register custom item for uploading documents
		registerDocumentIdFormItem();

		// Determine the layout:
		String createReferralParam = Window.Location.getParameter(KtunaxaConstant.CREATE_REFERRAL_URL_PARAMETER);

		MapLayout mapLayout = MapLayout.getInstance();
		ReferralMapWidget map = mapLayout.getMap();
		// initialize referral on callback
		map.addMapCallback(new ReferralInitializer());
		// initialize layers on callback
		map.addMapCallback(new LayerInitializer());
		// set default state

		mapLayout.draw();
		if (null != createReferralParam) {
			mapLayout.createReferral();
		}
	}

	private void registerTextAreaFormItem() {
		// Register a custom form item for text area's:
		AttributeFormFieldRegistry.registerCustomFormItem("textArea", new DataSourceFieldFactory() {

			public DataSourceField create() {
				return new DataSourceTextField();
			}
		}, new FormItemFactory() {

			public FormItem create() {
				return new TextAreaItem();
			}
		}, null);
	}
	
	private void registerDocumentIdFormItem() {
		// Register a custom form item for text area's:
		AttributeFormFieldRegistry.registerCustomFormItem("DOCUMENT_ID_TYPE", new DataSourceFieldFactory() {

			public DataSourceField create() {
				return new DataSourceTextField();
			}
		}, new FormItemFactory() {

			public FormItem create() {
				return new DocumentItem();
			}
		}, null);
	}
	
	/**
	 * Initializes the referral state: map navigation + title.
	 * 
	 * @author Jan De Moerloose
	 */
	private static class ReferralInitializer implements MapCallback {

		public void onResponse(GetReferralMapResponse response) {
			MapLayout mapLayout = MapLayout.getInstance();
			mapLayout.setReferralAndTask(response.getReferral(), response.getTask());
		}
	}

	/**
	 * Initializes the layer tree.
	 * 
	 * @author Jan De Moerloose
	 */
	private static class LayerInitializer implements MapCallback {

		public void onResponse(GetReferralMapResponse response) {
			MapLayout mapLayout = MapLayout.getInstance();
			VectorLayer base = (VectorLayer) mapLayout.getMap().getMapModel()
					.getLayer(KtunaxaConstant.LAYER_REFERENCE_BASE_ID);
			VectorLayer value = (VectorLayer) mapLayout.getMap().getMapModel()
			.getLayer(KtunaxaConstant.LAYER_REFERENCE_VALUE_ID);
			ReferenceLayer baseLayer = new ReferenceLayer(base, response.getLayers(), response.getLayerTypes(), true);
			ReferenceLayer valueLayer = new ReferenceLayer(value, response.getLayers(), 
					response.getLayerTypes(), false);
			mapLayout.getLayerPanel().setBaseLayer(baseLayer);
			mapLayout.getLayerPanel().setValueLayer(valueLayer);
		}
	}

}