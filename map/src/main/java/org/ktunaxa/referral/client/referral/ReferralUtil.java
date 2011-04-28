/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.layer.feature.SearchCriterion;

/**
 * Utility for referral id.
 * 
 * @author Jan De Moerloose
 * 
 */
public final class ReferralUtil {

	public static final String ATTRIBUTE_PRIMARY = "primaryClassificationNumber";

	public static final String ATTRIBUTE_SECONDARY = "secondaryClassificationNumber";

	public static final String ATTRIBUTE_YEAR = "calendarYear";

	public static final String ATTRIBUTE_NUMBER = "number";

	private ReferralUtil() {

	}

	/**
	 * Creates the id of the specified referral.
	 * 
	 * @param referral
	 *            the referral
	 * @return the land referral id
	 */
	public static String createId(Integer primary, Integer secondary, Integer year, Integer number) {
		String referralId = primary == null ? "XXXX" : pad(primary.toString(), 4);
		referralId += "-" + (secondary == null ? "XX" : pad(secondary.toString(), 2));
		referralId += "/" + (year == null ? "XX" : pad(year.toString(), 2));
		referralId += "-" + (number == null ? "XXXX" : pad(number.toString(), 4));
		return referralId;
	}

	/**
	 * Create a CQL filter from a referral ID.
	 * 
	 * @param referralId
	 *            The referral ID, as created in the <code>createId</code> method.
	 * @return The CQL filter.
	 */
	public static String createFilter(String referralId) {
		String[] values = parse(referralId);
		return "((" + ATTRIBUTE_PRIMARY + " = " + values[0] + ") AND (" + ATTRIBUTE_SECONDARY + " = " + values[1]
				+ ") AND (" + ATTRIBUTE_YEAR + " = " + values[2] + ") AND (" + ATTRIBUTE_NUMBER + " = " + values[3]
				+ "))";
	}

	/**
	 * Create a list of criterion objects from the referral ID.
	 * 
	 * @param referralId
	 *            The referral ID, as created in the <code>createId</code> method.
	 * @return List of criterion objects that can be used in a search command.
	 */
	public static SearchCriterion[] createCriteria(String referralId) {
		String[] values = parse(referralId);
		SearchCriterion[] criteria = new SearchCriterion[4];
		criteria[0] = new SearchCriterion(ATTRIBUTE_PRIMARY, "=", values[0]);
		criteria[1] = new SearchCriterion(ATTRIBUTE_SECONDARY, "=", values[1]);
		criteria[2] = new SearchCriterion(ATTRIBUTE_YEAR, "=", values[2]);
		criteria[3] = new SearchCriterion(ATTRIBUTE_NUMBER, "=", values[3]);
		return criteria;
	}

	private static String[] parse(String referralId) {
		String[] result = new String[4];
		int position = referralId.indexOf('-');
		result[0] = referralId.substring(0, position);
		String temp = referralId.substring(position + 1);
		position = temp.indexOf('/');
		result[1] = temp.substring(0, position);
		temp = temp.substring(position + 1);
		position = temp.indexOf('-');
		result[2] = temp.substring(0, position);
		result[3] = temp.substring(position + 1);
		return result;
	}

	private static String pad(String s, int i) {
		int padLength = i - s.length();
		while (padLength-- > 0) {
			s = "0" + s;
		}
		return s;
	}
}