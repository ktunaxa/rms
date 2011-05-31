/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
