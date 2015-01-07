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

package org.ktunaxa.referral.server;

import java.util.Map;

import org.geomajas.configuration.SyntheticAttributeInfo;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.layer.feature.SyntheticAttributeBuilder;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

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
