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