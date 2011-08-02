/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Overflow;
import org.geomajas.gwt.client.widget.FeatureListGrid;
import org.geomajas.gwt.client.widget.FeatureSearch;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.event.DefaultSearchHandler;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import org.geomajas.widget.searchandfilter.client.widget.attributesearch.AttributeSearchCreator;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.FreeDrawingSearch;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.GeometricSearchCreator;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.GeometricSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.GeometricSearchPanelCreator;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.SelectionSearch;
import org.geomajas.widget.searchandfilter.client.widget.multifeaturelistgrid.MultiFeatureListGrid;
import org.geomajas.widget.searchandfilter.client.widget.search.CombinedSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.search.PanelSearchWidget;
import org.geomajas.widget.searchandfilter.client.widget.search.SearchWidgetRegistry;
import org.geomajas.widget.searchandfilter.client.widget.searchfavourites.SearchFavouritesListCreator;

/**
 * Panel that displays search tools: search for referral and geographical search / spatial operations.
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 * @author Joachim Van der Auwera
 */
public class SearchPanel extends VLayout {

	private static final String NAME = "SEARCH";

	public SearchPanel(MapWidget mapWidget) {
		setSize("100%", "100%");

		final TabSet tabs = new TabSet();
		Tab tab1 = new Tab("General Search");
		Tab tab2 = new Tab("Search Results");
		Tab tabReferral = new Tab("Referral");
		Tab tabValues = new Tab("Values");

		FeatureListGrid grid = new FeatureListGrid(mapWidget.getMapModel());
		grid.setBackgroundColor("#FFFFFF");
		FeatureSearch search = new FeatureSearch(mapWidget.getMapModel(), true);
		search.setBackgroundColor("#FFFFFF");
		search.addSearchHandler(new DefaultSearchHandler(grid) {

			public void afterSearch() {
				tabs.selectTab(1);
			}
		});

		// initialization for value searching
		MultiFeatureListGrid valueResultList = new MultiFeatureListGrid(mapWidget);
		valueResultList.setBackgroundColor("#FFFFFF");
		SearchWidgetRegistry.initialize(mapWidget, valueResultList);
		SearchWidgetRegistry.put(new AttributeSearchCreator());
		SearchWidgetRegistry.put(new GeometricSearchCreator(new GeometricSearchPanelCreator() {
			public GeometricSearchPanel createInstance(MapWidget mapWidget) {
				GeometricSearchPanel gsp = new GeometricSearchPanel(mapWidget);
				gsp.addSearchMethod(new SelectionSearch());
				gsp.addSearchMethod(new FreeDrawingSearch());
				return gsp;
			}
		}));
		SearchWidgetRegistry.put(new SearchFavouritesListCreator());
		VLayout valueSearch = new VLayout();
		CombinedSearchPanel combinedSearchPanel = new CombinedSearchPanel(mapWidget);
		combinedSearchPanel.initializeListUseAll();
		combinedSearchPanel.setHeight(1); // minimum height
		combinedSearchPanel.setOverflow(Overflow.VISIBLE); // but scale
		PanelSearchWidget panelSearchWidget = new PanelSearchWidget("PanelSearchWidget", "search widget",
				combinedSearchPanel);
		panelSearchWidget.setHeight(1); // minimum height
		panelSearchWidget.setOverflow(Overflow.VISIBLE); // but scale
		valueSearch.addMember(panelSearchWidget);
		valueSearch.addMember(valueResultList);

		tab1.setPane(search);
		tab2.setPane(grid);
		tabReferral.setPane(new VLayout()); // @todo provide proper content
		tabValues.setPane(valueSearch);
		tabs.setTabs(tab1, tab2, tabReferral, tabValues);
		addMember(tabs);
	}

	public String getName() {
		return NAME;
	}
}