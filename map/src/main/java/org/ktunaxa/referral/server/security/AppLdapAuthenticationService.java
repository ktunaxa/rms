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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.geomajas.plugin.staticsecurity.configuration.AuthorizationInfo;
import org.geomajas.plugin.staticsecurity.configuration.NamedRoleInfo;
import org.geomajas.plugin.staticsecurity.ldap.LdapAuthenticationService;
import org.geomajas.plugin.staticsecurity.security.dto.UserFilter;
import org.ktunaxa.referral.server.security.dto.BpmRoleUserFilter;

import com.unboundid.ldap.sdk.Filter;

/**
 * Overrides {@link LdapAuthenticationService} to filter on bpm roles.
 * 
 * @author Jan De Moerloose
 * 
 */
public class AppLdapAuthenticationService extends LdapAuthenticationService {

	private Map<String, Set<String>> ldapRoleMapping;

	@Override
	public Filter convert(UserFilter filter) {
		if (filter instanceof BpmRoleUserFilter) {
			BpmRoleUserFilter roleFilter = (BpmRoleUserFilter) filter;
			List<Filter> filters = new ArrayList<Filter>();
			for (String ldapRole : ldapRoleMapping.keySet()) {
				if (ldapRoleMapping.get(ldapRole).contains(roleFilter.getName())) {
					filters.add(Filter.createEqualityFilter(getRolesAttribute(), ldapRole));
				}
			}
			return Filter.createORFilter(filters);
		} else {
			return super.convert(filter);
		}
	}

	@PostConstruct
	public void postConstruct() {
		ldapRoleMapping = new HashMap<String, Set<String>>();
		for (String roleName : getNamedRoles().keySet()) {
			if (!ldapRoleMapping.containsKey(roleName)) {
				ldapRoleMapping.put(roleName, new HashSet<String>());
			}
			for (NamedRoleInfo role : getNamedRoles().get(roleName)) {
				for (AuthorizationInfo authInfo : role.getAuthorizations()) {
					if (authInfo instanceof AppAuthorizationInfo) {
						AppAuthorizationInfo appInfo = (AppAuthorizationInfo) authInfo;
						if (appInfo.getBpmRoles() != null) {
							ldapRoleMapping.get(roleName).addAll(appInfo.getBpmRoles());
						}
					}
				}
			}
		}
	}

}
