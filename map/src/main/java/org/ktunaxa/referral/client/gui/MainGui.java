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
		leftLayout.restore();
		leftLayout.setWidth(width);
		leftLayout.setTitle(title);
		leftLayout.setPane(canvas);
		leftLayout.setVisible(true);
	}

	public void hideLeftLayout() {
		leftLayout.setVisible(false);
		if (leftLayout.getViewState() == ViewState.MAXIMIZED) {
			leftLayout.restore();
		}
	}

	public void showBottomLayout(Canvas canvas, String title, String height) {
		bottomLayout.restore();
		bottomLayout.setHeight(height);
		bottomLayout.setTitle(title);
		bottomLayout.setPane(canvas);
		bottomLayout.setVisible(true);
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
		//leftLayout.setShowResizeBar(true);  

		VLayout rightLayout = new VLayout();
		rightLayout.setSize("100%", "100%");

		VLayout mapLayout = new VLayout();
		mapLayout.setSize("100%", "100%");
		mapLayout.setStyleName("block");
		//mapLayout.setShowResizeBar(true);  
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