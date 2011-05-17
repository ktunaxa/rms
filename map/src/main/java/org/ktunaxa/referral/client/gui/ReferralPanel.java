/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.widget.MapWidget;

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

	/**
	 * Constructs a new referral panel for the specified map.
	 * 
	 * @param mapWidget the map
	 */
	public ReferralPanel(MapWidget mapWidget) {
		setSize("100%", "100%");

		TabSet tabs = new TabSet();
		tabs.setSize("100%", "100%");
		Tab tabDetails = new Tab("Details");
		Tab tabDouments = new Tab("Documents");
		Tab tabComments = new Tab("Comments");

		DetailPanel panelDetail = new DetailPanel(mapWidget);
		CommentPanel panelComments = new CommentPanel();
		DocumentPanel panelDocuments = new DocumentPanel();
		tabDetails.setPane(panelDetail);
		tabDouments.setPane(panelDocuments);
		tabComments.setPane(panelComments);
		tabs.setTabs(tabDetails, tabDouments, tabComments);
		addMember(tabs);
	}

	
	public String getName() {
		return NAME;
	}

}