/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
}
