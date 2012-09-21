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

package org.ktunaxa.referral.server.command;

import java.util.Locale;

import org.geomajas.global.GeomajasException;

/**
 * Application-specific {@link GeomajasException} with dynamic message and no i18n support.
 * 
 * @author Jan De Moerloose
 * 
 */
public class KtunaxaException extends GeomajasException {

	private static final long serialVersionUID = 1L;

	public static final int CODE_REPORT_ERROR = 100000;

	public static final int CODE_ALFRESCO_ERROR = 100001;

	public static final int CODE_MAIL_ERROR = 100002;

	private String message;

	public KtunaxaException(int code, String message) {
		super(code);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getMessage(Locale locale) {
		return message;
	}

	@Override
	public String getShortMessage(Locale locale) {
		return message;
	}

}
