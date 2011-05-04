/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client;

import org.geomajas.command.CommandResponse;
import org.geomajas.command.EmptyCommandRequest;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.util.WindowUtil;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.DataSourceFieldFactory;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.FormItemFactory;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.i18n.LocalizedMessages;
import org.ktunaxa.referral.server.command.request.GetTaskRequest;
import org.ktunaxa.referral.server.command.request.GetTaskResponse;
import org.ktunaxa.referral.server.command.request.GetUrlsResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

/**
 * Entry point and main class for GWT application. This class defines the layout and functionality of this application.
 * 
 * @author Pieter De Graef
 */
public class KtunaxaEntryPoint implements EntryPoint {

	private static final String RFA_TITLE = "Mining SoilDigger";

	private static final String RFA_DESCRIPTION = "SoilDigger wants to create a copper mine. "
			+ "It will provide work for 12 permanent and sometimes up to 20 people. "
			+ "Twenty tons will be excavated daily. And I need more text because I want to see it being truncated. "
			+ "Even when the screen is large and the display font for this description is small.";

	private LocalizedMessages messages = GWT.create(LocalizedMessages.class);

	private ToolStripButton mapButton;

	private ToolStripButton bpmButton;

	private MenuItem newItem;

	private MenuItem openItem;
	
	private HTMLFlow rfaLabel;
	
	private MapLayout map;

