/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;

/**
 * Response object for {@link org.ktunaxa.referral.server.command.email.SendEmailCommand}.
 * 
 * @author Emiel Ackermann
 */
public class SendEmailResponse extends CommandResponse {

	private static final long serialVersionUID = 1000L;
	
	private boolean mailIsSent;

	public void setMailIsSent(boolean mailIsSent) {
		this.mailIsSent = mailIsSent;
	}

	public boolean mailIsSent() {
		return mailIsSent;
	}
	
	

}
