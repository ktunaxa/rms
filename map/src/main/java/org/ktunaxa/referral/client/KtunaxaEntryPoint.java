/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client;

import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.util.WindowUtil;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.DataSourceFieldFactory;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.FormItemFactory;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.gui.CreateReferralLayout;
import org.ktunaxa.referral.client.gui.LayersPanel;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.gui.ReferralPanel;
import org.ktunaxa.referral.client.gui.SearchReferralLayout;
import org.ktunaxa.referral.client.i18n.LocalizedMessages;
import org.ktunaxa.referral.client.layer.ReferenceLayer;
import org.ktunaxa.referral.client.widget.ReferralMapWidget;
import org.ktunaxa.referral.client.widget.ReferralMapWidget.MapCallback;
import org.ktunaxa.referral.server.command.dto.GetReferralMapResponse;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point and main class for GWT application. This class defines the layout and functionality of this application.
 * 
 * @author Pieter De Graef
 */
public class KtunaxaEntryPoint implements EntryPoint {

	private LocalizedMessages messages = GWT.create(LocalizedMessages.class);

	private MapLayout mapLayout;

	public void onModuleLoad() {
		// Register custom textarea item
		registerTextAreaFormItem();

		// Determine the layout:
		String createReferralParam = Window.Location.getParameter(KtunaxaConstant.CREATE_REFERRAL_URL_PARAMETER);
		String searchReferralParam = Window.Location.getParameter(KtunaxaConstant.SEARCH_REFERRAL_URL_PARAMETER);
		String referralParam = Window.Location.getParameter(KtunaxaBpmConstant.QUERY_REFERRAL_ID);
		String bpmParam = Window.Location.getParameter(KtunaxaBpmConstant.QUERY_TASK_ID);

		VLayout layout;
		if (createReferralParam != null) {
			layout = new CreateReferralLayout();
		} else if (searchReferralParam != null) {
			layout = new SearchReferralLayout();
		} else {
			mapLayout = new MapLayout(referralParam, bpmParam);
			layout = mapLayout;
			ReferralMapWidget map = mapLayout.getMap();
			// initialize referral on callback
			map.addMapCallback(new ReferralInitializer());
			// initialize links on callback
			map.addMapCallback(new LinkInitializer());
			// initialize layers on callback
			map.addMapCallback(new LayerInitializer());
			// set default state
			if (referralParam != null) {
				// open the referral tab
				mapLayout.getInfoPane().showCard(ReferralPanel.NAME);
			} else {
				// open the referral tab
				mapLayout.getInfoPane().showCard(LayersPanel.NAME);
			}
		}
		layout.draw();

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

	/**
	 * Initializes the links to other pages.
	 * 
	 * @author Jan De Moerloose
	 */
	private class LinkInitializer implements MapCallback {

		public void onResponse(final GetReferralMapResponse response) {
			mapLayout.getNewButton().addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					WindowUtil.setLocation(addUrlParam(response.getMapDashboardBaseUrl(),
							KtunaxaConstant.CREATE_REFERRAL_URL_PARAMETER));
				}
			});
			mapLayout.getTopBar().getTasksButton().addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					WindowUtil.setLocation(response.getBpmDashboardBaseUrl());
				}
			});
		}

	}

	/**
	 * Initializes the referral state: map navigation + title.
	 * 
	 * @author Jan De Moerloose
	 */
	private class ReferralInitializer implements MapCallback {

		public void onResponse(GetReferralMapResponse response) {
			mapLayout.setReferralAndTask(response.getReferral(), response.getTask());
		}
	}

	/**
	 * Initializes the layer tree.
	 * 
	 * @author Jan De Moerloose
	 */
	private class LayerInitializer implements MapCallback {

		public void onResponse(GetReferralMapResponse response) {
			VectorLayer base = (VectorLayer) mapLayout.getMap().getMapModel()
					.getLayer(KtunaxaConstant.REFERENCE_BASE_LAYER_ID);
			VectorLayer value = (VectorLayer) mapLayout.getMap().getMapModel()
			.getLayer(KtunaxaConstant.REFERENCE_VALUE_LAYER_ID);
			ReferenceLayer baseLayer = new ReferenceLayer(base, response.getLayers(), response.getLayerTypes(), true);
			ReferenceLayer valueLayer = new ReferenceLayer(value, response.getLayers(), 
					response.getLayerTypes(), false);
			mapLayout.getLayerPanel().setBaseLayer(baseLayer);
			mapLayout.getLayerPanel().setValueLayer(valueLayer);
		}
	}

	private String addUrlParam(String baseUrl, String param) {
		if (baseUrl.indexOf('?') > 0) {
			return baseUrl + "&" + param;
		} else {
			return baseUrl + "?" + param;
		}
	}
}