/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.request;

import org.geomajas.command.dto.GetMapConfigurationRequest;

/**
 * Request DTO object for {@link org.ktunaxa.referral.server.command.GetReferralMapCommand} command.
 * 
 * @author Jan De Moerloose
 *
 */
public class GetReferralMapRequest extends GetMapConfigurationRequest {
	public static final String COMMAND = "command.GetReferralMap";
	private String taskId;
	private String referralId;

	public GetReferralMapRequest(String mapId, String applicationId,
			String referralId, String taskId) {
		setMapId(mapId);
		setApplicationId(applicationId);
		this.taskId = taskId;
		this.referralId = referralId;
	}

	public GetReferralMapRequest() {		
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

}
