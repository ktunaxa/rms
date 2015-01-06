package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;

public class TestLDAPLoginResponse extends CommandResponse {

	private String userName;

	private String organisation;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

}
