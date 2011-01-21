/*
 * This is part of Geomajas, a GIS framework, http://www.geomajas.org/.
 *
 * Copyright 2008-2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.widget.LayerTree;
import org.geomajas.gwt.client.widget.Legend;
import org.geomajas.gwt.client.widget.MapWidget;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Layout for the search panel (see buttons top right on the map).
 * 
 * @author Pieter De Graef
 */
public class LayerTreePanel extends VLayout {

	private static final int PANEL_WIDTH = 270;

	private static final int PANEL_HEIGHT = 400;

	private static final int BTN_WIDTH = 24;

	private static final int TOP_OFFSET = 41;

	/** The layout containing the search and grid layouts. */
	private VLayout panelLayout;

	/** The layout containing the search widget. */
	private Canvas searchPanel;

	/** The button that opens the search layout. */
	private IButton searchButton;

	/** Clickable image that closes the whole layout again. */
	private Img closeBtn;

	// ------------------------------------------------------------------------
	// Constructor:
	// ------------------------------------------------------------------------

	/**
	 * The only constructor.
	 * 
	 * @param mapModel
	 *            The map's model. It knows what layers we can search in.
	 * @param parent
	 *            The map's parent widget. We use this to attach the search layout to.
	 */
	public LayerTreePanel(final MapWidget mapWidget, final Canvas parent) {
		// Set some parameters:
		setTop(TOP_OFFSET);
		setLeft(parent.getWidth() - BTN_WIDTH - 8);
		setWidth(BTN_WIDTH);
		setHeight(BTN_WIDTH + 8);
		setStyleName("search_panel_buttons");

		// Create the button GUI:
		createButtonGui();

		// Create the 2 panels:
		panelLayout = new VLayout();
		panelLayout.setBackgroundColor("#A0A0A0");
		panelLayout.setTop(TOP_OFFSET);
		panelLayout.setLeft(parent.getWidth() - PANEL_WIDTH);
		panelLayout.setHeight(PANEL_HEIGHT);
		panelLayout.setWidth(PANEL_WIDTH);
		panelLayout.setStyleName("search_panel");
		panelLayout.setVisible(false);

		searchPanel = createSearchPanel(mapWidget);
		panelLayout.addChild(searchPanel);

		parent.addChild(panelLayout);

		// Add a handler that makes sure this widget is placed at the correct location when the parent widget resizes:
		parent.addResizedHandler(new ResizedHandler() {

			public void onResized(ResizedEvent event) {
				setTop(TOP_OFFSET);
				setLeft(parent.getWidth() - BTN_WIDTH - 8);
				panelLayout.setLeft(parent.getWidth() - PANEL_WIDTH);
			}
		});
	}

	// ------------------------------------------------------------------------
	// Public methods concerning showing and hiding the panels:
	// ------------------------------------------------------------------------

	/** Returns the panel that is currently open. If no panel is open, then null is returned. */
	public Canvas getOpenPanel() {
		if (searchPanel.isVisible()) {
			return searchPanel;
		}
		return null;
	}

	/**
	 * Is a certain panel open or not?
	 * 
	 * @param panel
	 *            The panel in question.
	 * @return Returns true or false.
	 */
	public boolean isOpen(Canvas panel) {
		return panel.isVisible();
	}

	/**
	 * Hide the open panel again - no matter which one is open. If no panel is open than nothing will happen.
	 */
	public void hidePanel() {
		Canvas panel = getOpenPanel();
		if (panel != null) {
			panel.setVisible(false);
			panelLayout.setVisible(false);
			searchButton.setSelected(false);
			closeBtn.setVisible(false);
			setHeight(BTN_WIDTH + 8);
		}
	}

	/**
	 * Open either the search or the grid panel. (this piece of code could be better....)
	 * 
	 * @param panel
	 *            The search or the grid panel to show.
	 */
	public void showPanel(final Canvas panel) {
		if (panel == searchPanel) {
			searchButton.setSelected(true);
		} else {
			searchPanel.setVisible(false);
			searchButton.setSelected(false);
		}
		panel.setVisible(true);
		panelLayout.setVisible(true);
		closeBtn.setVisible(true);
		setHeight(PANEL_HEIGHT);
	}

	// ------------------------------------------------------------------------
	// Private methods concerning GUI:
	// ------------------------------------------------------------------------

	/** Create the button GUI and attach the buttons to this widget. */
	private void createButtonGui() {
		// The search button opens the search widget:
		searchButton = new IButton();
		searchButton.setIcon("[ISOMORPHIC]/geomajas/osgeo/settings.png");
		searchButton.setSize(BTN_WIDTH + "px", BTN_WIDTH + "px");
		searchButton.setRadioGroup("panels");
		searchButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (isOpen(searchPanel)) {
					hidePanel();
				} else {
					showPanel(searchPanel);
				}
			}
		});

		// A close button for closing. Only visible when a panel is open:
		closeBtn = new Img("[ISOMORPHIC]/images/arrow_right.png", BTN_WIDTH, BTN_WIDTH);
		closeBtn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hidePanel();
			}
		});
		closeBtn.setCursor(Cursor.HAND);
		closeBtn.setVisible(false);

		// Add all buttons to this widget:
		addMember(searchButton);
		addMember(new LayoutSpacer());
		addMember(closeBtn);
	}

	/** Create the layout for the search panel. Also connect it to the feature grid. */
	private Canvas createSearchPanel(final MapWidget mapWidget) {
		VLayout layout = new VLayout();
		layout.setSize("100%", "100%");
		layout.setVisible(false);
		layout.setStyleName("search_panel_inner");

		final SectionStack sectionStack = new SectionStack();
		//sectionStack.setPadding(5);
		//sectionStack.setBackgroundColor("#E0E0E0");
		//sectionStack.setShowEdges(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setCanReorderSections(true);
		sectionStack.setCanResizeSections(false);
		sectionStack.setSize("100%", "100%");

		// LayerTree layout:
		SectionStackSection section2 = new SectionStackSection("Layer tree");
		section2.setExpanded(true);
		LayerTree layerTree = new LayerTree(mapWidget);
		section2.addItem(layerTree);
		sectionStack.addSection(section2);

		// Legend layout:
		SectionStackSection section3 = new SectionStackSection("Legend");
		section3.setExpanded(true);
		Legend legend = new Legend(mapWidget.getMapModel());
		section3.addItem(legend);
		sectionStack.addSection(section3);

		layout.addMember(sectionStack);
		return layout;
	}
}