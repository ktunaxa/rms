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