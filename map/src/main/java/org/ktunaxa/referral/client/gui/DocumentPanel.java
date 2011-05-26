/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Temporary class for displaying documents...
 * 
 * @author Pieter De Graef
 */
public class DocumentPanel extends VLayout {

	private FeatureAttributeEditor editor;

	public DocumentPanel() {
		setWidth100();
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		editor = new FeatureAttributeEditor(referralLayer, false, new DocumentsFormFactory());
		editor.setFeature(referral);
		addMember(editor);
	}

	/**
	 * Factory for {@link DocumentsForm}.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class DocumentsFormFactory implements FeatureFormFactory<Canvas> {

		public FeatureForm<Canvas> createFeatureForm(VectorLayer layer) {
			return new DocumentsForm();
		}

	}

}