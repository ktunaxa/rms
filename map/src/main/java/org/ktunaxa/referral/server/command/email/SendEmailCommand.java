/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.geomajas.command.Command;
import org.ktunaxa.referral.server.command.dto.SendEmailRequest;
import org.ktunaxa.referral.server.command.dto.SendEmailResponse;
import org.springframework.stereotype.Component;

/**
 * Sends an email.
 * 
 * @author Emiel Ackermann
 *
 */
@Component
public class SendEmailCommand implements Command<SendEmailRequest, SendEmailResponse> {
	
	public SendEmailResponse getEmptyCommandResponse() {
		return new SendEmailResponse();
	}

	public void execute(SendEmailRequest request, SendEmailResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
