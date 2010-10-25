package org.ktunaxa.referral.client.pages;

import org.geomajas.gwt.client.widget.FeatureListGrid;
import org.geomajas.gwt.client.widget.FeatureSearch;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.event.DefaultSearchHandler;

import com.smartgwt.client.widgets.tab.TabSet;

/**
 * <p>
 * Example tab showing the {@link SearchTable} widget.
 * </p>
 * 
 * @author Pieter De Graef
 */
public class SearchPage extends AbstractTab {

	/**
	 * Create a tab page with a search functionality.
	 * 
	 * @param map
	 *            The map for whom we are searching for features.
	 * @param tabSet
	 *            The set of tabs to whom this tab will be added. Also when a search is done, this tab-set is used to
	 *            make the tab with the feature grid visible.
	 * @param featureGrid
	 *            The actual feature grid instance from the second tab. This feature grid is used to display the
	 *            features that result from a search.
	 */
	public SearchPage(MapWidget map, final TabSet tabSet, FeatureListGrid featureGrid) {
		super("Search", map);

		// Create a SearchWidget, based upon a map's model:
		FeatureSearch searchWidget = new FeatureSearch(map.getMapModel(), true);

		// What to do when the result of a search comes in?
		// The DefaultSearchHandler will add all the features in the result to the given FeatureListGrid.
		searchWidget.addSearchHandler(new DefaultSearchHandler(featureGrid) {

			// After the features have been added to the FeatureListGrid, make sure the tab with the grid is visible:
			public void afterSearch() {
				tabSet.selectTab(1);
			}
		});

		// Limit the maximum number of features that a search may produce:
		searchWidget.setMaximumResultSize(20);
		mainLayout.addMember(searchWidget);
	}

	public void initialize() {
	}
}
