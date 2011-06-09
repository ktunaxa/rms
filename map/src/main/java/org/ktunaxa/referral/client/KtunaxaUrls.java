/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client;

/**
 * Singleton for urls.
 * 
 * @author Jan De Moerloose
 * 
 */
public final class KtunaxaUrls {

	private static final KtunaxaUrls INSTANCE = new KtunaxaUrls();

	private String mapDashboardBaseUrl;

	private String bpmDashboardBaseUrl;

	private String cmisBaseUrl;

	private KtunaxaUrls() {

	}
	public static KtunaxaUrls getInstance() {
		return INSTANCE;
	}

	public String getMapDashboardBaseUrl() {
		return mapDashboardBaseUrl;
	}

	public void setMapDashboardBaseUrl(String mapDashboardBaseUrl) {
		this.mapDashboardBaseUrl = mapDashboardBaseUrl;
	}

	public String getBpmDashboardBaseUrl() {
		return bpmDashboardBaseUrl;
	}

	public void setBpmDashboardBaseUrl(String bpmDashboardBaseUrl) {
		this.bpmDashboardBaseUrl = bpmDashboardBaseUrl;
	}

	public String getCmisBaseUrl() {
		return cmisBaseUrl;
	}

	public void setCmisBaseUrl(String cmisBaseUrl) {
		this.cmisBaseUrl = cmisBaseUrl;
	}


}
