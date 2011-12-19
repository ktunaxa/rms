/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client;

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
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.DataSourceFieldFactory;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.FormItemFactory;
import org.geomajas.plugin.printing.client.util.PrintingLayout;
import org.geomajas.plugin.staticsecurity.client.StaticSecurityTokenRequestHandler;
import org.geomajas.plugin.staticsecurity.client.util.SsecLayout;
import org.geomajas.widget.searchandfilter.client.util.GsfLayout;
import org.ktunaxa.referral.client.action.ZoomCurrentReferralModalAction;
import org.ktunaxa.referral.client.action.ZoomKtunaxaTerritoryModalAction;
import org.ktunaxa.referral.client.gui.DocumentItem;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.layer.ReferenceLayer;
import org.ktunaxa.referral.client.referral.SelectReferralModalAction;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.server.command.dto.GetReferralMapRequest;
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
				return new SelectReferralModalAction(mapWidget);
			}
		});

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
				GwtCommandDispatcher dispatcher = GwtCommandDispatcher.getInstance();
				dispatcher.execute(commandRequest, new AbstractCommandCallback<GetReferralMapResponse>() {

					public void execute(GetReferralMapResponse response) {
						UserContext.getInstance().setBpmRoles(response.getBpmRoles());
						UserContext.getInstance().setAdmin(response.isAdmin());
						appInfo = response;
						setter.set(applicationId, response.getApplication());
					}

				});
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