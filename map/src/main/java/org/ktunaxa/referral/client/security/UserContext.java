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

	public static UserContext getInstance() {
		return INSTANCE;
	}

	public void set(String user, String name) {
		this.user = user;
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public String getName() {
		return name;
	}
}
