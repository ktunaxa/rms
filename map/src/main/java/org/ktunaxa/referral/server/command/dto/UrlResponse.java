/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;

/**
 * Command response which return the BPM dashboard URL.
 *
 * @author Joachim Van der Auwera
 */
public class UrlResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private String url;

	/**
	 * Get the URL of the BPM dashboard.
	 *
	 * @return BPM dashboard URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the URL for the BPM dashboard.
	 *
	 * @param url BPM dashboard URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
