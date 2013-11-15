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

import java.util.Date;
import java.util.HashMap;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.LongAttribute;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * Wizard data holding feature under construction.
 * 
 * @author Jan De Moerloose
 */
public class ReferralData {

	private Feature feature;

	private VectorLayer layer;

	public ReferralData(VectorLayer layer) {
		this.layer = layer;
		feature = new Feature(layer);
		// set defaults, TODO: make generic implementation calling FeatureModel.newInstance() on the server
		feature.setIntegerAttribute(KtunaxaConstant.ATTRIBUTE_PRIMARY, 3500);
		feature.setIntegerAttribute(KtunaxaConstant.ATTRIBUTE_SECONDARY, 10);
		int year = new Date().getYear() - 100;
		feature.setIntegerAttribute(KtunaxaConstant.ATTRIBUTE_YEAR, year);
		feature.setStringAttribute(KtunaxaConstant.ATTRIBUTE_EXTERNAL_PROJECT_ID, "-99");
		feature.setStringAttribute(KtunaxaConstant.ATTRIBUTE_EXTERNAL_FILE_ID, "-99");
		feature.setIntegerAttribute(KtunaxaConstant.ATTRIBUTE_ACTIVE_RETENTION_PERIOD, 2);
		feature.setIntegerAttribute(KtunaxaConstant.ATTRIBUTE_SEMI_ACTIVE_RETENTION_PERIOD, 5);
		feature.setBooleanAttribute(KtunaxaConstant.ATTRIBUTE_CONFIDENTIAL, false);
		Date nextMonth = new Date();
		CalendarUtil.addMonthsToDate(nextMonth, 1);
		feature.setDateAttribute(KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE, nextMonth);
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_TYPE, createValue(1));
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_FINAL_DISPOSITION, createValue(1));
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_APPLICATION_TYPE, createValue(1));
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_STATUS,
				createValue(1L, KtunaxaConstant.ATTRIBUTE_STATUS_TITLE, "New"));
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_DECISION,
				createValue(1L, KtunaxaConstant.ATTRIBUTE_DECISION_TITLE, "Unknown"));
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_PROVINCIAL_DECISION,
				createValue(1L, KtunaxaConstant.ATTRIBUTE_DECISION_TITLE, "Unknown"));
		feature.setIntegerAttribute(KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_PROVINCE, 1);
		feature.setIntegerAttribute(KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL, 1);
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_EXTERNAL_AGENCY_TYPE, createValue(1));
		feature.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_PRIORITY, createValue(1));
	}

	private AssociationValue createValue(long id) {
		return new AssociationValue(new LongAttribute(id), new HashMap<String, PrimitiveAttribute<?>>());
	}

	private AssociationValue createValue(long id, String displayName, String displayValue) {
		AssociationValue value = new AssociationValue(new LongAttribute(id),
				new HashMap<String, PrimitiveAttribute<?>>());
		value.setStringAttribute(displayName, displayValue);
		return value;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Feature getFeature() {
		return feature;
	}

	public VectorLayer getLayer() {
		return layer;
	}

}