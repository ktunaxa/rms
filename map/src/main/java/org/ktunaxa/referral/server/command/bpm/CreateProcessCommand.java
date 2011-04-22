/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.geomajas.command.Command;
import org.ktunaxa.referral.server.command.request.CreateProcessRequest;
import org.ktunaxa.referral.server.command.request.UrlResponse;

/**
 * ...
 *
 * @author Joachim Van der Auwera
 */
public class CreateProcessCommand implements Command<CreateProcessRequest, UrlResponse> {

	public UrlResponse getEmptyCommandResponse() {
		return null;  // @todo implement
	}

	public void execute(CreateProcessRequest createProcessRequest, UrlResponse urlResponse) throws Exception {
		// @todo implement
	}
}
