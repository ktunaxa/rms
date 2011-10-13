/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.referral.server.command.dto.SendEmailRequest;
import org.ktunaxa.referral.server.command.dto.SendEmailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Send an email.
 *
 * @author Emiel Ackermann
 * @author Joachim Van der Auwera
 */
@Component
public class SendEmailCommand implements Command<SendEmailRequest, SendEmailResponse> {

	private final Logger log = LoggerFactory.getLogger(SendEmailCommand.class);

	@Autowired
	private JavaMailSenderImpl mailSender;

	public SendEmailResponse getEmptyCommandResponse() {
		return new SendEmailResponse();
	}

	public void execute(final SendEmailRequest request, final SendEmailResponse response) throws Exception {
		final String from = request.getFrom();
		if (null == from) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "from");
		}
		final String to = request.getFrom();
		if (null == to) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "to");
		}

		response.setSuccess(false);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				addRecipients(mimeMessage, Message.RecipientType.TO, to);
				addRecipients(mimeMessage, Message.RecipientType.CC, request.getCc());
				addRecipients(mimeMessage, Message.RecipientType.BCC, request.getBcc());
				mimeMessage.setFrom(new InternetAddress(from));
				mimeMessage.setSubject(request.getSubject());
				mimeMessage.setText(request.getText());
			}
		};
		try {
			mailSender.send(preparator);
			response.setSuccess(true);
		} catch (MailException me) {
			log.error("Could not send e-mail", me);
		}
	}

	private void addRecipients(MimeMessage mimeMessage, Message.RecipientType recipientType, String recipients)
			throws MessagingException {
		if (null != recipients && recipients.length() > 0) {
			for (String part : recipients.split("[,;\\s]")) {
				String address = part.trim();
				if (address.length() > 0) {
					mimeMessage.setRecipient(recipientType, new InternetAddress(address));
				}
			}
		}
	}
}

