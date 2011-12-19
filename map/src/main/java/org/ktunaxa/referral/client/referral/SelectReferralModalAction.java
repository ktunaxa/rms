/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.action.toolbar.SelectionModalAction;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.FeatureDeselectedEvent;
import org.geomajas.gwt.client.map.event.FeatureSelectedEvent;
import org.geomajas.gwt.client.map.event.FeatureSelectionHandler;
import org.geomajas.gwt.client.map.event.MapModelChangedEvent;
import org.geomajas.gwt.client.map.event.MapModelChangedHandler;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.util.EqualsUtil;
import org.geomajas.gwt.client.widget.MapWidget;

import com.smartgwt.client.widgets.events.ClickEvent;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

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
		mapModel = mapWidget.getMapModel();
		if (mapModel.isInitialized()) {
			VectorLayer layer = mapModel.getVectorLayer(KtunaxaConstant.LAYER_REFERRAL_ID);
			layer.addFeatureSelectionHandler(new ReferralSelectHandler());
		}
		mapModel.addMapModelChangedHandler(new RegisterSelectReferralHandler());
		setTooltip("Select referral");
	}

	/** {@inheritDoc} */
	public void onSelect(ClickEvent event) {
		mapModel.selectLayer(mapModel.getLayer(KtunaxaConstant.LAYER_REFERRAL_ID));
		super.onSelect(event);
	}

	/**
	 * Handler to register the {@link ReferralSelectHandler}.
	 *
	 * @author Joachim Van der Auwera
	 */
	private static class RegisterSelectReferralHandler implements MapModelChangedHandler {
		/** {@inheritDoc} */
		public void onMapModelChanged(MapModelChangedEvent event) {
			VectorLayer layer = event.getMapModel().getVectorLayer(KtunaxaConstant.LAYER_REFERRAL_ID);
			layer.addFeatureSelectionHandler(new ReferralSelectHandler());
		}
	}

	/**
	 * Handler to make a referral current when selected.
	 *
	 * @author Joachim Van der Auwera
	 */
	private static class ReferralSelectHandler implements FeatureSelectionHandler {
		/** {@inheritDoc} */
		public void onFeatureSelected(FeatureSelectedEvent event) {
			Feature referral = event.getFeature().toDto();
			MapLayout mapLayout = MapLayout.getInstance();
			Feature prev = mapLayout.getCurrentReferral();
			// select referral only when different, prevent possible loops
			if (null == prev || !referral.getId().equals(prev.getId())) {
				mapLayout.setReferralAndTask(referral, null);
			}
		}

		/** {@inheritDoc} */
		public void onFeatureDeselected(FeatureDeselectedEvent event) {
			// nothing to do
		}
	}

}
