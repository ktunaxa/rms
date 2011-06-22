/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import org.geomajas.configuration.FeatureInfo;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeProvider;
import org.geomajas.gwt.client.widget.attribute.DefaultFeatureForm;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

/**
 * Form for editing comments.
 * 
 * @author Jan De Moerloose
 * 
 */
public class CommentForm extends DefaultFeatureForm {

	public CommentForm(FeatureInfo commentsInfo, VectorLayer referralLayer) {
		super(commentsInfo, new DefaultAttributeProvider(referralLayer, KtunaxaConstant.ATTRIBUTE_COMMENTS));
		getWidget().setColWidths("120", "*");
	}
}