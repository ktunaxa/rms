/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.geomajas.command.CommandResponse;
import org.ktunaxa.referral.server.domain.Template;

/**
 * Response object for {@link org.ktunaxa.referral.server.command.email.GetEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class GetEmailDataResponse extends CommandResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000L;

	private Template template;

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Template getTemplate() {
		return template;
	}
}
