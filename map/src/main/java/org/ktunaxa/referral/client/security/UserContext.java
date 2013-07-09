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

package org.ktunaxa.referral.client.security;

import org.ktunaxa.bpm.KtunaxaBpmConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * Static holder of the user context.
 *
 * @author Joachim Van der Auwera
 */
public final class UserContext {

	private static final UserContext INSTANCE = new UserContext();
	private String user = "???";
	private String name = "???";
	private Set<String> bpmRoles = new HashSet<String>();
	private boolean admin;

	private UserContext() {
		// hide constructor
	}

	/**
	 * Get the instance for this application.
	 *
	 * @return object instance
	 */
	public static UserContext getInstance() {
		return INSTANCE;
	}

	/**
	 * Initialise user context.
	 *
	 * @param user user login
	 * @param name user full name
	 */
	public void set(String user, String name) {
		this.user = user;
		this.name = name;
		bpmRoles.clear();
	}

	/**
	 * Get login of the logged in user.
	 *
	 * @return user login
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Get the name of the logged in user.
	 *
	 * @return user full name
	 */
	public String getName() {
		return name;
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

	/**
	 * Return whether the currently logged in user has data entry role.
	 *
	 * @return true when having data entry role
	 */
	public boolean isDataEntry() {
		return hasBpmRole(KtunaxaBpmConstant.ROLE_REFERRAL_MANAGER);
	}

	/**
	 * Return whether the currently logged in user is a referral manager.
	 *
	 * @return true when referral manager
	 */
	public boolean isReferralManager() {
		return hasBpmRole(KtunaxaBpmConstant.ROLE_REFERRAL_MANAGER);
	}

	/**
	 * Check whether the current user has a specific BPM role.
	 *
	 * @param role role to test
	 * @return true when current user has the given role
	 */
	public boolean hasBpmRole(String role) {
		return bpmRoles.contains(role);
	}

	/**
	 * Check whether the current user has a one of the specified BPM roles.
	 *
	 * @param roles roles to test
	 * @return true when current user has one of the given roles
	 */
	public boolean hasBpmRole(String... roles) {
		for (String role : roles) {
			if (hasBpmRole(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the current user has a one of the specified BPM roles.
	 *
	 * @param roles roles to test
	 * @return true when current user has one of the given roles
	 */
	public boolean hasBpmRole(Iterable<String> roles) {
		for (String role : roles) {
			if (hasBpmRole(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the BPM roles for this user.
	 *
	 * @param bpmRoles BPM roles for current user
	 */
	public void setBpmRoles(Set<String> bpmRoles) {
		this.bpmRoles.clear();
		this.bpmRoles.addAll(bpmRoles);
	}

	/**
	 * Is the current user a guest? A guest user has no BPM roles.
	 *
	 * @return true when current user is a guest
	 */
	public boolean isGuest() {
		return bpmRoles.isEmpty();
	}
}
