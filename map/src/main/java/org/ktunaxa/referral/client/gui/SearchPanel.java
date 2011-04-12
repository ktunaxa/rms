/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.widget.FeatureListGrid;
import org.geomajas.gwt.client.widget.FeatureSearch;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.event.DefaultSearchHandler;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

/**
 * Panel that displays 2 tabs: a FeatureSearch and a FeatureListGrid.
 * 
 * @author Pieter De Graef
 */
public class SearchPanel extends VLayout {

	public SearchPanel(MapWidget mapWidget) {
		setSize("100%", "100%");

		final TabSet tabs = new TabSet();
		Tab tab1 = new Tab("Search");
		Tab tab2 = new Tab("Search Results");

		FeatureListGrid grid = new FeatureListGrid(mapWidget.getMapModel());
		grid.setBackgroundColor("#FFFFFF");
		FeatureSearch search = new FeatureSearch(mapWidget.getMapModel(), true);
		search.setBackgroundColor("#FFFFFF");
		search.addSearchHandler(new DefaultSearchHandler(grid) {

			public void afterSearch() {
				tabs.selectTab(1);
			}
		});

		tab1.setPane(search);
		tab2.setPane(grid);
		tabs.setTabs(tab1, tab2);
		addMember(tabs);
	}
}