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
import org.ktunaxa.referral.client.referral.ReferralDetailForm;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel showing detail view of referral. Allows limited updates.
 * 
 * @author Jan De Moerloose
 */
public class DetailPanel extends VLayout {

	private FeatureAttributeEditor editor;

	public DetailPanel() {
		super(10);
		setSize("100%", "100%");
		setPadding(10);
		setOverflow(Overflow.AUTO);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		editor = new FeatureAttributeEditor(referralLayer, false, new ReferralDetailFormFactory());
		editor.setFeature(referral);
		HLayout buttonLayout = new HLayout(5);
		buttonLayout.setHeight(30);
		addMember(buttonLayout);
		addMember(editor);
	}

	/**
	 * Factory for {@link ReferralDetailForm}.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class ReferralDetailFormFactory implements FeatureFormFactory<DynamicForm> {

		public FeatureForm<DynamicForm> createFeatureForm(VectorLayer layer) {
			return new ReferralDetailForm(layer);
		}

	}

}