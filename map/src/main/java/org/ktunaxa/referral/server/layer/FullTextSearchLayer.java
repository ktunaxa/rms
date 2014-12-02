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
package org.ktunaxa.referral.server.layer;

import java.util.Iterator;
import java.util.List;

import org.geomajas.configuration.SortType;
import org.geomajas.global.ExceptionCode;
import org.geomajas.layer.LayerException;
import org.geomajas.layer.hibernate.CriteriaVisitor;
import org.geomajas.layer.hibernate.HibernateFeatureModel;
import org.geomajas.layer.hibernate.HibernateLayer;
import org.geomajas.layer.hibernate.HibernateLayerException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.opengis.filter.Filter;

/**
 * Full test search layer, manages its own hibernate session because it depends on a different session factory and we
 * can only have 1 transaction manager in spring.
 * 
 * @author Jan De Moerloose
 *
 */
public class FullTextSearchLayer extends HibernateLayer {
	
	private int MAX_FEATURES = 1000;

	@Override
	public Iterator<?> getElements(Filter filter, int offset, int maxResultSize) throws LayerException {
		try {
			Session session = getSessionFactory().openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(getFeatureInfo().getDataSourceName());
			if (filter != null) {
				if (filter != Filter.INCLUDE) {
					CriteriaVisitor visitor = new FullTextCriteriaVisitor((HibernateFeatureModel) getFeatureModel(),
							getDateFormat());
					Criterion c = (Criterion) filter.accept(visitor, criteria);
					if (c != null) {
						criteria.add(c);
					}
				}
			}

			// Sorting of elements.
			if (getFeatureInfo().getSortAttributeName() != null) {
				if (SortType.ASC.equals(getFeatureInfo().getSortType())) {
					criteria.addOrder(Order.asc(getFeatureInfo().getSortAttributeName()));
				} else {
					criteria.addOrder(Order.desc(getFeatureInfo().getSortAttributeName()));
				}
			}

			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);			
			criteria.setMaxResults(MAX_FEATURES);

			List<?> list = criteria.list();
			session.close();
			return list.iterator();
		} catch (HibernateException he) {
			throw new HibernateLayerException(he, ExceptionCode.HIBERNATE_LOAD_FILTER_FAIL, getFeatureInfo()
					.getDataSourceName(), filter.toString());
		}
	}

}
