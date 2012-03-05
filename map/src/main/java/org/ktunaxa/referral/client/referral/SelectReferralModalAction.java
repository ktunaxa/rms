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

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.action.toolbar.SelectionModalAction;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.events.ClickEvent;

/**
 * <p>
 * Selection tool. You can either select individual features by indicating them, or drag a rectangle and select all
 * features which fall (for a minimum percentage) inside this rectangle.
 * </p>
 * <p>
 * Possible configuration options (server-side configuration) are:
 * <ul>
 * <li><b>clickTimeout</b>: Timeout in milliseconds for handling as click versus dragging.</li>
 * <li><b>coverageRatio</b>: Coverage percentage which is used to determine a feature as selected. This is only used
 * when dragging a rectangle to select in. Must be a floating value between 0 and 1.</li>
 * <li><b>priorityToSelectedLayer</b>: Activate or disable priority to the selected layer. This works only if there is a
 * selected layer, and that selected layer is a <code>VectorLayer</code>. In all other cases, the selection toggle will
 * occur on the first object that is encountered. In other words it will depend on the layer drawing order, starting at
 * the top.</li>
 * <li><b>pixelTolerance</b>: Number of pixels that describes the tolerance allowed when trying to select features.</li>
 * </ul>
 * </p>
 *
 * @author Joachim Van der Auwera
 * @author Pieter De Graef
 */
public class SelectReferralModalAction extends SelectionModalAction {

	private MapModel mapModel;

	/**
	 * Construct the selection tool.
	 *
	 * @param mapWidget map widget
	 */
	public SelectReferralModalAction(MapWidget mapWidget) {
		super(mapWidget);
		setTooltip("Select referral");
		setIcon("[ISOMORPHIC]/images/selectReferral.png");
	}

	/** {@inheritDoc} */
	public void onSelect(ClickEvent event) {
		// activate layer
		mapModel.selectLayer(mapModel.getLayer(KtunaxaConstant.LAYER_REFERRAL_ID));
		super.onSelect(event);
	}

}
