/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import java.util.List;
import java.util.Map;

import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.domain.Template;
import org.ktunaxa.referral.server.dto.TaskDto;
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

	public void execute(GetEmailDataRequest request, GetEmailDataResponse response) throws Exception {
		final String notifier = request.getNotifier();
		if (null == notifier) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "notifier");
		}
		final TaskDto task = request.getTask();
		if (null == task) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "task");
		}
		final Map<String, String> attributes = request.getAttributes();
		if (null == attributes) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "attributes");
		}

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Template where title = '" + notifier + "'");
		List list = query.list();
		if (!list.isEmpty()) {
			Template result = (Template) list.get(0);
			if (null != result) {
				response.setFrom(result.getMailSender());
				TemplateFiller filler = new TemplateFiller(task, attributes, result);
				response.setSubject(filler.getFilledSubject());
				response.setBody(filler.getFilledMessage());
			}
		}
	}
}

