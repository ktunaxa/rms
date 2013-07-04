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

package org.ktunaxa.referral.server.command.email;

import org.geomajas.command.Command;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.ktunaxa.referral.server.command.dto.UpdateEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.UpdateEmailDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Updates the default data for an email.
 * 
 * @author Emiel Ackermann
 */
@Component
@Transactional(readOnly = false, rollbackFor = { Exception.class })
public class UpdateEmailDataCommand implements Command<UpdateEmailDataRequest, UpdateEmailDataResponse> {

	@Autowired
	private SessionFactory sessionFactory;

	public UpdateEmailDataResponse getEmptyCommandResponse() {
		return new UpdateEmailDataResponse();
	}

	public void execute(UpdateEmailDataRequest request, UpdateEmailDataResponse response) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE Template SET subject= :subject, " +
				"mail_sender= :from, cc= :cc, string_content= :body WHERE title= :title");
		query.setString("subject", request.getSubject());
		query.setString("from", request.getFrom());
		query.setString("cc", request.getCc());
		query.setString("body", request.getBody());
		query.setString("title", request.getNotifier());
		response.setUpdated(1 == query.executeUpdate());
	}
}

