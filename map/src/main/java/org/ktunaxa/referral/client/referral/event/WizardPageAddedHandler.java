/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Handler for firing events that signal the addition of a new page to a wizard.
 * 
 * @author Pieter De Graef
 */
public interface WizardPageAddedHandler extends EventHandler {

	GwtEvent.Type<WizardPageAddedHandler> TYPE = new GwtEvent.Type<WizardPageAddedHandler>();

	void onWizardPageAdded(WizardPageAddedEvent event);
}