/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Menu bar for the Ktunaxa mapping dashboard. The menu bar has a navigation toolstrip on the left and an action
 * toolstrip on the right.
 * 
 * @author Jan De Moerloose
 * 
 */
public class MenuBar extends ToolStrip {

	private ToolStrip navStrip = new ToolStrip();

	private ToolStrip actionStrip = new ToolStrip();

	/**
	 * Construct a default menu bar.
	 */
	public MenuBar() {
		setMembersMargin(5);
		setSize("100%", "36");
		setBorder("none");
		navStrip.setBorder("none");
		actionStrip.setBorder("none");
		actionStrip.setAlign(Alignment.RIGHT);
		addSpacer(10);
		addMember(navStrip);
		addMember(new LayoutSpacer());
		addMember(actionStrip);
		addSpacer(10);
	}

	/**
	 * Add a navigation button to the left tool strip.
	 * 
	 * @param navMember
	 */
	public void addNavigationButton(Canvas navMember) {
		if (navStrip.getMembers().length > 0) {
			navStrip.addSeparator();
		}
		navStrip.addMember(navMember);
	}

	/**
	 * Add an action button to the right tool strip.
	 * 
	 * @param navMember
	 */
	public void addActionButton(Canvas actionMember) {
		if (actionStrip.getMembers().length > 0) {
			actionStrip.addSeparator();
		}
		actionStrip.addMember(actionMember);
	}

}
