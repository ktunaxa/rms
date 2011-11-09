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
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.domain.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Retrieves the default data for an email.
 * 
 * @author Emiel Ackermann
 */
@Component
@Transactional(readOnly = true, rollbackFor = { Exception.class })
public class GetEmailDataCommand implements Command<GetEmailDataRequest, GetEmailDataResponse> {

	@Autowired
	private SessionFactory sessionFactory;

	public GetEmailDataResponse getEmptyCommandResponse() {
		return new GetEmailDataResponse();
	}

	@SuppressWarnings("rawtypes")
	public void execute(GetEmailDataRequest request, GetEmailDataResponse response) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Template where title = '" + request.getNotifier() + "'");
		List list = query.list();
		if (!list.isEmpty()) {
			Template result = (Template) list.get(0);
			if (null != result) {
				response.setFrom(result.getMailSender());
				TemplateFiller filler = new TemplateFiller(request.getTask(), result);
				response.setSubject(filler.getFilledSubject());
				response.setBody(filler.getFilledMessage());
			}
		}
	}
}

