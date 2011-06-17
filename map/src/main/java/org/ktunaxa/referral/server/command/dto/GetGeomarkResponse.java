/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;
import org.geomajas.geometry.Geometry;

/**
 * Response data for a {@link org.ktunaxa.referral.server.command.GetGeomarkCommand}.
 * 
 * @author Jan De Moerloose
 * 
 */
public class GetGeomarkResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private Geometry geometry;

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

}
