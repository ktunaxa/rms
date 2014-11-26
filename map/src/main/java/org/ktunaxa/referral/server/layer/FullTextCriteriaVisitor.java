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

import java.text.DateFormat;

import org.geomajas.layer.hibernate.CriteriaVisitor;
import org.geomajas.layer.hibernate.HibernateFeatureModel;
import org.hibernate.criterion.Restrictions;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.opengis.filter.PropertyIsLike;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.PropertyName;

/**
 * Special handling of full text attribute search.
 * 
 * @author Jan De Moerloose
 *
 */
public class FullTextCriteriaVisitor extends CriteriaVisitor {

	public FullTextCriteriaVisitor(HibernateFeatureModel featureModel, DateFormat dateFormat) {
		super(featureModel, dateFormat);
	}

	@Override
	public Object visit(PropertyIsLike filter, Object userData) {
		Expression expression = filter.getExpression();
		if (expression instanceof PropertyName) {
			String propertyName = ((PropertyName) expression).getPropertyName();
			String value = filter.getLiteral();
			if (KtunaxaConstant.ATTRIBUTE_FULL_TEXT.equalsIgnoreCase(propertyName)) {
				return Restrictions.sqlRestriction(KtunaxaConstant.ATTRIBUTE_FULL_TEXT + " @@ " + "to_tsquery('"
						+ value + ":*')");
			} else {
				return super.visit(filter, userData);
			}
		} else {
			return super.visit(filter, userData);
		}
	}

}
