/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server;

import org.geomajas.configuration.SyntheticAttributeInfo;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.layer.feature.SyntheticAttributeBuilder;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.Map;

/**
 * Build the full id synthetic attribute.
 *
 * @author Joachim Van der Auwera
 */
public class FullIdAttributeBuilder implements SyntheticAttributeBuilder<String> {

	/** {@inheritDoc} */
	public Attribute<String> getAttribute(SyntheticAttributeInfo info, InternalFeature feature) {
		Map<String, Attribute> attributes = feature.getAttributes();
		Integer primary = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_PRIMARY).getValue();
		Integer secondary = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_SECONDARY).getValue();
		Integer year = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_YEAR).getValue();
		Integer number = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_NUMBER).getValue();
		return new StringAttribute(ReferralUtil.createId(primary, secondary, year, number));
	}
}
