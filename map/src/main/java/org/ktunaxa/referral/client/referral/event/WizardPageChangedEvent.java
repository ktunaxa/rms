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