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

import org.geomajas.gwt.client.widget.LayerTree;
import org.geomajas.gwt.client.widget.Legend;
import org.geomajas.gwt.client.widget.MapWidget;

import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel that displays a layer tree and a legend.
 * 
 * @author Pieter De Graef
 */
public class LayerPanel extends VLayout {

	public LayerPanel(MapWidget mapWidget) {
		setSize("100%", "100%");
		setPadding(3);

		final SectionStack sectionStack = new SectionStack();
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
		
		addMember(sectionStack);
	}
}