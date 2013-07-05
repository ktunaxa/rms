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

import org.geomajas.command.CommandRequest;

import java.util.List;

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
	private List<String> attachmentUrls;
	
	private boolean sendMail;
	private boolean saveMail;
	private String referralId;

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

	/**
	 * List of URLs which need to be added as attachment.
	 *
	 * @return attachment URLs
	 */
	public List<String> getAttachmentUrls() {
		return attachmentUrls;
	}

	/**
	 * List of URLs which need to be added as attachment.
	 *
	 * @param attachmentUrls attachment URLs
	 */
	public void setAttachmentUrls(List<String> attachmentUrls) {
		this.attachmentUrls = attachmentUrls;
	}
	
	/**
	 * Should the e-mail be saved as a comment or not ?
	 *
	 * @return true when e-mail should be saved
	 */
	public boolean isSaveMail() {
		return saveMail;
	}
	
	/**
	 * Should the e-mail with the report be sent?
	 *
	 * @return true when mail should be sent
	 */
	public boolean isSendMail() {
		return sendMail;
	}

	/**
	 * Should the e-mail with the report be sent?
	 *
	 * @param sendMail true when mail should be sent
	 */
	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}

	/**
	 * Should the e-mail be saved as a comment or not ?
	 *
	 * @param saveMail true when e-mail should be saved
	 */
	public void setSaveMail(boolean saveMail) {
		this.saveMail = saveMail;
	}
	
	/**
	 * Full id for referral to close (eg "3500-12/10-201").
	 *
	 * @return referral id
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * Full id for referral to close (eg "3500-12/10-201").
	 *
	 * @param referralId referral id
	 */
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}


}
