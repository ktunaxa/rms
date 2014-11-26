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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.map.feature.LazyLoadCallback;
import org.geomajas.gwt.client.spatial.geometry.Geometry;
import org.geomajas.gwt.client.util.GeometryConverter;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.FeatureListGrid;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.layer.feature.Feature;
import org.geomajas.widget.searchandfilter.client.widget.attributesearch.AttributeSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.configuredsearch.ConfiguredSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.FreeDrawingSearch;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.GeometricSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.geometricsearch.SelectionSearch;
import org.geomajas.widget.searchandfilter.client.widget.multifeaturelistgrid.MultiFeatureListGrid;
import org.geomajas.widget.searchandfilter.client.widget.search.AbstractSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.search.CombinedSearchPanel;
import org.geomajas.widget.searchandfilter.client.widget.search.PanelSearchWidget;
import org.geomajas.widget.searchandfilter.client.widget.search.SearchController;
import org.geomajas.widget.searchandfilter.client.widget.search.SearchWidget;
import org.geomajas.widget.searchandfilter.search.dto.ConfiguredSearch;
import org.geomajas.widget.searchandfilter.search.dto.ConfiguredSearchAttribute;
import org.geomajas.widget.searchandfilter.search.dto.ConfiguredSearchAttribute.AttributeType;
import org.geomajas.widget.searchandfilter.search.dto.ConfiguredSearchAttribute.InputType;
import org.geomajas.widget.searchandfilter.search.dto.ConfiguredSearchAttribute.Operation;
import org.geomajas.widget.utility.gwt.client.widget.CardLayout;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedEvent;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedHandler;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * Panel that displays search tools: search for referral and geographical search / spatial operations.
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 * @author Joachim Van der Auwera
 */
public class SearchPanel extends VLayout {

	private static final String NAME = "SEARCH";

	private ValueSearchPanel referralSearchTab;

	private ValueSearchPanel referenceValueSearchTab;
	
	private ConfiguredSearchPanel fullTextSearchPanel;

	/**
	 * Card type enumeration.
	 * 
	 * @author Joachim Van der Auwera
	 */
	private enum Card {
		EMPTY, GEOMETRIC, ATTRIBUTE
	}

	/**
	 * Construct a {@link SearchPanel}.
	 * 
	 * @param mapLayout map layout
	 */
	public SearchPanel(MapLayout mapLayout) {
		setSize("100%", "100%");
		setOverflow(Overflow.AUTO);

		final TabSet tabs = new TabSet();
		Tab tabReferral = new Tab("Referral");
		Tab tabValues = new Tab("Values");

		referralSearchTab = createSearchTabContent(mapLayout, KtunaxaConstant.LAYER_REFERRAL_ID);
		referenceValueSearchTab = createSearchTabContent(mapLayout, KtunaxaConstant.LAYER_REFERENCE_VALUE_ID);
		tabReferral.setPane(referralSearchTab);
		tabValues.setPane(referenceValueSearchTab);
		tabs.setTabs(tabReferral, tabValues);
		addMember(tabs);
	}

	public void refreshSearch() {
		referralSearchTab.refreshSearch();
	}

	private ValueSearchPanel createSearchTabContent(final MapLayout mapLayout, String layerId) {
		return new ValueSearchPanel(mapLayout, layerId);
	}

	public List<String> getSelectedReferrals() {
		List<String> fullIds = new ArrayList<String>();
		if (referralSearchTab.getSelection(KtunaxaConstant.LAYER_REFERRAL_ID) != null) {
			for (ListGridRecord record : referralSearchTab.getSelection(KtunaxaConstant.LAYER_REFERRAL_ID)) {
				fullIds.add(record.getAttribute(KtunaxaConstant.ATTRIBUTE_FULL_ID));
			}
		}
		return fullIds;
	}

	public String getName() {
		return NAME;
	}

