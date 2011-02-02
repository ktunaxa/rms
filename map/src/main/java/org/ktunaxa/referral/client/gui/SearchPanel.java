/*
 * This file is part of Geomajas, a component framework for building
 * rich Internet applications (RIA) with sophisticated capabilities for the
 * display, analysis and management of geographic information.
 * It is a building block that allows developers to add maps
 * and other geographic data capabilities to their web applications.
 *
 * Copyright 2008-2010 Geosparc, http://www.geosparc.com, Belgium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
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
		FeatureSearch search = new FeatureSearch(mapWidget.getMapModel(), true);
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