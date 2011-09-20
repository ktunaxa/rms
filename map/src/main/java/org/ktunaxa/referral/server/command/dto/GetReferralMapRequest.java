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

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.GetReferralMap";

	public GetReferralMapRequest(String mapId, String applicationId) {
		super(mapId, applicationId);
	}

	public GetReferralMapRequest() {		
	}
	
}
