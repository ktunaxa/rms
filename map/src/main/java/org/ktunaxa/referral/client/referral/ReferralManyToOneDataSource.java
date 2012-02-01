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

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.configuration.AttributeInfo;
import org.geomajas.gwt.client.widget.attribute.DefaultAttributeProvider;
import org.geomajas.gwt.client.widget.attribute.ManyToOneDataSource;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;

/**
 * Slightly adapted option data source to show the complete referral id in the drop-down box.
 * 
 * @author Jan De Moerloose
 * 
 */

public class ReferralManyToOneDataSource extends ManyToOneDataSource {

	public static final String LAND_REFERRAL_ID_FIELD = "land_referral_id";

	public ReferralManyToOneDataSource(AttributeInfo info, String serverLayerId) {
		super((AssociationAttributeInfo) info, new DefaultAttributeProvider(serverLayerId, info.getName()));
		DataSourceTextField field = new DataSourceTextField(LAND_REFERRAL_ID_FIELD, "Land Referral Id");
		addField(field);
	}

	protected void transformResponse(DSResponse response, DSRequest request, Object data) {
		super.transformResponse(response, request, data);
		for (Record record : response.getData()) {
			String referralId = ReferralUtil.createId(record);
			record.setAttribute(LAND_REFERRAL_ID_FIELD, referralId);
		}
	}

}
