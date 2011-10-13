/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
	
	public void setCorruptPlaceholder(String corruptPlaceholder) {
		this.corruptPlaceholder = corruptPlaceholder;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