	public void onModuleLoad() {
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

		VLayout layout = new VLayout();
		layout.setSize("100%", "100%");

		// Determine the layout:
		String createReferralParam = Window.Location.getParameter(KtunaxaConstant.CREATE_REFERRAL_URL_PARAMETER);
		String searchReferralParam = Window.Location.getParameter(KtunaxaConstant.SEARCH_REFERRAL_URL_PARAMETER);
		String referralParam = Window.Location.getParameter(KtunaxaBpmConstant.QUERY_REFERRAL_ID);
		String bpmParam = Window.Location.getParameter(KtunaxaBpmConstant.QUERY_TASK_ID);

		if (createReferralParam != null) {
			layout.addMember(createHeader(KtunaxaConstant.TITLE_CREATE_REFERRAL, null));
			layout.addMember(new CreateReferralLayout());
		} else if (searchReferralParam != null) {
			layout.addMember(createHeader(KtunaxaConstant.TITLE_SEARCH_REFERRAL, null));
			layout.addMember(new SearchReferralLayout());
		} else {
			if (referralParam != null) {
				layout.addMember(createHeader(messages.applicationTitle(referralParam, "View"), null));
			} else {
				layout.addMember(createHeader(KtunaxaConstant.TITLE_GENERAL, null));
			}
			map = new MapLayout(referralParam, bpmParam);
			layout.addMember(map);
		}
		layout.draw();

		// Fetch correct URLs and attach them to the header menu buttons:
		GwtCommand command = new GwtCommand("command.GetUrls");
		command.setCommandRequest(new EmptyCommandRequest());
		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

			public void execute(CommandResponse response) {
				if (response instanceof GetUrlsResponse) {
					GetUrlsResponse gur = (GetUrlsResponse) response;
					mapButton.addClickHandler(new MapClickHandler(gur.getMapDashboardBaseUrl()));
					bpmButton.addClickHandler(new TasksClickHandler(gur.getBpmDashboardBaseUrl()));
					newItem.addClickHandler(new CreateReferralClickHandler(gur.getMapDashboardBaseUrl()));
					openItem.addClickHandler(new OpenReferralClickHandler(gur.getMapDashboardBaseUrl()));
				}
			}
		});
		
		// Fetch the task
		if (bpmParam != null) {
			GwtCommand taskCommand = new GwtCommand(GetTaskRequest.COMMAND);
			GetTaskRequest request = new GetTaskRequest();
			request.setTaskId(bpmParam);
			taskCommand.setCommandRequest(request);
			final String refParam = referralParam;
			GwtCommandDispatcher.getInstance().execute(taskCommand, new CommandCallback() {

				public void execute(CommandResponse response) {
					if (response instanceof GetTaskResponse) {
						GetTaskResponse ftr = (GetTaskResponse) response;
						TaskDto task = ftr.getTask();
						if (task != null) {
							String title = messages.applicationTitle(refParam, task.getName());
							rfaLabel.setContents(title);
							rfaLabel.redraw();
							map.addFinishButton();
						}
					}
				}
			});
		}
		
	}

	private Canvas createHeader(String title, String tooltip) {
		HLayout header = new HLayout();
		header.setSize("100%", "44");
		header.setStyleName("header");

		rfaLabel = new HTMLFlow(title);
		rfaLabel.setStyleName("headerText");
		if (tooltip != null) {
			rfaLabel.setTooltip(tooltip);
			rfaLabel.setHoverWidth(700);
		}
		rfaLabel.setWidth100();
		header.addMember(rfaLabel);

		LayoutSpacer spacer = new LayoutSpacer();
		header.addMember(spacer);

		ToolStrip headerBar = new ToolStrip();
		headerBar.setMembersMargin(2);
		headerBar.setSize("445", "44");
		headerBar.addFill();
		headerBar.setStyleName("headerRight");

		mapButton = new ToolStripButton("Map");
		bpmButton = new ToolStripButton("Tasks");

		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(3);
		newItem = new MenuItem("New", "[ISOMORPHIC]/images/document_plain_new.png");
		openItem = new MenuItem("Open", "[ISOMORPHIC]/images/folder_out.png");
		menu.setItems(newItem, openItem);
		ToolStripMenuButton referralButton = new ToolStripMenuButton("Referral", menu);

		headerBar.addMember(mapButton);
		headerBar.addMember(referralButton);
		headerBar.addMember(bpmButton);
		headerBar.addMember(new ToolStripButton("Logout"));
		headerBar.addSpacer(10);
		header.addMember(headerBar);

		return header;
	}

	/**
	 * Map click handler.
	 */
	private static class MapClickHandler implements ClickHandler {

		private String mapDashboardBaseUrl;

		public MapClickHandler(String mapDashboardBaseUrl) {
			this.mapDashboardBaseUrl = mapDashboardBaseUrl;
		}

		public void onClick(ClickEvent event) {
			WindowUtil.setLocation(mapDashboardBaseUrl);
		}
	}

	/**
	 * Task click handler.
	 */
	private static class TasksClickHandler implements ClickHandler {

		private String bpmDashboardBaseUrl;

		public TasksClickHandler(String bpmDashboardBaseUrl) {
			this.bpmDashboardBaseUrl = bpmDashboardBaseUrl;
		}

		public void onClick(ClickEvent event) {
			WindowUtil.setLocation(bpmDashboardBaseUrl);
		}
	}

	/**
	 * Create referral handler.
	 */
	private class CreateReferralClickHandler implements com.smartgwt.client.widgets.menu.events.ClickHandler {

		private String mapDashboardBaseUrl;

		public CreateReferralClickHandler(String mapDashboardBaseUrl) {
			this.mapDashboardBaseUrl = mapDashboardBaseUrl;
		}

		public void onClick(MenuItemClickEvent event) {
			WindowUtil.setLocation(addUrlParam(mapDashboardBaseUrl, KtunaxaConstant.CREATE_REFERRAL_URL_PARAMETER));
		}
	}

	/**
	 * Open referral handler.
	 */
	private class OpenReferralClickHandler implements com.smartgwt.client.widgets.menu.events.ClickHandler {

		private String mapDashboardBaseUrl;

		public OpenReferralClickHandler(String mapDashboardBaseUrl) {
			this.mapDashboardBaseUrl = mapDashboardBaseUrl;
		}

		public void onClick(MenuItemClickEvent event) {
			WindowUtil.setLocation(addUrlParam(mapDashboardBaseUrl, KtunaxaConstant.SEARCH_REFERRAL_URL_PARAMETER));
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