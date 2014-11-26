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

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring bean of the reference layer service.
 * 
 * @author Pieter De Graef
 */
@Component
@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class ReferenceLayerServiceImpl implements ReferenceLayerService {

	@Autowired
	@Qualifier("postgisSessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<ReferenceLayerType> findLayerTypes() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM ReferenceLayerType");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<ReferenceLayer> findLayers() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM ReferenceLayer");
		return query.list();
	}
}