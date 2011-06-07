/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.dto.GetMapConfigurationRequest;

/**
 * Request DTO object for {@link org.ktunaxa.referral.server.command.GetReferralMapCommand} command.
 * 
 * @author Jan De Moerloose
 */
public class GetReferralMapRequest extends GetMapConfigurationRequest {
	public static final String COMMAND = "command.GetReferralMap";

	public GetReferralMapRequest(String mapId, String applicationId) {
		setMapId(mapId);
		setApplicationId(applicationId);
	}

	public GetReferralMapRequest() {		
	}
	
}
