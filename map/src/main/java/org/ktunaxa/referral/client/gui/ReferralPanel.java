/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.client.referral.ReferralFormFactory;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Temporary class for displaying documents...
 * 
 * @author Pieter De Graef
 */
public class ReferralPanel extends VLayout {

	public ReferralPanel(final MapWidget mapWidget) {
		super(10);
		setSize("100%", "100%");
		setPadding(10);
		setOverflow(Overflow.AUTO);

		if (mapWidget.getMapModel().isInitialized()) {
			addEditor(mapWidget.getMapModel());
		} else {
			mapWidget.getMapModel().addMapModelHandler(new MapModelHandler() {

				public void onMapModelChange(MapModelEvent event) {
					addEditor(mapWidget.getMapModel());
				}
			});
		}
	}

	private void addEditor(MapModel mapModel) {
		VectorLayer layer = (VectorLayer) mapModel.getLayer("referralLayer");
		if (layer != null) {
			final FeatureAttributeEditor editor = new FeatureAttributeEditor(layer, false, new ReferralFormFactory());
			Feature feature = new Feature(layer);
			editor.setFeature(feature);

			HLayout buttonLayout = new HLayout(5);
			buttonLayout.setHeight(30);
			IButton button = new IButton("GetValue");
			button.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					Feature feature = editor.getFeature();
					GWT.log(feature.toString());
				}
			});
			buttonLayout.addMember(button);
			IButton disabledBtn = new IButton("Toggle editing");
			disabledBtn.addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					editor.setDisabled(!editor.isDisabled());
				}
			});
			buttonLayout.addMember(disabledBtn);

			addMember(buttonLayout);
			addMember(editor);
		}
	}
}