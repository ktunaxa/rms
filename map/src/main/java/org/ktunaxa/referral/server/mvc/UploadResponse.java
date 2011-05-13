/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
