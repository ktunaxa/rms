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
 * Form for editing a document.
 * 
 * @author Jan De Moerloose
 * 
 */
public class DocumentForm extends DefaultFeatureForm {

	public DocumentForm(FeatureInfo documentsInfo, VectorLayer referralLayer) {
		super(documentsInfo, new DefaultAttributeProvider(referralLayer, KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME));
	}

}
