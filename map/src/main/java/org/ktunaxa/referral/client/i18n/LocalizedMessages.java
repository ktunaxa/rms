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

package org.ktunaxa.referral.client.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * <p>
 * Localization messages for the GWT test samples.
 * </p>
 *
 * @author Jan De Moerloose
 */
public interface LocalizedMessages extends Messages {

	String mapTitle();
	
	String referralTitle(String referralId, String description);

	String referralAndTaskTitle(String referralId, String description, String task);

	String loadingNotice();
}
