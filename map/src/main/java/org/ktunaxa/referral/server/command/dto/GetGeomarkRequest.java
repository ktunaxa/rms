/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request data for a {@link org.ktunaxa.referral.server.command.GetGeomarkCommand}.
 * 
 * @author Jan De Moerloose
 * 
 */
public class GetGeomarkRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.GetGeomark";

	private String geomark;

	public String getGeomark() {
		return geomark;
	}

	public void setGeomark(String geomark) {
		this.geomark = geomark;
	}

}
