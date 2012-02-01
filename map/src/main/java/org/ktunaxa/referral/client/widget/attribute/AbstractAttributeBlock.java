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

package org.ktunaxa.referral.client.widget.attribute;

import org.geomajas.layer.feature.attribute.AssociationValue;

import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * An {@link AbstractAttributeBlock} is a collapsible widget that shows a single attribute value in an
 * {@link AttributeBlockList}.
 * 
 * @author Jan De Moerloose
 * 
 */
public abstract class AbstractAttributeBlock extends VLayout {

	private AssociationValue value;

	public AbstractAttributeBlock(AssociationValue value) {
		this.value = value;
	}

	/**
	 * Expand the attribute block, showing all information.
	 */
	protected abstract void expand();

	/**
	 * Collapse the attribute block, showing a summary.
	 */
	protected abstract void collapse();

	protected abstract boolean valueEquals(AbstractAttributeBlock other);

	protected abstract boolean evaluate(String filter);

	public abstract void addEditHandler(ClickHandler clickHandler);

	public abstract void addDeleteHandler(ClickHandler clickHandler);
	
	public AssociationValue getValue() {
		return value;
	}

	public abstract void redrawValue();
	
	public abstract String getDeleteMessage();


}
