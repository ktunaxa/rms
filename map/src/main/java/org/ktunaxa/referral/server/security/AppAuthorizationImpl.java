/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.security;

import org.geomajas.plugin.staticsecurity.configuration.LayerAuthorization;

import java.util.Set;

/**
 * Custom authorization implementation.
 *
 * @author Joachim Van der Auwera
 */
public class AppAuthorizationImpl extends LayerAuthorization implements AppAuthorization {

	private AppAuthorizationInfo appAuthorizationInfo;

	protected AppAuthorizationImpl() {
		// for deserialization
	}

	public AppAuthorizationImpl(AppAuthorizationInfo info) {
		super(info);
		appAuthorizationInfo = info;
	}

	public Set<String> getBpmRoles() {
		return appAuthorizationInfo.getBpmRoles();
	}

	public boolean isAdmin() {
		return appAuthorizationInfo.isAdmin();
	}
}
