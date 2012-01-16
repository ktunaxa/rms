/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.GetEmailDataCommand}.
 * 
 * @author Emiel Ackermann
 */
public class GetEmailDataRequest extends AbstractTaskAttributesRequest {

	private static final long serialVersionUID = 100L;
	
	public static final String COMMAND = "command.email.GetEmailData";

	private String notifier;

	/** No-arguments constructor. */
	public GetEmailDataRequest() {
		super();
	}

	/**
	 * Set notifier, the e-mail template indicator.
	 *
	 * @param notifier notifier
	 */
	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	/**
	 * Get notifier, the e-mail template indicator.
	 *
	 * @return notifier
	 */
	public String getNotifier() {
		return notifier;
	}
}
