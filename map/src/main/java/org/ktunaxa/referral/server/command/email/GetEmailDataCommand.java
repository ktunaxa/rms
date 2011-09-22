/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.geomajas.command.Command;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.ktunaxa.referral.server.domain.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Retrieves the default data for an email.
 * 
 * @author Emiel Ackermann
 *
 */
@Component
public class GetEmailDataCommand implements Command<GetEmailDataRequest, GetEmailDataResponse> {

	@Autowired
	private SessionFactory sessionFactory;

	public GetEmailDataResponse getEmptyCommandResponse() {
		return new GetEmailDataResponse();
	}

	public void execute(GetEmailDataRequest request, GetEmailDataResponse response) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Object result = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Template where title = :emailTitle ");
			query.setParameter("emailTitle", request.getNotifier());
			result = query.uniqueResult();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
			if (null != result && result instanceof Template) {
				response.setTemplate((Template) result);
			}
		}
	}

}
