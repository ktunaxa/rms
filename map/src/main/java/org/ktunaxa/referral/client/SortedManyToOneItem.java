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
package org.ktunaxa.referral.client;

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.gwt.client.widget.attribute.AttributeProvider;
import org.geomajas.gwt.client.widget.attribute.DefaultManyToOneItem;

/**
 * Many to one item with sorted values.
 * 
 * @author Jan De Moerloose
 * 
 */
public class SortedManyToOneItem extends DefaultManyToOneItem {

	public SortedManyToOneItem() {

	}

	public SortedManyToOneItem(String name) {
		getItem().setName(name);
	}

	/** {@inheritDoc} */
	public void init(AssociationAttributeInfo attributeInfo, AttributeProvider attributeProvider) {
		super.init(attributeInfo, attributeProvider);
		if (attributeInfo.getFeature() != null) {
			String sortName = attributeInfo.getFeature().getSortAttributeName();
			String displayName = attributeInfo.getFeature().getDisplayAttributeName();
			getItem().setDisplayField(displayName);
			if (sortName != null) {
				getItem().setSortField(sortName);
			} else {
				getItem().setSortField(displayName);
			}
		}
	}

}
