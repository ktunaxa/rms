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

package org.ktunaxa.referral.server.security.dto;

import org.geomajas.plugin.staticsecurity.security.dto.AbstractUserFilter;
import org.geomajas.plugin.staticsecurity.security.dto.UserFilter;
import org.geomajas.plugin.staticsecurity.security.dto.UserFilterVisitor;

/**
 * Filter on bpm roles.
 * 
 * @author Jan De Moerloose
 * 
 */
public class BpmRoleUserFilter extends AbstractUserFilter implements UserFilter {

	private String name;

	public BpmRoleUserFilter() {
	}

	public BpmRoleUserFilter(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object accept(UserFilterVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

}
