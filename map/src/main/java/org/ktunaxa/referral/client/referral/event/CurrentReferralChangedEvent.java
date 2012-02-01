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
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Event that is fired when the current referral has changed.
 *
 * @author Joachim Van der Auwera
 */
public class CurrentReferralChangedEvent extends GwtEvent<CurrentReferralChangedHandler> {

	private Feature referral;
	private TaskDto task;

	/**
	 * Constructor with new current referral.
	 *
	 * @param referral current referral
	 * @param  task current task
	 */
	public CurrentReferralChangedEvent(Feature referral, TaskDto task) {
		this.referral = referral;
		this.task = task;
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

	/**
	 * Get the new current task.
	 *
	 * @return current task
	 */
	public TaskDto getTask() {
		return task;
	}
}
