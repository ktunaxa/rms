package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

public class TestLDAPLoginRequest implements CommandRequest {

	public static final String COMMAND = "command.test.TestLDAPLogin";

	private String user;

	private String password;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
