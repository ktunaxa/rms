/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Handler for {@link CurrentReferralChangedEvent}.
 *
 * @author Joachim Van der Auwera
 */
public interface CurrentReferralChangedHandler extends EventHandler {

	GwtEvent.Type<CurrentReferralChangedHandler> TYPE = new GwtEvent.Type<CurrentReferralChangedHandler>();

	void onCurrentReferralChanged(CurrentReferralChangedEvent event);
}
