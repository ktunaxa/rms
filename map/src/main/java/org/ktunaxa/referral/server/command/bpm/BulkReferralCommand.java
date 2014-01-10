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

package org.ktunaxa.referral.server.command.bpm;

import org.geomajas.command.Command;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.CommandResponse;
import org.geomajas.security.SecurityContext;
import org.ktunaxa.referral.server.command.dto.BulkReferralRequest;
import org.ktunaxa.referral.server.command.dto.CloseReferralRequest;
import org.ktunaxa.referral.server.command.dto.DeleteReferralRequest;
import org.ktunaxa.referral.server.command.dto.FinishReferralRequest;
import org.ktunaxa.referral.server.command.dto.ResetReferralRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Execute a bulk command on a list of referrals.
 * 
 * @author Jan De Moerloose
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class BulkReferralCommand extends AbstractReferralCommand implements
		Command<BulkReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(BulkReferralCommand.class);

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private SecurityContext securityContext;

	public CommandResponse getEmptyCommandResponse() {
		return new CommandResponse();
	}

	public void execute(BulkReferralRequest request, CommandResponse response) throws Exception {
		log.info("Bulk operation on " + request.getReferralIds().size() + " referrals");
		String token = securityContext.getToken();
		for (String referralId : request.getReferralIds()) {
			if (CloseReferralRequest.COMMAND.equals(request.getCommandName())) {
				CloseReferralRequest r = new CloseReferralRequest();
				r.setReason(request.getReason());
				r.setReferralId(referralId);
				commandDispatcher.execute(request.getCommandName(), r, token, null);
			}
			if (ResetReferralRequest.COMMAND.equals(request.getCommandName())) {
				ResetReferralRequest r = new ResetReferralRequest();
				r.setReferralId(referralId);
				commandDispatcher.execute(request.getCommandName(), r, token, null);
			}
			if (FinishReferralRequest.COMMAND.equals(request.getCommandName())) {
				FinishReferralRequest r = new FinishReferralRequest();
				r.setReferralId(referralId);
				commandDispatcher.execute(request.getCommandName(), r, token, null);
			}
			if (DeleteReferralRequest.COMMAND.equals(request.getCommandName())) {
				DeleteReferralRequest r = new DeleteReferralRequest();
				r.setReferralId(referralId);
				commandDispatcher.execute(request.getCommandName(), r, token, null);
			}
		}
	}
}
