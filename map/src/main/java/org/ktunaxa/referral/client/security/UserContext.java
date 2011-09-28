/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
	 * Return whether the currently logged in user is a referral administrator.
	 *
	 * @return true when referral admin
	 */
	public boolean isReferralAdmin() {
		return bpmRoles.contains(KtunaxaBpmConstant.ROLE_REFERRAL_MANAGER);
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
}
