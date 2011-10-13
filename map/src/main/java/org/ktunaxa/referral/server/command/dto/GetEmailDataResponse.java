/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandResponse;

/**
 * Response object for {@link org.ktunaxa.referral.server.command.email.GetEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class GetEmailDataResponse extends CommandResponse {

	private static final long serialVersionUID = 100L;

	private String from;
	private String subject;
	private String body;

	/**
	 * Get e-mail from address.
	 *
	 * @return from address
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Set e-mail from address.
	 *
	 * @param from from address
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Get e-mail subject.
	 *
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Set ee-mail subject.
	 *
	 * @param subject subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Get e-mail body.
	 *
	 * @return body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set e-mail body.
	 *
	 * @param body body
	 */
	public void setBody(String body) {
		this.body = body;
	}
}