	/**
	 * Refreshable search panel.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	private class ValueSearchPanel extends VLayout {

		private MultiFeatureListGrid valueResultList;

		private PanelSearchWidget combinedSearch;

		public ValueSearchPanel(final MapLayout mapLayout, String layerId) {
			final MapWidget mapWidget = mapLayout.getMap();
			valueResultList = new MultiFeatureListGrid(mapWidget);
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
						mapWidget.getMapModel().getVectorLayer(KtunaxaConstant.LAYER_REFERRAL_ID).getFeatureStore()
								.getFeature(featureId, GeomajasConstant.FEATURE_INCLUDE_ALL, new LazyLoadCallback() {

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
			geometricSearchPanel.setCanCancel(true);
			PanelSearchWidget geometricSearch = new CardPanelSearchWidget("GeometricSearch" + layerId,
					"Search on geometry", geometricSearchPanel, searchPanels, Card.GEOMETRIC);
			geometricSearch.setWidth100();
			AttributeSearchPanel attributeSearchPanel = new AttributeSearchPanel(mapWidget, false, layerId);
			attributeSearchPanel.setWidth100();
			attributeSearchPanel.setCanAddToFavourites(false);
			attributeSearchPanel.setCanCancel(true);
			PanelSearchWidget attributeSearch = new CardPanelSearchWidget("AttributeSearch" + layerId,
					"Search on attributes", attributeSearchPanel, searchPanels, Card.ATTRIBUTE);
			attributeSearch.setWidth100();			
			// PanelSearchWidget favouriteSearch = new CardPanelSearchWidget("FavouritesSearch", "Select favourite",
			// new SearchFavouritesListPanel(mapWidget), searchPanels, CARD_FAVOURITE);
			searchPanels.addCard(Card.EMPTY, new VLayout()); // add empty card option
			searchPanels.showCard(Card.EMPTY);
			searchPanels.setHeight(1); // minimum height
			searchPanels.setOverflow(Overflow.VISIBLE); // but scale
			List<SearchWidget> searchWidgetList = new ArrayList<SearchWidget>();
			if (KtunaxaConstant.LAYER_REFERENCE_VALUE_ID.equals(layerId)) {
				fullTextSearchPanel = new ConfiguredSearchPanel(mapWidget);
				fullTextSearchPanel.setWidth100();
				fullTextSearchPanel.setCanAddToFavourites(false);
				fullTextSearchPanel.setCanCancel(true);
				fullTextSearchPanel.setLayerId(layerId);
				PanelSearchWidget fullTextSearch = new CardPanelSearchWidget("FullTextSearch" + layerId,
						"Search full text", fullTextSearchPanel, searchPanels, Card.ATTRIBUTE);
				searchWidgetList.add(fullTextSearch);
				mapLayout.getMap().getMapModel().runWhenInitialized(new Runnable() {
					
					@Override
					public void run() {
						ConfiguredSearch searchConfig = new ConfiguredSearch();
						ConfiguredSearchAttribute searchAttribute = new ConfiguredSearchAttribute();
						searchAttribute.setAttributeName(KtunaxaConstant.ATTRIBUTE_FULL_TEXT);
						searchAttribute.setDisplayText("Full text search");
						searchAttribute.setAttributeType(AttributeType.String);
						searchAttribute.setInputType(InputType.FreeValue);
						searchAttribute.setOperation(Operation.EqualToString);
						searchAttribute.setServerLayerId("layerFtsLayers");
						searchConfig.setAttributes(Arrays.asList(searchAttribute));
						fullTextSearchPanel.setSearchConfig(searchConfig, KtunaxaConstant.LAYER_REFERENCE_VALUE_ID);
						
					}
				});
			}
			searchWidgetList.add(geometricSearch);
			searchWidgetList.add(attributeSearch);
			// searchWidgetList.add(favouriteSearch);
			CombinedSearchPanel combinedSearchPanel = new CombinedSearchPanel(mapWidget);
			combinedSearchPanel.initializeList(searchWidgetList);
			combinedSearchPanel.setCanAddToFavourites(false);
			combinedSearchPanel.setAlwaysShowElements(false);
			combinedSearchPanel.setHideButtonsWhenAdding(true);
			combinedSearchPanel.setCanCancel(false);
			combinedSearchPanel.setWidth100();
			combinedSearch = new PanelSearchWidget("PanelSearchWidget" + layerId, "search widget", combinedSearchPanel);
			SearchController searchController = new SearchController(mapWidget, false);
			searchController.addSearchHandler(valueResultList);
			combinedSearch.addSearchRequestHandler(searchController);
			combinedSearch.setWidth100();
			addMember(combinedSearch);
			HTMLFlow divider = new HTMLFlow("<hr />");
			divider.setWidth100();
			divider.setHeight(5);
			addMember(divider);
			addMember(searchPanels);
			addMember(valueResultList);
		}

		public void refreshSearch() {
			combinedSearch.startSearch();
		}

		public ListGridRecord[] getSelection(String layerId) {
			return valueResultList.getSelection(layerId);
		}

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