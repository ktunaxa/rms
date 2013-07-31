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

import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.MapModelChangedEvent;
import org.geomajas.gwt.client.map.event.MapModelChangedHandler;
import org.geomajas.gwt.client.widget.Legend;

/**
 * Legend that does not react to layer filter changes but can be refreshed (has no effect anyway).
 * 
 * @author Jan De Moerloose
 * 
 */
public class RefreshLegend extends Legend {

	public RefreshLegend(MapModel mapModel) {
		super(mapModel);
		mapModel.addMapModelChangedHandler(new MapModelChangedHandler() {

			public void onMapModelChanged(MapModelChangedEvent event) {
				refresh();
			}
		});
	}

	@Override
	public void render() {
	}

	public void refresh() {
		super.render();
	}

}
