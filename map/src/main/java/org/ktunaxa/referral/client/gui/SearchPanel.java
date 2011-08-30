/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.map.feature.LazyLoadCallback;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.FeatureListGrid;
import org.geomajas.gwt.client.widget.MapWidget;

import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import org.geomajas.layer.feature.Feature;
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
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedEvent;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedHandler;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

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

	public SearchPanel(MapLayout mapLayout) {
		setSize("100%", "100%");
		setOverflow(Overflow.AUTO);

		final TabSet tabs = new TabSet();
		Tab tabReferral = new Tab("Referral");
		Tab tabValues = new Tab("Values");

		// initialization for value searching

		tabReferral.setPane(getSearchTabContent(mapLayout, KtunaxaConstant.LAYER_REFERRAL_ID));
		tabValues.setPane(getSearchTabContent(mapLayout, KtunaxaConstant.LAYER_REFERENCE_VALUE_ID));
		tabs.setTabs(tabReferral, tabValues);
		addMember(tabs);
	}

	private VLayout getSearchTabContent(final MapLayout mapLayout, String layerId) {
		final MapWidget mapWidget = mapLayout.getMap();
		final MultiFeatureListGrid valueResultList = new MultiFeatureListGrid(mapWidget);
		valueResultList.setWidth100();
		valueResultList.setHeight100();
		valueResultList.setClearTabsetOnSearch(true);
		ToolStripButton makeCurrentButton = new ToolStripButton("Make current");
		makeCurrentButton.setIcon(WidgetLayout.iconSaveAlt);
		makeCurrentButton.setTooltip("Set as current referral");
		makeCurrentButton.setShowDisabledIcon(false);
		makeCurrentButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ListGridRecord[] records = valueResultList.getSelection(KtunaxaConstant.LAYER_REFERRAL_ID);
					if (null != records && records.length > 0) {
						String featureId = records[0].getAttribute(FeatureListGrid.FIELD_NAME_FEATURE_ID);
						mapWidget.getMapModel().getVectorLayer(KtunaxaConstant.LAYER_REFERRAL_ID).
								getFeatureStore().getFeature(featureId, GeomajasConstant.FEATURE_INCLUDE_ALL,
								new LazyLoadCallback() {
							public void execute(List<org.geomajas.gwt.client.map.feature.Feature> response) {
								if (response.size() > 0) {
									mapLayout.setReferralAndTask(response.get(0).toDto(), null);
								}
							}
						});
					}
				}
			});
		valueResultList.addButton(KtunaxaConstant.LAYER_REFERRAL_ID, makeCurrentButton, 1);
		CardLayout<Card> searchPanels = new CardLayout<Card>();
		GeometricSearchPanel geometricSearchPanel = new GeometricSearchPanel(mapWidget);
		final SelectionSearch selectionSearch = new SelectionSearch();
		final IButton currentReferralGeometry = new IButton("Use current referral");
		currentReferralGeometry.setIcon(WidgetLayout.iconOpenAlt);
		currentReferralGeometry.setShowDisabledIcon(false);
		currentReferralGeometry.setAutoFit(true);
		currentReferralGeometry.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				List<Geometry> geometries = new ArrayList<Geometry>();
				Feature referral = mapLayout.getCurrentReferral();
				if (null != referral) {
					geometries.add(GeometryConverter.toGwt(referral.getGeometry()));
				}
				selectionSearch.setGeometry(geometries);
			}
		});
		currentReferralGeometry.setDisabled(true); // no current referral yet
		CurrentReferralChangedHandler currentReferralChangedHandler = new CurrentReferralChangedHandler() {
			public void onCurrentReferralChanged(CurrentReferralChangedEvent event) {
				currentReferralGeometry.setDisabled(null == event.getReferral());
			}
		};
		mapLayout.addCurrentReferralChangedHandler(currentReferralChangedHandler);
		selectionSearch.addSelectButton(currentReferralGeometry);
		geometricSearchPanel.addSearchMethod(selectionSearch);
		geometricSearchPanel.addSearchMethod(new FreeDrawingSearch());
		geometricSearchPanel.setWidth100();
		geometricSearchPanel.setCanAddToFavourites(false);
		geometricSearchPanel.setFeatureSearchVectorLayer(layerId);
		PanelSearchWidget geometricSearch = new CardPanelSearchWidget("GeometricSearch" + layerId, "Search on geometry",
				geometricSearchPanel, searchPanels, Card.GEOMETRIC);
		geometricSearch.setWidth100();
		AttributeSearchPanel attributeSearchPanel = new AttributeSearchPanel(mapWidget,
				false, layerId);
		attributeSearchPanel.setWidth100();
		attributeSearchPanel.setCanAddToFavourites(false);
		PanelSearchWidget attributeSearch = new CardPanelSearchWidget("AttributeSearch" + layerId,
				"Search on attributes", attributeSearchPanel, searchPanels, Card.ATTRIBUTE);
		attributeSearch.setWidth100();
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
		combinedSearchPanel.setCanAddToFavourites(false);
		combinedSearchPanel.setWidth100();
		PanelSearchWidget combinedSearch = new PanelSearchWidget("PanelSearchWidget" + layerId, "search widget",
				combinedSearchPanel);
		SearchController searchController = new SearchController(mapWidget, false);
		searchController.addSearchHandler(valueResultList);
		combinedSearch.addSearchRequestHandler(searchController);
		combinedSearch.setWidth100();
		valueSearch.addMember(combinedSearch);
		HTMLFlow divider = new HTMLFlow("<hr />");
		divider.setWidth100();
		divider.setHeight(5);
		valueSearch.addMember(divider);
		valueSearch.addMember(searchPanels);

		valueSearch.addMember(valueResultList);
		return valueSearch;
	}

	public String getName() {
		return NAME;
	}

	/**
	 * Specific {@link PanelSearchWidget} to put as card inside {@link CardLayout}.
	 *
	 * @author Joachim Van der Auwera
	 */
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