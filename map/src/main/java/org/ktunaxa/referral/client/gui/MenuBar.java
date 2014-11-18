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

	private boolean empty = true;

	/**
	 * Construct a default menu bar.
	 */
	public MenuBar() {
		super();

		setMembersMargin(LayoutConstant.MARGIN_SMALL);
		setSize("100%", "36");
		setBorder("none");
		navStrip.setBorder("none");
		actionStrip.setBorder("none");
		actionStrip.setAlign(Alignment.RIGHT);
		addSpacer(LayoutConstant.SPACER_SMALL);
		addMember(navStrip);
		addMember(new LayoutSpacer());
		addMember(actionStrip);
		addSpacer(LayoutConstant.SPACER_SMALL);
	}

	/**
	 * Add a navigation button to the left tool strip.
	 * 
	 * @param navMember navigation member
	 */
	public void addNavigationButton(Canvas navMember) {
		// can't check members in new smartgwt !!!???
		if (!empty) {
			navStrip.addSeparator();
		} else {
			empty = false;
		}
		navStrip.addMember(navMember);
	}

	/**
	 * Add an action button to the right tool strip.
	 * 
	 * @param actionMember action member
	 */
	public void addActionButton(Canvas actionMember) {
		// can't check members in new smartgwt !!!???
		if (!empty) {
			actionStrip.addSeparator();
		} else {
			empty = false;
		}
		actionStrip.addMember(actionMember);
	}

}
