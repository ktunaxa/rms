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

package org.ktunaxa.referral.server.service;

import javax.annotation.PostConstruct;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.ktunaxa.referral.server.domain.Referral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Resets the referral count when the year has passed.
 * 
 * @author Jan De Moerloose
 * 
 */
@Component
public class ResetReferralCounterService implements PreInsertEventListener {

	private final Logger log = LoggerFactory.getLogger(ResetReferralCounterService.class);

	@Autowired
	private SessionFactoryImpl sessionFactoryImpl;

	@PostConstruct
	public void registerListeners() {
		sessionFactoryImpl.getEventListeners().setPreInsertEventListeners(
				new org.hibernate.event.PreInsertEventListener[] { this });
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		Object object = event.getEntity();
		if(object instanceof Referral) {
			// using the current session causes flush, followed by rg.hibernate.AssertionFailure: null id in
			// org.ktunaxa.referral.server.domain.Referral entry ?!!
			Session session = sessionFactoryImpl.openSession();
			try {
				SQLQuery q1 = session
						.createSQLQuery(
								"SELECT count(*) as cnt, to_char(current_date, 'yyyy') as yr "
										+ "FROM referral WHERE calendar_year = cast(to_char(current_date, 'yy')"
										+ " as integer)").addScalar("cnt", new IntegerType())
						.addScalar("yr", new StringType());
				Object[] result = (Object[]) q1.uniqueResult();
				Integer cnt = (Integer) result[0];
				String yr = (String) result[1];
				log.info("Found " + cnt + " referrals for the current year " + yr);
				if (cnt == 0) {
					log.info("No referrals found for the current year " + yr
							+ ", resetting sequence before adding the first referral");
					// in the (unlikely) case that multiple referrals are committed concurrently,
					// this may cause loss of one or more concurrent referrals because of duplicate key errors
					session.createSQLQuery("select setval('referral_number_seq',1,false)");
				}
			} finally {
				session.close();
			}
		}
		return false;
	}

}
