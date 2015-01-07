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

package org.geomajas.plugin.staticsecurity.command.staticsecurity;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.annotation.Api;
import org.geomajas.command.Command;
import org.geomajas.plugin.staticsecurity.command.dto.LoginRequest;
import org.geomajas.plugin.staticsecurity.command.dto.LoginResponse;
import org.geomajas.plugin.staticsecurity.configuration.AuthorizationInfo;
import org.geomajas.plugin.staticsecurity.configuration.SecurityServiceInfo;
import org.geomajas.plugin.staticsecurity.configuration.UserInfo;
import org.geomajas.plugin.staticsecurity.security.AuthenticationService;
import org.geomajas.plugin.staticsecurity.security.AuthenticationTokenService;
import org.geomajas.security.Authentication;
import org.geomajas.security.BaseAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to obtain a login token for a user/password combination.
 * <p/>
 * When comparing passwords, it assures that the base64 encoding padding is not required to be used.
 *
 * @author Joachim Van der Auwera
 * @since 1.7.1
 */
@Api
@Component
public class LoginCommand implements Command<LoginRequest, LoginResponse> {

	@Autowired
	private SecurityServiceInfo securityServiceInfo;

	@Autowired
	private AuthenticationTokenService tokenService;

	@Override
	public LoginResponse getEmptyCommandResponse() {
		return new LoginResponse();
	}

	@Override
	public void execute(LoginRequest request, LoginResponse response) throws Exception {
		String login = request.getLogin();
		String password = request.getPassword();

		if (null == login || null == password || "".equals(login) || "".equals(password)) {
			// need both user name and password to login
			return;
		}

		for (AuthenticationService authenticationService : securityServiceInfo.getAuthenticationServices()) {
			final String convertedPass = authenticationService.convertPassword(login, password);

			UserInfo user = authenticationService.isAuthenticated(login, convertedPass);
			if (null != user) {
				// login successful
				Authentication authentication = new Authentication();
				authentication.setUserId(user.getUserId());
				authentication.setUserName(user.getUserName());
				authentication.setUserLocale(user.getUserLocale());
				authentication.setUserOrganization(user.getUserOrganization());
				authentication.setUserDivision(user.getUserDivision());
				authentication.setAuthorizations(getAuthorizations(user));
				response.setToken(tokenService.login(authentication));
				response.setUserId(user.getUserId());
				response.setUserName(user.getUserName());
				response.setUserOrganization(user.getUserOrganization());
				response.setUserDivision(user.getUserDivision());
				if (null != user.getUserLocale()) {
					response.setUserLocale(user.getUserLocale().toString());
				}
				break;
			}
		}
	}

	private BaseAuthorization[] getAuthorizations(UserInfo user) {
		List<BaseAuthorization> res = new ArrayList<BaseAuthorization>();
		List<AuthorizationInfo> ua = user.getAuthorizations();
		if (null != ua) {
			for (AuthorizationInfo ai : ua) {
				res.add(ai.getAuthorization());
			}
		}
		return res.toArray(new BaseAuthorization[res.size()]);
	}
}
