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

import org.geomajas.internal.security.DefaultSecurityManager;
import org.geomajas.security.Authentication;
import org.geomajas.security.SavedAuthorization;
import org.geomajas.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Custom security context for this application. This needs to be overwritten as the SecurityManager builds/fills the
 * {@link org.geomajas.security.SecurityContext}.
 *
 * @author Joachim Van der Auwera
 */
public class AppSecurityManager extends DefaultSecurityManager {

	@Autowired
	private SecurityContext securityContext;

	/** @inheritDoc */
	public boolean setSecurityContext(String authenticationToken, List<Authentication> authentications) {
		if (!authentications.isEmpty()) {
			// build authorization and build thread local SecurityContext
			((AppSecurityContext) securityContext).setAuthentications(authenticationToken, authentications);
			return true;
		}
		return false;
	}

	/** @inheritDoc */
	public void clearSecurityContext() {
		((AppSecurityContext) securityContext).setAuthentications(null, null);
	}

	/** @inheritDoc */
	public void restoreSecurityContext(SavedAuthorization authorizations) {
		if (null == authorizations) {
			clearSecurityContext();
		} else {
			((AppSecurityContext) securityContext).restoreSecurityContext(authorizations);
		}
	}

}
