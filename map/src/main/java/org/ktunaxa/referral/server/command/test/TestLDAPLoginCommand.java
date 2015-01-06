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
