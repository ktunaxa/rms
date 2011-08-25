/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.GwtEvent;
import org.geomajas.layer.feature.Feature;

/**
 * Event that is fired when the current referral has changed.
 *
 * @author Joachim Van der Auwera
 */
public class CurrentReferralChangedEvent  extends GwtEvent<CurrentReferralChangedHandler> {

	private Feature referral;

	/**
	 * Constructor with new current referral.
	 *
	 * @param referral current referral
	 */
	public CurrentReferralChangedEvent(Feature referral) {
		this.referral = referral;
	}

	/** {@inheritDoc} */
	public Type<CurrentReferralChangedHandler> getAssociatedType() {
		return CurrentReferralChangedHandler.TYPE;
	}

	/** {@inheritDoc} */
	protected void dispatch(CurrentReferralChangedHandler handler) {
		handler.onCurrentReferralChanged(this);
	}

	/**
	 * Get the new current referral.
	 *
	 * @return current referral
	 */
	public Feature getReferral() {
		return referral;
	}
}
