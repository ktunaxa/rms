/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral;

import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.InternalFeature;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.Map;

/**
 * Utility code for easier report generation.
 *
 * @author Joachim Van der Auwera
 */
public final class ReportUtil {

	private ReportUtil() {
		// hide constructor
	}

	/**
	 * Build the referral id.
	 *
	 * @param referral for which to calculate the referral number
	 * @return the land referral id
	 */
	public static String referralId(InternalFeature referral) {
		Map<String, Attribute> attributes = referral.getAttributes();
		Integer primary = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_PRIMARY).getValue();
		Integer secondary = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_SECONDARY).getValue();
		Integer year = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_YEAR).getValue();
		Integer number = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_NUMBER).getValue();
		return referralId(primary, secondary, year, number);
	}

	/**
	 * Creates the id of the specified referral.
	 *
	 * @param primary primary
	 * @param secondary secondary
	 * @param year year
	 * @param number sequence number
	 * @return the land referral id
	 */
	public static String referralId(Integer primary, Integer secondary, Integer year, Integer number) {
		String referralId = primary == null ? "XXXX" : pad(primary.toString(), 4);
		referralId += "-" + (secondary == null ? "XX" : pad(secondary.toString(), 2));
		referralId += "/" + (year == null ? "XX" : pad(year.toString(), 2));
		referralId += "-" + (number == null ? "XXXX" : pad(number.toString(), 4));
		return referralId;
	}

	public static String pad(String s, int i) {
		int padLength = i - s.length();
		while (padLength-- > 0) {
			s = "0" + s;
		}
		return s;
	}

}
