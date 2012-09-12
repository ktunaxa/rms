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

package org.ktunaxa.referral.server.command.dto;

import java.util.HashSet;
import java.util.Set;

import org.geomajas.command.EmptyCommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.bpm.GetUsersCommand}.
 * 
 * @author Jan De Moerloose
 */
public class GetUsersRequest extends EmptyCommandRequest {

	private static final long serialVersionUID = 1100L;

	/**
	 * Command name for this request.
	 */
	public static final String COMMAND = "command.bpm.GetUsers";

	private Set<String> roles = new HashSet<String>();

	
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void addRole(String role) {
		roles.add(role);
	}

}
