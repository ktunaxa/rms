package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

public class TestEmailRequest implements CommandRequest {

	public static final String COMMAND = "command.test.TestEmail";

	private String to;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
