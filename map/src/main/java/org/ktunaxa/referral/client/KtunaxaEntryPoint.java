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

package org.ktunaxa.referral.client;

import java.util.LinkedHashMap;

import org.geomajas.gwt.client.action.ToolCreator;
import org.geomajas.gwt.client.action.ToolbarBaseAction;
import org.geomajas.gwt.client.action.toolbar.ToolbarRegistry;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.command.event.TokenChangedEvent;
import org.geomajas.gwt.client.command.event.TokenChangedHandler;
import org.geomajas.gwt.client.map.event.MapModelChangedEvent;
import org.geomajas.gwt.client.map.event.MapModelChangedHandler;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.service.ClientConfigurationLoader;
import org.geomajas.gwt.client.service.ClientConfigurationService;
import org.geomajas.gwt.client.service.ClientConfigurationSetter;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry;
import org.geomajas.gwt.client.widget.attribute.DataSourceFieldFactory;
import org.geomajas.gwt.client.widget.attribute.FormItemFactory;
import org.geomajas.plugin.printing.client.util.PrintingLayout;
import org.geomajas.plugin.staticsecurity.client.StaticSecurityTokenRequestHandler;
import org.geomajas.plugin.staticsecurity.client.util.SsecLayout;
import org.geomajas.widget.searchandfilter.client.util.GsfLayout;
import org.ktunaxa.referral.client.action.ZoomCurrentReferralModalAction;
import org.ktunaxa.referral.client.action.ZoomKtunaxaTerritoryModalAction;
import org.ktunaxa.referral.client.gui.DocumentItem;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.layer.ReferenceLayer;
import org.ktunaxa.referral.client.referral.MakeReferralCurrentModalAction;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.CommunicationHandler;
import org.ktunaxa.referral.server.command.dto.GetReferralMapRequest;
import org.ktunaxa.referral.server.command.dto.GetReferralMapResponse;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
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
	public static final String TOOL_SELECT_REFERRAL = "SelectReferralMode";

	private GetReferralMapResponse appInfo;

	public void onModuleLoad() {
		// force a fixed height to feature attribute windows, preventing them to become too big (and add scroll bars)
		WidgetLayout.featureAttributeWindowHeight =
				Integer.toString(Window.getClientHeight() - WidgetLayout.windowOffset * 4);
		WidgetLayout.featureAttributeWindowWidth = "470";
		GsfLayout.geometricSearchPanelTabWidth = "100%";
		PrintingLayout.templateIncludeLegend = false;

		SsecLayout.tokenRequestWindowLogo = "[ISOMORPHIC]/images/logoKtunaxaReferrals.png";
		SsecLayout.tokenRequestWindowLogoWidth = "300";
		SsecLayout.tokenRequestWindowLogoHeight = "120";
		SsecLayout.tokenRequestWindowWidth = "400";
		SsecLayout.tokenRequestWindowHeight = "240";

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
		ToolbarRegistry.put(TOOL_SELECT_REFERRAL, new ToolCreator() {
			public ToolbarBaseAction createTool(MapWidget mapWidget) {
				return new MakeReferralCurrentModalAction(mapWidget);
			}
		});
		AttributeFormFieldRegistry.registerCustomFormItem("topMiddleBottom", new DataSourceFieldFactory() {
					public DataSourceField create() {
						return new DataSourceIntegerField();
					}
				}, new FormItemFactory() {
					public FormItem create() {
						ComboBoxItem formItem = new ComboBoxItem();
						LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
						values.put("1", "top");
						values.put("2", "middle");
						values.put("3", "bottom");
						formItem.setValueMap(values);
						formItem.setValue(2);
						return formItem;
					}
				}, null
		);

		GwtCommandDispatcher.getInstance().setTokenRequestHandler(new StaticSecurityTokenRequestHandler());

		GwtCommandDispatcher.getInstance().addTokenChangedHandler(new TokenChangedHandler() {
			public void onTokenChanged(TokenChangedEvent event) {
				UserContext.getInstance().set(event.getUserDetail().getUserId(), event.getUserDetail().getUserName());
			}
		});

		ClientConfigurationService.setConfigurationLoader(new ClientConfigurationLoader() {
			public void loadClientApplicationInfo(final String applicationId, final ClientConfigurationSetter setter) {
				GwtCommand commandRequest = new GwtCommand(GetReferralMapRequest.COMMAND);
				commandRequest.setCommandRequest(new GetReferralMapRequest(applicationId));
				CommunicationHandler.get().execute(commandRequest,
						new AbstractCommandCallback<GetReferralMapResponse>() {

					public void execute(GetReferralMapResponse response) {
						UserContext.getInstance().setBpmRoles(response.getBpmRoles());
						UserContext.getInstance().setAdmin(response.isAdmin());
						appInfo = response;
						setter.set(applicationId, response.getApplication());
					}

				}, "Loading RMS...");
			}
		});

		// Register custom text area item
		registerTextAreaFormItem();
		// Register custom item for uploading documents
		registerDocumentIdFormItem();

		// Determine the layout:
		String createReferralParam = Window.Location.getParameter(KtunaxaConstant.CREATE_REFERRAL_URL_PARAMETER);

		MapLayout mapLayout = MapLayout.getInstance();
		mapLayout.getMap().getMapModel().addMapModelChangedHandler(new MapModelChangedHandler() {
			public void onMapModelChanged(MapModelChangedEvent event) {
				// Initializes the referral state: map navigation + title.
				MapLayout mapLayout = MapLayout.getInstance();
				mapLayout.setReferralAndTask(appInfo.getReferral(), appInfo.getTask());

				// Initializes the layer tree.
				VectorLayer base = (VectorLayer) mapLayout.getMap().getMapModel()
						.getLayer(KtunaxaConstant.LAYER_REFERENCE_BASE_ID);
				VectorLayer value = (VectorLayer) mapLayout.getMap().getMapModel()
						.getLayer(KtunaxaConstant.LAYER_REFERENCE_VALUE_ID);
				ReferenceLayer baseLayer =
						new ReferenceLayer(base, appInfo.getLayers(), appInfo.getLayerTypes(), true);
				ReferenceLayer valueLayer = new ReferenceLayer(value, appInfo.getLayers(),
						appInfo.getLayerTypes(), false);
				mapLayout.getLayerPanel().setBaseLayer(baseLayer);
				mapLayout.getLayerPanel().setValueLayer(valueLayer);

			}
		});

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

}