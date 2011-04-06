/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.referral.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired when a new page has been made visible in the wizard.
 * 
 * @author Pieter De Graef
 */
public class WizardPageChangedEvent extends GwtEvent<WizardPageChangedHandler> {

	private int index;

	private int maximum;

	public WizardPageChangedEvent(int index, int maximum) {
		this.index = index;
		this.maximum = maximum;
	}

	public Type<WizardPageChangedHandler> getAssociatedType() {
		return WizardPageChangedHandler.TYPE;
	}

	protected void dispatch(WizardPageChangedHandler handler) {
		handler.onWizardPageChanged(this);
	}

	public int getIndex() {
		return index;
	}

	public int getMaximum() {
		return maximum;
	}
}