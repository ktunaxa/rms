/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.geomajas.layer.feature.SearchCriterion;

import com.smartgwt.client.data.Record;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.Map;

/**
 * Utility for referral id.
 * 
 * @author Jan De Moerloose
 */
public final class ReferralUtil {

	private ReferralUtil() {
		// hide constructor
	}

	/**
	 * Creates the id of the specified referral.
	 * 
	 * @param referral for which to calculate the referral number
	 * @return the land referral id
	 */
	public static String createId(Feature referral) {
		Map<String, Attribute> attributes = referral.getAttributes();
		Integer primary = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_PRIMARY).getValue();
		Integer secondary = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_SECONDARY).getValue();
		Integer year = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_YEAR).getValue();
		Integer number = (Integer) attributes.get(KtunaxaConstant.ATTRIBUTE_NUMBER).getValue();
		return createId(primary, secondary, year, number);
	}

	/**
	 * Creates the id of the specified referral.
	 *
	 * @param referral for which to calculate the referral number
	 * @return the land referral id
	 */
	public static String createId(org.geomajas.gwt.client.map.feature.Feature referral) {
		Integer primary = (Integer) referral.getAttributeValue(KtunaxaConstant.ATTRIBUTE_PRIMARY);
		Integer secondary = (Integer) referral.getAttributeValue(KtunaxaConstant.ATTRIBUTE_SECONDARY);
		Integer year = (Integer) referral.getAttributeValue(KtunaxaConstant.ATTRIBUTE_YEAR);
		Integer number = (Integer) referral.getAttributeValue(KtunaxaConstant.ATTRIBUTE_NUMBER);
		return createId(primary, secondary, year, number);
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
	 *            The referral ID, as created in the {@link #createId(Integer, Integer, Integer, Integer)} method.
	 * @return The CQL filter.
	 */
	public static String createFilter(String referralId) {
		String[] values = parse(referralId);
		return "((" + KtunaxaConstant.ATTRIBUTE_PRIMARY + " = " + values[0] + ") AND (" +
				KtunaxaConstant.ATTRIBUTE_SECONDARY + " = " + values[1] + ") AND (" + KtunaxaConstant.ATTRIBUTE_YEAR +
				" = " + values[2] + ") AND (" + KtunaxaConstant.ATTRIBUTE_NUMBER + " = " + values[3] + "))";
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
		criteria[0] = new SearchCriterion(KtunaxaConstant.ATTRIBUTE_PRIMARY, "=", values[0]);
		criteria[1] = new SearchCriterion(KtunaxaConstant.ATTRIBUTE_SECONDARY, "=", values[1]);
		criteria[2] = new SearchCriterion(KtunaxaConstant.ATTRIBUTE_YEAR, "=", values[2]);
		criteria[3] = new SearchCriterion(KtunaxaConstant.ATTRIBUTE_NUMBER, "=", values[3]);
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

	/**
	 * Pad a string representing a number with zero characters to reach the requested length.
	 * 
	 * @param s input string
	 * @param i requested length
	 * @return string with minimum length of i, padded by zeros on the left to reach that length
	 */
	public static String pad(String s, int i) {
		int padLength = i - s.length();
		while (padLength-- > 0) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * Full full referral id from a {@link Record} instance.
	 * 
	 * @param record record for the referral
	 * @return full referral id
	 */
	public static String createId(Record record) {
		return createId(record.getAttributeAsInt(KtunaxaConstant.ATTRIBUTE_PRIMARY),
				record.getAttributeAsInt(KtunaxaConstant.ATTRIBUTE_SECONDARY),
				record.getAttributeAsInt(KtunaxaConstant.ATTRIBUTE_YEAR),
				record.getAttributeAsInt(KtunaxaConstant.ATTRIBUTE_NUMBER));
	}

	/**
	 * Get the full year from the referral id.
	 *
	 * @param referralId full referral id (e.g. "3500-10/11-0017")
	 * @return year, e.g. "2011"
	 */
	public static String getYear(String referralId) {
		return "20" + parse(referralId)[2];
	}
}