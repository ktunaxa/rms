/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.command.event.TokenChangedEvent;
import org.geomajas.gwt.client.command.event.TokenChangedHandler;
import org.geomajas.plugin.staticsecurity.client.util.SsecAccess;
import org.ktunaxa.referral.client.security.UserContext;

import javax.validation.constraints.NotNull;

/**
 * Top menu bar of the general layout. The top bar has a user section (login/logout,status) on the right and a list of
 * links. On the left there is a title.
 * 
 * @author Jan De Moerloose
 * 
 */
public class TopBar extends HLayout {

	private HTMLFlow leftLabel;

	private ToolStripButton userButton;

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
		headerBar.addSeparator();
		ToolStripButton logoutButton = new ToolStripButton("Logout");
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
}