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

import org.geomajas.security.BaseAuthorization;

import java.util.Set;

/**
 * Custom authorization class for the application.
 *
 * @author Joachim Van der Auwera
 */
public interface AppAuthorization extends BaseAuthorization {

	/**
	 * Get the business process roles for this user.
	 *
	 * @return list of bpm roles
	 */
	Set<String> getBpmRoles();

	/**
	 * Can this user administer the application?
	 *
	 * @return true when user is administrator
	 */
	boolean isAdmin();

}
