/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;
import org.geomajas.layer.feature.Feature;

/**
 * Command response object which contains a referral.
 *
 * @author Joachim Van der Auwera
 */
public class GetReferralResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private Feature referral;

	/**
	 * Get the feature for the referral.
	 *
	 * @return referral feature.
	 */
	public Feature getReferral() {
		return referral;
	}

	/**
	 * Set the feature for the referral.
	 *
	 * @param referral referral feature.
	 */
	public void setReferral(Feature referral) {
		this.referral = referral;
	}
}
