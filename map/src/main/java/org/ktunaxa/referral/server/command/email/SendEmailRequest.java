/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.geomajas.command.CommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.SendEmailCommand}.
 * 
 * @author Emiel Ackermann
 *
 */
// Temporary Checkstyle solution
public final class SendEmailRequest implements CommandRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000L;
	
	public static final String COMMAND = "command.email.SendEmail";
	
	// Temporary Checkstyle solution
	private SendEmailRequest() {
	}

}
