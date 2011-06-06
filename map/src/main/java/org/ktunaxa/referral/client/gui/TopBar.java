/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import org.ktunaxa.referral.client.security.UserContext;

/**
 * Top menu bar of the general layout. The top bar has a user section (login/logout,status) on the right and a list of
 * links. On the left there is a title.
 * 
 * @author Jan De Moerloose
 * 
 */
public class TopBar extends HLayout {

	private HTMLFlow leftLabel;

	private String title;

	private String tooltip;

	private ToolStripButton tasksButton;

	private ToolStripButton userButton;

	/**
	 * Constructs a top bar.
	 */
	public TopBar() {
		setSize("100%", "44");
		setStyleName("header");

		leftLabel = new HTMLFlow(title);
		leftLabel.setStyleName("headerText");
		if (tooltip != null) {
			leftLabel.setTooltip(tooltip);
			leftLabel.setHoverWidth(700);
		}
		leftLabel.setWidth100();
		addMember(leftLabel);

		LayoutSpacer spacer = new LayoutSpacer();
		addMember(spacer);

		ToolStrip headerBar = new ToolStrip();
		headerBar.setMembersMargin(2);
		headerBar.setSize("445", "44");
		headerBar.addFill();
		headerBar.setStyleName("headerRight");

		tasksButton = new ToolStripButton("Tasks");
		headerBar.addMember(tasksButton);
		userButton = new ToolStripButton(UserContext.getInstance().getName()); // @todo update after login
		userButton.setIcon("[ISOMORPHIC]/images/user-icon.png");
		headerBar.addMember(userButton);
		headerBar.addSeparator();
		ToolStripButton logoutButton = new ToolStripButton("Logout");
		headerBar.addMember(logoutButton);
		headerBar.addSpacer(10);
		addMember(headerBar);
	}

	/**
	 * Sets the title of the top bar.
	 * 
	 * @param title the new title
	 */
	public void setLeftTitle(String title) {
		leftLabel.setContents(title);
	}

	/**
	 * Gets the button to navigate to the BPM dashboard.
	 * 
	 * @return the tasks button
	 */
	public ToolStripButton getTasksButton() {
		return tasksButton;
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
