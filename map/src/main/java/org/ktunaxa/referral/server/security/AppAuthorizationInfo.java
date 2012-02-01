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

import org.geomajas.plugin.staticsecurity.configuration.LayerAuthorizationInfo;
import org.geomajas.security.BaseAuthorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Configuration object which includes the custom policy.
 *
 * @author Joachim Van der Auwera
 */
public class AppAuthorizationInfo extends LayerAuthorizationInfo {

	// do not serialize to assure this makes no difference for caching tiles
	private transient Set<String> bpmRoles;
	private transient boolean admin;

	/**
	 * Set some sensible default values giving access to everything...
	 */
	public AppAuthorizationInfo() {
		List<String> all = new ArrayList<String>();
		all.add(".*");
		setCommandsInclude(all);
		setCreateAuthorizedLayersInclude(all);
		setDeleteAuthorizedLayersInclude(all);
		setToolsInclude(all);
		setUpdateAuthorizedLayersInclude(all);
		setVisibleLayersInclude(all);
	}

	/**
	 * Set the BPM roles for the user.
	 *
	 * @param bpmRoles BPM roles
	 */
	public void setBpmRoles(Set<String> bpmRoles) {
		this.bpmRoles = bpmRoles;
	}

	/**
	 * Get the BPM roles for this user.
	 *
	 * @return BPM roles
	 */
	Set<String> getBpmRoles() {
		return bpmRoles;
	}

	/**
	 * Can this user administer the application?
	 *
	 * @return true when user is administrator
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Can this user administer the application?
	 *
	 * @param admin true when user is administrator
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public BaseAuthorization getAuthorization() {
		return new AppAuthorizationImpl(this);
	}
}
