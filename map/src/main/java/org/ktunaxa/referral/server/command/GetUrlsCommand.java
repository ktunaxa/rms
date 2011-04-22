/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command;

import org.geomajas.command.Command;
import org.geomajas.command.EmptyCommandRequest;
import org.ktunaxa.bpm.KtunaxaConfiguration;
import org.ktunaxa.referral.server.command.request.GetUrlsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command for fetching base URLs.
 * 
 * @author Pieter De Graef
 */
@Component
public class GetUrlsCommand implements Command<EmptyCommandRequest, GetUrlsResponse> {

	@Autowired
	private KtunaxaConfiguration ktunaxaConfiguration;

	public GetUrlsResponse getEmptyCommandResponse() {
		return new GetUrlsResponse();
	}

	public void execute(EmptyCommandRequest request, GetUrlsResponse response) throws Exception {
		response.setBpmDashboardBaseUrl(ktunaxaConfiguration.getBpmDashboardBaseUrl());
		response.setMapDashboardBaseUrl(ktunaxaConfiguration.getMapDashboardBaseUrl());
	}
}