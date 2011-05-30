/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;

/**
 * Response object that sends all the necessary URLs to the client, so that it can use them in the menu.
 * 
 * @author Pieter De Graef
 */
public class GetUrlsResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private String mapDashboardBaseUrl;

	private String bpmDashboardBaseUrl;

	public GetUrlsResponse() {
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
}