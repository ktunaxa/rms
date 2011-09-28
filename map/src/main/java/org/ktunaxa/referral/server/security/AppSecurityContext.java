/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.security;

import org.geomajas.internal.security.DefaultSecurityContext;
import org.geomajas.security.Authentication;
import org.geomajas.security.BaseAuthorization;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom security context for this application.
 *
 * @author Joachim Van der Auwera
 */
@Scope(value = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppSecurityContext extends DefaultSecurityContext implements AppAuthorization {

	// new authorization

	public Set<String> getBpmRoles() {
		Set<String> roles = new HashSet<String>();
		for (Authentication authentication : getSecurityServiceResults()) {
			for (BaseAuthorization authorization : authentication.getAuthorizations()) {
				if (authorization instanceof AppAuthorization) {
					roles.addAll(((AppAuthorization) authorization).getBpmRoles());
				}
			}
		}
		return roles;
	}

}
