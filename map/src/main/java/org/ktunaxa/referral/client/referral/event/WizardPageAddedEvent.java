/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event the signals the addition of a new page to a wizard.
 * 
 * @author Pieter De Graef
 */
public class WizardPageAddedEvent extends GwtEvent<WizardPageAddedHandler> {

	private int index;

	public WizardPageAddedEvent(int index) {
		this.index = index;
	}

	public Type<WizardPageAddedHandler> getAssociatedType() {
		return WizardPageAddedHandler.TYPE;
	}

	protected void dispatch(WizardPageAddedHandler handler) {
		handler.onWizardPageAdded(this);
	}

	public int getIndex() {
		return index;
	}
}