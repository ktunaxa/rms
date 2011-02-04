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

import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.Toolbar;
import org.ktunaxa.referral.client.widget.ResizableBottomLayout;
import org.ktunaxa.referral.client.widget.ResizableLeftLayout;
import org.ktunaxa.referral.client.widget.ViewState;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * General definition of the main layout for the Ktunaxa mapping component. It defines a left layer, bottom layout and a
 * map layout.
 * 
 * @author Pieter De Graef
 */
public class MainGui extends VLayout {

	private MapWidget mapWidget;

	private ResizableLeftLayout leftLayout;

	private ResizableBottomLayout bottomLayout;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public MainGui() {
		buildGui();
	}

	// ------------------------------------------------------------------------
	// Public methods:
	// ------------------------------------------------------------------------

	public void showLeftLayout(Canvas canvas, String title, String width) {
		mapWidget.setVisible(false);
		leftLayout.restore();
		leftLayout.setTitle(title);
		leftLayout.setPane(canvas);
		leftLayout.setWidth(width);
		leftLayout.setVisible(true);
		mapWidget.setVisible(true);
	}

	public void setLeftLayoutViewState(ViewState viewState) {
		if (viewState == ViewState.MINIMIZED) {
			leftLayout.minimize();
		} else if (viewState == ViewState.MAXIMIZED) {
			leftLayout.maximize();
		} else {
			leftLayout.restore();
		}
	}

	public void hideLeftLayout() {
		leftLayout.setVisible(false);
		if (leftLayout.getViewState() == ViewState.MAXIMIZED) {
			leftLayout.restore();
		}
	}

	public void showBottomLayout(Canvas canvas, String title, String height) {
		mapWidget.setVisible(false);
		bottomLayout.restore();
		bottomLayout.setHeight(height);
		bottomLayout.setTitle(title);
		bottomLayout.setPane(canvas);
		bottomLayout.setVisible(true);
		mapWidget.setVisible(true);
	}

	public void setBottomLayoutViewState(ViewState viewState) {
		if (viewState == ViewState.MINIMIZED) {
			bottomLayout.minimize();
		} else if (viewState == ViewState.MAXIMIZED) {
			bottomLayout.maximize();
		} else {
			bottomLayout.restore();
		}
	}

	public void hideBottomLayout() {
		bottomLayout.setVisible(false);
		if (bottomLayout.getViewState() == ViewState.MAXIMIZED) {
			bottomLayout.restore();
		}
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	public MapWidget getMapWidget() {
		return mapWidget;
	}

	// ------------------------------------------------------------------------
	// Private methods concerning GUI:
	// ------------------------------------------------------------------------

	private void buildGui() {
		HLayout hLayout = new HLayout();
		leftLayout = new ResizableLeftLayout("Layers");
		leftLayout.setSize("33%", "100%");
		leftLayout.setStyleName("block");
		// leftLayout.setShowResizeBar(true);

		VLayout rightLayout = new VLayout();
		rightLayout.setSize("100%", "100%");

		VLayout mapLayout = new VLayout();
		mapLayout.setSize("100%", "100%");
		mapLayout.setStyleName("block");
		// mapLayout.setShowResizeBar(true);
		mapWidget = new MapWidget("mainMap", "app");
		Toolbar toolbar = new Toolbar(mapWidget);
		toolbar.setButtonSize(Toolbar.BUTTON_SIZE_BIG);
		toolbar.setBorder("none");

		mapLayout.addMember(toolbar);
		mapLayout.addMember(mapWidget);

		bottomLayout = new ResizableBottomLayout("Hello world");
		bottomLayout.setSize("100%", "200px");
		bottomLayout.setStyleName("block");
		bottomLayout.setPane(new VLayout());

		rightLayout.addMember(mapLayout);
		rightLayout.addMember(bottomLayout);

		hLayout.addMember(leftLayout);
		hLayout.addMember(rightLayout);
		addMember(hLayout);
	}
}