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

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Panel that displays the referral information (details, comments, documents).
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 */
public class ReferralPanel extends VLayout {

	/**
	 * Name of the panel in navigation link.
	 */
	public static final String NAME = "REFERRAL";

	private DetailPanel panelDetail;

	private CommentPanel panelComments;

	private DocumentPanel panelDocuments;

	private ReferralTasksPanel panelTasks;

	private TabSet tabs;

	private Tab tabTasks;

	private Tab tabDetails;

	/**
	 * Constructs a new referral panel.
	 */
	public ReferralPanel() {
		super();
		setSize("100%", "100%");

		tabs = new TabSet();
		tabs.setSize("100%", "100%");
		tabDetails = new Tab("Details");
		Tab tabDocuments = new Tab("Documents");
		Tab tabComments = new Tab("Comments");
		tabTasks = new Tab("Tasks");

		panelDetail = new DetailPanel();
		panelDetail.setWidth100();
		
		panelComments = new CommentPanel();
		panelDocuments = new DocumentPanel();
		panelTasks = new ReferralTasksPanel();
		tabDetails.setPane(panelDetail);
		tabDocuments.setPane(panelDocuments);
		tabComments.setPane(panelComments);
		tabTasks.setPane(panelTasks);
		tabs.setTabs(tabDetails, tabDocuments, tabComments, tabTasks);
		addMember(tabs);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		panelDetail.init(referralLayer, referral);
		panelDocuments.init(referralLayer, referral);
		panelComments.init(referralLayer, referral);
		panelTasks.refresh();
	}

	public String getName() {
		return NAME;
	}

	/**
	 * Set the focus to the current task on this pane.
	 */
	public void focusCurrentTask() {
		tabs.selectTab(tabTasks); // assure tabs pane is displayed
		panelTasks.show(); // refresh view
	}

	public void focusDetail() {
		tabs.selectTab(tabDetails); // assure details pane is displayed		
	}
}