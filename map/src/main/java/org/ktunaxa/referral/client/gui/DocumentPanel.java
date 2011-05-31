/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.action.menu.SaveEditingAction;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel for editing documents.
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 */
public class DocumentPanel extends VLayout {

	private FeatureAttributeEditor editor;

	private VectorLayer referralLayer;

	public DocumentPanel() {
		setWidth100();
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		this.referralLayer = referralLayer;
		editor = new FeatureAttributeEditor(referralLayer, false, new DocumentsFormFactory());
		editor.setFeature(referral);
		addMember(editor);
		// add a push-up spacer
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight("*");
		addMember(spacer);
	}

	/**
	 * Factory for {@link DocumentsForm}.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class DocumentsFormFactory implements FeatureFormFactory<Canvas> {

		public FeatureForm<Canvas> createFeatureForm(VectorLayer layer) {
			DocumentsForm form = new DocumentsForm(layer);
			// auto-save
			form.addItemChangedHandler(new ItemChangedHandler() {

				public void onItemChanged(ItemChangedEvent event) {
					MapModel mapModel = referralLayer.getMapModel();
					mapModel.getFeatureEditor().startEditing(new Feature[] { editor.getFeature() },
							new Feature[] { editor.getFeature() });
					SaveEditingAction action = new SaveEditingAction(mapModel);
					action.onClick(null);
				}
			});
			return form;
		}

	}

}