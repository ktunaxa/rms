/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.gui;

import org.ktunaxa.referral.client.gui.comments.CommentList;

import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Panel that displays 2 tabs: a FeatureSearch and a FeatureListGrid.
 * 
 * @author Pieter De Graef
 */
public class CommentPanel extends VLayout {

	public CommentPanel() {
		setSize("100%", "100%");

		SectionStack commentStack = new SectionStack();
		commentStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		commentStack.setSize("100%", "100%");

		// Comment list:
		SectionStackSection section1 = new SectionStackSection("List of all comments");
		section1.setExpanded(true);
		CommentList commentList = new CommentList();
		commentList.setMargin(5);
		section1.addItem(commentList);
		commentStack.addSection(section1);

		// Comment list:
		SectionStackSection section2 = new SectionStackSection("Comment details");
		section2.setExpanded(true);
		TabSet tabs = new TabSet();
		tabs.setMargin(5);
		Tab tab1 = new Tab("Edit selected comment");
		Tab tab2 = new Tab("Create new comment");
		tabs.setTabs(tab1, tab2);
		section2.addItem(tabs);
		commentStack.addSection(section2);

		addMember(commentStack);
	}
}