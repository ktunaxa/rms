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

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;

/**
 * Response object for {@link org.ktunaxa.referral.server.command.email.ValidateTemplateCommand}.
 * 
 * @author Emiel Ackermann
 */
public class ValidateTemplateResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private String corruptPlaceholder;
	private boolean valid;
	
	public String getCorruptPlaceholder() {
		return corruptPlaceholder;
	}
	
	public void setInvalidPlaceholder(String corruptPlaceholder) {
		this.corruptPlaceholder = corruptPlaceholder;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
