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
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.DataSourceFieldFactory;
import org.geomajas.gwt.client.widget.attribute.AttributeFormFieldRegistry.FormItemFactory;
import org.ktunaxa.referral.client.i18n.LocalizedMessages;
import org.ktunaxa.referral.server.command.request.GetUrlsResponse;

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

	private static final String RFA_ID = "MIN001";

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
		String createReferralParam = Window.Location.getParameter("createReferral");
		String searchReferralParam = Window.Location.getParameter("searchReferral");
		String referralParam = Window.Location.getParameter("r");
		String bpmParam = Window.Location.getParameter("bpm");

		if (createReferralParam != null) {
			layout.addMember(createHeader("Referral Management System - Create referral", null));
			layout.addMember(new CreateReferralLayout());
		} else if (searchReferralParam != null) {
			layout.addMember(createHeader("Referral Management System - Search referral", null));
			layout.addMember(new SearchReferralLayout());
		} else if (referralParam != null) {
			layout.addMember(createHeader(messages.applicationTitle(RFA_ID, RFA_TITLE), RFA_DESCRIPTION));
			layout.addMember(new MapLayout(referralParam, bpmParam));
		} else {
			layout.addMember(createHeader("Referral Management System - Mapping Dashboard", null));
			layout.addMember(new MapLayout(null, null));
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
	}

	private Canvas createHeader(String title, String tooltip) {
		HLayout header = new HLayout();
		header.setSize("100%", "44");
		header.setStyleName("header");

		HTMLFlow rfaLabel = new HTMLFlow(title);
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
			Window.Location.assign(mapDashboardBaseUrl);
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
			Window.Location.assign(bpmDashboardBaseUrl);
		}
	}

	/**
	 * Create referral handler.
	 */
	private static class CreateReferralClickHandler implements com.smartgwt.client.widgets.menu.events.ClickHandler {

		private String mapDashboardBaseUrl;

		public CreateReferralClickHandler(String mapDashboardBaseUrl) {
			this.mapDashboardBaseUrl = mapDashboardBaseUrl;
		}

		public void onClick(MenuItemClickEvent event) {
			if (mapDashboardBaseUrl.indexOf('?') > 0) {
				Window.Location.assign(mapDashboardBaseUrl + "&createReferral");
			} else {
				Window.Location.assign(mapDashboardBaseUrl + "?createReferral");
			}
		}
	}

	/**
	 * Open referral handler.
	 */
	private static class OpenReferralClickHandler implements com.smartgwt.client.widgets.menu.events.ClickHandler {

		private String mapDashboardBaseUrl;

		public OpenReferralClickHandler(String mapDashboardBaseUrl) {
			this.mapDashboardBaseUrl = mapDashboardBaseUrl;
		}

		public void onClick(MenuItemClickEvent event) {
			if (mapDashboardBaseUrl.indexOf('?') > 0) {
				Window.Location.assign(mapDashboardBaseUrl + "&searchReferral");
			} else {
				Window.Location.assign(mapDashboardBaseUrl + "?searchReferral");
			}
		}
	}
}