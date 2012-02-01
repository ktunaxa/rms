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
 * Event that marks a successful upload and recieval of the referral geometry by one of the three methods.
 * 
 * @author Jan De Moerloose
 * 
 */
public class GeometryUploadSuccessEvent extends GwtEvent<GeometryUploadHandler> {

	public Type<GeometryUploadHandler> getAssociatedType() {
		return GeometryUploadHandler.TYPE;
	}

	@Override
	protected void dispatch(GeometryUploadHandler handler) {
		handler.onUploadSuccess(this);
	}

}
