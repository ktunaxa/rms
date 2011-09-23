/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.GetEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class GetEmailDataRequest implements CommandRequest {

	private static final long serialVersionUID = 1000L;
	
	public static final String COMMAND = "command.email.GetEmailData";

	private String notifier;

	public GetEmailDataRequest(String notifier) {
		this.notifier = notifier;
	}

	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	public String getNotifier() {
		return notifier;
	}
	
}
