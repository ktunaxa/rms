/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client;

/**
 * Utility for handling CMIS related things.
 *
 * @author Joachim Van der Auwera
 */
public final class CmisUtil {

	private static final String GUEST_ACCESS_PARAMETER = "guest=true";
	private static final String PARAMETER_SEPARATOR_FIRST = "?";
	private static final String PARAMETER_SEPARATOR_MORE = "&";

	private CmisUtil() {
		// hide constructor
	}

	/**
	 * Assure a document/URL uses guest security.
	 *
	 * @param url URL to "fix"
	 * @return fixed URL
	 */
	public static String addGuestAccess(String url) {
		if (url.contains(GUEST_ACCESS_PARAMETER)) {
			return url;
		} else if (url.contains(PARAMETER_SEPARATOR_FIRST)) {
			return url + PARAMETER_SEPARATOR_MORE + GUEST_ACCESS_PARAMETER;
		} else {
			return url + PARAMETER_SEPARATOR_FIRST + GUEST_ACCESS_PARAMETER;
		}
	}


}
