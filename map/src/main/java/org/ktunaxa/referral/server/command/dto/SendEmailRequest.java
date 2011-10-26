/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.dto;

import org.geomajas.command.CommandRequest;

/**
 * Request object for {@link org.ktunaxa.referral.server.command.email.SendEmailCommand}.
 * 
 * @author Emiel Ackermann
 * @author Joachim Van der Auwera
 */
public class SendEmailRequest implements CommandRequest {

	private static final long serialVersionUID = 100L;

	public static final String COMMAND = "command.email.Send";

	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String replyTo;
	private String subject;
	private String text;

	/**
	 * From address for the e-mail. Required.
	 *
	 * @return from address
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * From address for the e-mail. Required.
	 *
	 * @param from from address
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * To address for the e-mail. Required.
	 *
	 * @return to address
	 */
	public String getTo() {
		return to;
	}

	/**
	 * To address for the e-mail. Required.
	 *
	 * @param to to address
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Cc addresses for the e-mail. Multiple values are separated by space, comma or semicolon.
	 *
	 * @return cc address
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * Cc addresses for the e-mail. Multiple values are separated by space, comma or semicolon.
	 *
	 * @param cc cc address
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * Bcc addresses for the e-mail. Multiple values are separated by space, comma or semicolon.
	 *
	 * @return bcc address
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * Bcc addresses for the e-mail. Multiple values are separated by space, comma or semicolon.
	 *
	 * @param bcc bcc address
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
	 * E-mail subject.
	 *
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * E-mail subject.
	 *
	 * @param subject subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * E-mail body text.
	 *
	 * @return e-mail body text
	 */
	public String getText() {
		return text;
	}

	/**
	 * E-mail body text.
	 *
	 * @param text e-mail body text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Reply-to address for the e-mail.
	 *
	 * @return reply-to address
	 */
	public String getReplyTo() {
		return replyTo;
	}

	/**
	 * Reply-to address for the e-mail.
	 *
	 * @param replyTo reply-to address
	 */
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

}
