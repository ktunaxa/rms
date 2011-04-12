/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Handler for managing the pages changes within a wizard. Receives events every time the visible page is switched.
 * 
 * @author Pieter De Graef
 */
public interface WizardPageChangedHandler extends EventHandler {

	GwtEvent.Type<WizardPageChangedHandler> TYPE = new GwtEvent.Type<WizardPageChangedHandler>();

	void onWizardPageChanged(WizardPageChangedEvent event);
}