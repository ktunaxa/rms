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

package org.ktunaxa.referral.server.security;

import org.geomajas.plugin.staticsecurity.configuration.AuthorizationInfo;
import org.geomajas.plugin.staticsecurity.configuration.UserInfo;
import org.geomajas.plugin.staticsecurity.security.StaticAuthenticationService;
import org.geomajas.plugin.staticsecurity.security.dto.UserFilter;
import org.ktunaxa.referral.server.security.dto.BpmRoleUserFilter;

/**
 * Overrides {@link StaticAuthenticationService} to filter on bpm roles.
 * 
 * @author Jan De Moerloose
 * 
 */
public class AppStaticAuthenticationService extends StaticAuthenticationService {

	@Override
	public boolean evaluate(UserFilter filter, UserInfo userInfo) {
		if (filter instanceof BpmRoleUserFilter) {
			BpmRoleUserFilter roleFilter = (BpmRoleUserFilter) filter;
			for (AuthorizationInfo auth : userInfo.getAuthorizations()) {
				if (auth instanceof AppAuthorizationInfo) {
					AppAuthorizationInfo appInfo = (AppAuthorizationInfo) auth;
					return appInfo.getBpmRoles().contains(roleFilter.getName());
				}
			}
		}
		return false;
	}

}
