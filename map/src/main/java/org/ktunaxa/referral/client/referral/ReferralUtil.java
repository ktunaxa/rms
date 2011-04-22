/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

/**
 * Utility for referral id.
 * 
 * @author Jan De Moerloose
 * 
 */
public final class ReferralUtil {

	private ReferralUtil() {

	}

	/**
	 * Creates the id of the specified referral.
	 * 
	 * @param referral
	 *            the referral
	 * @return the land referral id
	 */
	public static String createId(Integer primary, Integer secondary, Integer year, Integer number ) {
		String referralId = primary == null ? "XXXX" : pad(primary.toString(), 4);
		referralId += "-" + (secondary == null ? "XX" : pad(secondary.toString(), 2));
		referralId += "/" + (year == null ? "XX" : pad(year.toString(), 2));
		referralId += "-" + (number == null ? "XXXX" : pad(number.toString(), 4));
		return referralId;
	}
	
	private static String pad(String s, int i) {
		int padLength = i - s.length();
		while (padLength-- > 0) {
			s = "0" + s;
		}
		return s;
	}

}
