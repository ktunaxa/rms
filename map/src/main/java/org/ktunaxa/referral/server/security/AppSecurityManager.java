/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
