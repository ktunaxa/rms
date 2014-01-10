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

import java.util.ArrayList;
import java.util.List;

import org.geomajas.command.CommandRequest;

/**
 * Request data for {@link org.ktunaxa.referral.server.command.bpm.BulkReferralCommand}.
 * 
 * @author Jan De Moerloose
 */
public class BulkReferralRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.bpm.BulkReferral";

	private List<String> referralIds = new ArrayList<String>();

	private String commandName;

	private String reason;

	/**
	 * List of ids for referrals to execute on (eg "3500-12/10-201").
	 * 
	 * @return referral id
	 */
	public List<String> getReferralIds() {
		return referralIds;
	}

	/**
	 * Set full ids for referral to execute on (eg "3500-12/10-201").
	 * 
	 * @param referralId referral id
	 */
	public void setReferralIds(List<String> referralIds) {
		this.referralIds = referralIds;
	}

	/**
	 * Get the command name.
	 * 
	 * @return the name
	 */
	public String getCommandName() {
		return commandName;
	}

	/**
	 * Set the command name.
	 * 
	 * @param commandName the name
	 */
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	
	public String getReason() {
		return reason;
	}

	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
