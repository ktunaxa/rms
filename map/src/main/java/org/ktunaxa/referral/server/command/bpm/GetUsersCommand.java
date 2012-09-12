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

package org.ktunaxa.referral.server.command.bpm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geomajas.command.Command;
import org.geomajas.plugin.staticsecurity.configuration.AuthorizationInfo;
import org.geomajas.plugin.staticsecurity.configuration.SecurityServiceInfo;
import org.geomajas.plugin.staticsecurity.configuration.UserInfo;
import org.geomajas.plugin.staticsecurity.security.AuthenticationService;
import org.geomajas.plugin.staticsecurity.security.UserDirectoryService;
import org.ktunaxa.referral.server.command.dto.GetUsersRequest;
import org.ktunaxa.referral.server.command.dto.GetUsersResponse;
import org.ktunaxa.referral.server.security.AppAuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command which allows you to list the users in a specific set of BPM roles.
 * 
 * @author Jan De Moerloose
 */
@Component
public class GetUsersCommand implements Command<GetUsersRequest, GetUsersResponse> {

	@Autowired
	private SecurityServiceInfo securityServiceInfo;

	/** {@inheritDoc} */
	public GetUsersResponse getEmptyCommandResponse() {
		return new GetUsersResponse();
	}

	/** {@inheritDoc} */
	public void execute(GetUsersRequest request, GetUsersResponse response) throws Exception {
		// just get all users and filter them on bpm roles
		// no easy way to map between both (TODO ?)
		Set<String> users = new HashSet<String>();
		for (AuthenticationService authenticationService : securityServiceInfo.getAuthenticationServices()) {
			if (authenticationService instanceof UserDirectoryService) {
				UserDirectoryService userService = (UserDirectoryService) authenticationService;
				List<UserInfo> userInfos = userService.getUsers(null, null);
				users.addAll(getFilteredUsers(userInfos, request.getRoles()));
			}
		}
		users.addAll(getFilteredUsers(securityServiceInfo.getUsers(), request.getRoles()));
		response.setUsers(users);
	}

	private Set<String> getFilteredUsers(List<UserInfo> users, Set<String> roles) {
		Set<String> result = new HashSet<String>();
		for (UserInfo user : users) {
			for (AuthorizationInfo authorization : user.getAuthorizations()) {
				if (authorization instanceof AppAuthorizationInfo) {
					Set<String> bpmRoles = ((AppAuthorizationInfo) authorization).getBpmRoles();
					for (String role : bpmRoles) {
						if (roles.contains(role)) {
							result.add(user.getUserId());
							break;
						}
					}
				}
			}
		}
		return result;
	}
}
