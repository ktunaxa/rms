/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;

/**
 * Empty response object for {@link org.ktunaxa.referral.server.command.email.UpdateEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class UpdateEmailDataResponse extends CommandResponse {

	private static final long serialVersionUID = 1000L;
	private boolean updated;

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
}
