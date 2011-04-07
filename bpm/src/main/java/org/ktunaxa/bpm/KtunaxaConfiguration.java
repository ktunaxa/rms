/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.bpm;

/**
 * Configuration settings for the Ktunaxa business flows.
 *
 * @author Joachim Van der Auwera
 */
public class KtunaxaConfiguration {

	public static final String QUERY_REFERRAL_ID = "r";

	private String mapDashboardBaseUrl = "http://localhost:8080/map/";
	private String bpmDashboardBaseUrl = "http://localhost:8080/activiti-explorer/";

	/**
	 * Get map dashboard base URL.
	 *
	 * @return base URL
	 */
	public String getMapDashboardBaseUrl() {
		return mapDashboardBaseUrl;
	}

	/**
	 * Set map dashboard base URL.
	 *
	 * @param mapDashboardBaseUrl base URL
	 */
	public void setMapDashboardBaseUrl(String mapDashboardBaseUrl) {
		this.mapDashboardBaseUrl = mapDashboardBaseUrl;
	}

	/**
	 * Get BPM dashboard base URL.
	 *
	 * @return base URL
	 */
	public String getBpmDashboardBaseUrl() {
		return bpmDashboardBaseUrl;
	}

	/**
	 * Set BPM dashboard base URL.
	 *
	 * @param bpmDashboardBaseUrl base URL
	 */
	public void setBpmDashboardBaseUrl(String bpmDashboardBaseUrl) {
		this.bpmDashboardBaseUrl = bpmDashboardBaseUrl;
	}

	public String getReferralUrl(String referralId) {
		return mapDashboardBaseUrl + "?" + QUERY_REFERRAL_ID + "=" + referralId;
	}
}
