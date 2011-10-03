/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import javax.validation.constraints.NotNull;

import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.command.event.TokenChangedEvent;
import org.geomajas.gwt.client.command.event.TokenChangedHandler;
import org.geomajas.plugin.staticsecurity.client.util.SsecAccess;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

/**
 * Top menu bar of the general layout. The top bar has a user section (login/logout,status) on the right and a list of
 * links. On the left there is a title.
 * 
 * @author Jan De Moerloose
 * 
 */
public class TopBar extends HLayout {
	
	/**
	 * Menu item titles.
	 */
	private static final String REJECTED = "Rejected";
	private static final String STARTED = "Started";
	private static final String ENGAGEMENT_CHANGED = "Engagement changed";
	private static final String FINISHED = "Finished";
	private static final String LOGOUT = "Logout";
	private static final String ADMIN = "Admin";

	private HTMLFlow leftLabel;

	private ToolStripButton userButton;
	
	private EditEmailWindow emailWindow = new EditEmailWindow();

	/**
	 * Constructs a top bar.
	 */
	public TopBar() {
		super();
		setSize("100%", "44");
		setStyleName("header");

		leftLabel = new HTMLFlow(" ");
		leftLabel.setStyleName("headerText");
		leftLabel.setWidth100();
		addMember(leftLabel);

		LayoutSpacer spacer = new LayoutSpacer();
		addMember(spacer);

		ToolStrip headerBar = new ToolStrip();
		headerBar.setMembersMargin(2);
		headerBar.setSize("445", "44");
		headerBar.addFill();
		headerBar.setStyleName("headerRight");

		userButton = new ToolStripButton(UserContext.getInstance().getName());
		GwtCommandDispatcher.getInstance().addTokenChangedHandler(new TokenChangedHandler() {

			public void onTokenChanged(TokenChangedEvent event) {
				userButton.setTitle(UserContext.getInstance().getName());
			}
		});
		userButton.setIcon("[ISOMORPHIC]/images/user-icon.png");
		headerBar.addMember(userButton);
		
		//TODO if user has admin role.
		if (true) {
			headerBar.addSeparator();
			ToolStripMenuButton menuButton = createAdminButton();
			headerBar.addMenuButton(menuButton);
		}
		headerBar.addSeparator();
		
		ToolStripButton logoutButton = new ToolStripButton(LOGOUT);
		logoutButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent clickEvent) {
				SsecAccess.logout();
			}
		});
		headerBar.addMember(logoutButton);
		headerBar.addSpacer(LayoutConstant.SPACER_SMALL);
		addMember(headerBar);
	}

	/**
	 * Sets the title of the top bar.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setLeftTitle(@NotNull String title) {
		leftLabel.setContents(title);
	}

	public String getLeftTitle() {
		return leftLabel.getContents();
	}

	/**
	 * Sets the tooltip of the top bar.
	 * 
	 * @param tooltip
	 *            the new title
	 */
	public void setLeftTooltip(@NotNull String tooltip) {
		leftLabel.setTooltip(tooltip);
		leftLabel.setHoverWidth(700);
	}

	/**
	 * Gets the button to open the user profile.
	 * 
	 * @return the user button
	 */
	public ToolStripButton getUserButton() {
		return userButton;
	}

	private ToolStripMenuButton createAdminButton() {
		Menu menu = new Menu();
		menu.setShowShadow(true);
		menu.setShadowDepth(3);

		MenuItem editEmails = new MenuItem("Edit email template...");
		Menu emailTemplates = new Menu();
		emailTemplates.setItems(
					new EmailItem(REJECTED),
					new EmailItem(STARTED),
					new EmailItem(ENGAGEMENT_CHANGED),
					new EmailItem(FINISHED));
		editEmails.setSubmenu(emailTemplates);

//		TODO add report edit.
//		MenuItemSeparator separator = new MenuItemSeparator();
//		
//		MenuItem editEmails = new MenuItem("Edit email...", "icons/16/export1.png");
//		Menu emailTemplates = new Menu();
//		emailTemplates.setItems(
//					new MenuItem("XML"),
//					new MenuItem("CSV"),
//					new MenuItem("Plain text"));
//		editEmails.setSubmenu(emailTemplates);

		menu.setItems(editEmails);

		ToolStripMenuButton menuButton = new ToolStripMenuButton(ADMIN, menu);
		return menuButton;
	}
	/**
	 * Internal class used for the creation of {@link MenuItem}s for editing of {@link Template}s.
	 * @author Emiel Ackermann
	 *
	 */
	private class EmailItem extends MenuItem {
		
		public EmailItem(final String title) {
			super(title);
			
			addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				public void onClick(MenuItemClickEvent event) {
					String notifier = "";
					if (REJECTED.equals(title)) {
						notifier = KtunaxaConstant.Email.LEVEL_0;
					} else if (STARTED.equals(title)) {
						notifier = KtunaxaConstant.Email.START;
					} else if (ENGAGEMENT_CHANGED.equals(title)) {
						notifier = KtunaxaConstant.Email.CHANGE;
					} else if (FINISHED.equals(title)) {
						notifier = KtunaxaConstant.Email.RESULT;
					}
					emailWindow.setEmailTemplate(notifier);
					emailWindow.show();
				}
			});
		}
	}
}
