/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import java.util.List;

import org.geomajas.command.Command;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.ktunaxa.referral.server.command.dto.UpdateEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.UpdateEmailDataResponse;
import org.ktunaxa.referral.server.domain.Template;
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
				"mail_sender= :from, string_content= :body WHERE title= :title");
		query.setString("subject", request.getSubject());
		query.setString("from", request.getFrom());
		query.setString("body", request.getBody());
		query.setString("title", request.getNotifier());
		response.setUpdated(1 == query.executeUpdate());
	}
}

