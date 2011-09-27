/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import java.util.Map;

import org.geomajas.command.CommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.SendEmailCommand}.
 * 
 * @author Emiel Ackermann
 */
public class SendEmailRequest implements CommandRequest {

	private static final long serialVersionUID = 1000L;

	public static final String COMMAND = "command.email.Send";

	private Map<String, String> mailVariables;

	public SendEmailRequest() {}

	public void setMailVariables(Map<String, String> mailVariables) {
		this.mailVariables = mailVariables;
	}

	public Map<String, String> getMailVariables() {
		return mailVariables;
	}

}
