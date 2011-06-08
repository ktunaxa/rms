/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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

	protected abstract void expand();

	protected abstract void collapse();

	protected abstract boolean valueEquals(AbstractAttributeBlock other);

	protected abstract boolean evaluate(String filter);

	public abstract void addEditHandler(ClickHandler clickHandler);

	public abstract void addDeleteHandler(ClickHandler clickHandler);
	
	public AssociationValue getValue() {
		return value;
	}

	public abstract void redrawValue();


}
