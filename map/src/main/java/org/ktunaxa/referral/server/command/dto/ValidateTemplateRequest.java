/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.ValidateTemplateCommand}.
 * 
 * @author Emiel Ackermann
 */
public class ValidateTemplateRequest extends AbstractTaskAttributesRequest {

	private static final long serialVersionUID = 100L;
	
	public static final String COMMAND = "command.email.ValidateTemplate";

	private String subject;
	private String body;

	/** No-arguments constructor. */
	public ValidateTemplateRequest() {
		super();
	}

	/**
	 * Set e-mail body template.
	 *
	 * @param body body template
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Get e-mail body template.
	 *
	 * @return template body template
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Set e-mail subject template.
	 *
	 * @param subject subject template
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Get e-mail subject template.
	 *
	 * @return subject template
	 */
	public String getSubject() {
		return subject;
	}

}
