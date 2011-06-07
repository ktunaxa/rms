/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.security;

/**
 * Static holder of the user context.
 *
 * @author Joachim Van der Auwera
 */
public final class UserContext {

	private static final UserContext INSTANCE = new UserContext();
	private String user = "KtBpmAdmin";
	private String name = "Testing admin user";

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
		return true;  // @todo @sec proper role check
	}
}
