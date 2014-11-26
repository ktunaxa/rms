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
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("postgisSessionFactory")
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
				response.setCc(result.getCc());
				TemplateFiller filler = new TemplateFiller(task, attributes, result);
				if (request.isRaw()) {
					response.setSubject(result.getSubject());
					response.setBody(result.getStringContent());
				} else {
					response.setSubject(filler.getFilledSubject());
					response.setBody(filler.getFilledMessage());
				}
			}
		}
	}
}

