/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.server.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.springframework.beans.factory.annotation.Autowired;
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