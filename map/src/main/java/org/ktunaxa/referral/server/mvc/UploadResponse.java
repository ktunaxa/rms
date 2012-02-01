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
package org.ktunaxa.referral.server.mvc;

import java.util.HashMap;
import java.util.Map;

import org.ktunaxa.referral.server.service.KtunaxaConstant;

/**
 * Response object to be sent to the file upload client (typically as JSON).
 * 
 * @author Jan De Moerloose
 * 
 */
public class UploadResponse {

	private Map<String, Object> resultMap = new HashMap<String, Object>();

	private boolean success = true;

	/**
	 * Adds an object to the response.
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 */
	public void addObject(String name, Object value) {
		resultMap.put(name, value);
	}

	/**
	 * Sets the exception for an unsuccessful file upload.
	 * 
	 * @param e
	 */
	public void setException(Exception e) {
		resultMap.put(KtunaxaConstant.FORM_ERROR_MESSAGE, e.getMessage());
		success = false;
	}

	/**
	 * Returns the map of result objects.
	 * 
	 * @return map of objects
	 */
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	/**
	 * Returns whether the upload action was successful.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isSuccess() {
		return success;
	}

}
