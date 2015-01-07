/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ktunaxa.referral.server.command.test;

import org.geomajas.command.Command;
import org.geomajas.plugin.staticsecurity.configuration.SecurityServiceInfo;
import org.geomajas.plugin.staticsecurity.configuration.UserInfo;
import org.geomajas.plugin.staticsecurity.ldap.LdapAuthenticationService;
import org.geomajas.plugin.staticsecurity.security.AuthenticationService;
import org.ktunaxa.referral.server.command.dto.TestLDAPLoginRequest;
import org.ktunaxa.referral.server.command.dto.TestLDAPLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Jan De Moerloose
 *
 */
@Component(TestLDAPLoginRequest.COMMAND)
public class TestLDAPLoginCommand implements Command<TestLDAPLoginRequest, TestLDAPLoginResponse> {

	@Autowired
	private SecurityServiceInfo securityServiceInfo;

	@Override
	public TestLDAPLoginResponse getEmptyCommandResponse() {
		return new TestLDAPLoginResponse();
	}

	@Override
	public void execute(TestLDAPLoginRequest request, TestLDAPLoginResponse response) throws Exception {
		for (AuthenticationService authenticationService : securityServiceInfo.getAuthenticationServices()) {
			if (authenticationService instanceof LdapAuthenticationService) {
				final String convertedPass = authenticationService.convertPassword(request.getUser(),
						request.getPassword());
				UserInfo user = authenticationService.isAuthenticated(request.getUser(), convertedPass);
				if (null != user) {
					response.setUserName(user.getUserName());
					response.setOrganisation(user.getUserOrganization());
				}
			}
		}

	}

}
