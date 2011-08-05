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
import org.geomajas.widget.searchandfilter.client.widget.attributesearch.AttributeSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.FreeDrawingSearch;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.GeometricSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.SelectionSearch;
import org.geomajas.widget.searchandfilter.client.widget.multifeaturelistgrid.MultiFeatureListGrid;
import org.geomajas.widget.searchandfilter.client.widget.search.AbstractSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.search.CombinedSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.search.PanelSearchWidget;
import org.geomajas.widget.searchandfilter.client.widget.search.SearchController;
import org.geomajas.widget.searchandfilter.client.widget.search.SearchWidget;
import org.geomajas.widget.utility.smartgwt.client.widget.CardLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Panel that displays search tools: search for referral and geographical search / spatial operations.
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 * @author Joachim Van der Auwera
 */
public class SearchPanel extends VLayout {

	private static final String NAME = "SEARCH";

	/**
	 * Card type enumeration.
	 *
	 * @author Joachim Van der Auwera
	 */
	private enum Card {
		EMPTY, GEOMETRIC, ATTRIBUTE
	}

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
		CardLayout<Card> searchPanels = new CardLayout<Card>();
		GeometricSearchPanel gsp = new GeometricSearchPanel(mapWidget);
		gsp.addSearchMethod(new SelectionSearch());
		gsp.addSearchMethod(new FreeDrawingSearch());
		PanelSearchWidget geometricSearch = new CardPanelSearchWidget("GeometricSearch", "Search on geometry", gsp,
				searchPanels, Card.GEOMETRIC);
		PanelSearchWidget attributeSearch = new CardPanelSearchWidget("AttributeSearch", "Search on attributes",
				new AttributeSearchPanel(mapWidget), searchPanels, Card.ATTRIBUTE);
		//PanelSearchWidget favouriteSearch = new CardPanelSearchWidget("FavouritesSearch", "Select favourite",
		//		new SearchFavouritesListPanel(mapWidget), searchPanels, CARD_FAVOURITE);
		searchPanels.addCard(Card.EMPTY, new VLayout()); // add empty card option
		searchPanels.showCard(Card.EMPTY);
		searchPanels.setHeight(1); // minimum height
		searchPanels.setOverflow(Overflow.VISIBLE); // but scale
		List<SearchWidget> searchWidgetList = new ArrayList<SearchWidget>();
		searchWidgetList.add(attributeSearch);
		searchWidgetList.add(geometricSearch);
		//searchWidgetList.add(favouriteSearch);

		VLayout valueSearch = new VLayout();
		CombinedSearchPanel combinedSearchPanel = new CombinedSearchPanel(mapWidget);
		combinedSearchPanel.initializeList(searchWidgetList);
		combinedSearchPanel.setHeight(1); // minimum height
		combinedSearchPanel.setOverflow(Overflow.VISIBLE); // but scale
		PanelSearchWidget searchCombined = new PanelSearchWidget("PanelSearchWidget", "search widget",
				combinedSearchPanel);
		SearchController searchController = new SearchController(mapWidget, false);
		searchController.addSearchHandler(valueResultList);
		searchCombined.addSearchRequestHandler(searchController);
		// following lines make sure the criterion type select is not visible
		//searchCombined.setHeight(1); // minimum height
		//searchCombined.setOverflow(Overflow.VISIBLE); // but scale
		valueSearch.addMember(searchCombined);
		valueSearch.addMember(searchPanels);

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

	private static class CardPanelSearchWidget extends PanelSearchWidget {
		private CardLayout<Card> cardLayout;
		private Card cardKey;

		public CardPanelSearchWidget(String id, String name, AbstractSearchPanel searchPanel,
				CardLayout<Card> cardLayout, Card cardKey) {
			super(id, name, searchPanel);
			this.cardLayout = cardLayout;
			this.cardKey = cardKey;
			cardLayout.addCard(cardKey, this);
		}

		@Override
		public void showForSearch() {
			super.showForSearch();
			cardLayout.showCard(cardKey);
		}

		@Override
		public void showForSave(SaveRequestHandler handler) {
			super.showForSave(handler);
			cardLayout.showCard(cardKey);
		}

		@Override
		public void show() {
			// have to prevent infinite loops
			if (this != cardLayout.getCurrentCard()) {
				cardLayout.showCard(cardKey);
			}
			super.show();
		}
	}
}