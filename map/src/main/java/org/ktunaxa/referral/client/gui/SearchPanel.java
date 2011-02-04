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
		Tab tab2 = new Tab("Feature Grid");

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