/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.apache.http.client.methods.HttpGet;
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

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
		final String to = request.getTo();
		if (null == to) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "to");
		}

		response.setSuccess(false);
		final List<HttpGet> attachmentConnections = new ArrayList<HttpGet>();
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				log.debug("Build mime message");

				addRecipients(mimeMessage, Message.RecipientType.TO, to);
				addRecipients(mimeMessage, Message.RecipientType.CC, request.getCc());
				addRecipients(mimeMessage, Message.RecipientType.BCC, request.getBcc());
				addReplyTo(mimeMessage, request.getReplyTo());
				mimeMessage.setFrom(new InternetAddress(from));
				mimeMessage.setSubject(request.getSubject());
				//mimeMessage.setText(request.getText());

				List<String> attachments = request.getAttachmentUrls();
				MimeMultipart mp = new MimeMultipart();
				MimeBodyPart mailBody = new MimeBodyPart();
				mailBody.setText(request.getText());
				mp.addBodyPart(mailBody);
				if (null != attachments && !attachments.isEmpty()) {
					for (String url : attachments) {
						log.debug("add mime part for {}", url);
						MimeBodyPart part = new MimeBodyPart();
						String filename = url;
						int pos = filename.lastIndexOf('/');
						if (pos >= 0) {
							filename = filename.substring(pos + 1);
						}
						part.setFileName(filename);
						String fixedUrl = url;
						if (fixedUrl.startsWith("..")) {
							fixedUrl = "http://localhost:8080/" + fixedUrl.substring(2);
						}
						fixedUrl = fixedUrl.replace(" ", "%20");
						part.setDataHandler(new DataHandler(new URL(fixedUrl)));
						mp.addBodyPart(part);
					}
				}
				mimeMessage.setContent(mp);
				log.debug("message {}", mimeMessage);
			}
		};
		try {
			mailSender.send(preparator);
			cleanAttachmentConnection(attachmentConnections);
			log.debug("mail sent");
			response.setSuccess(true);
		} catch (MailException me) {
			log.error("Could not send e-mail", me);
		}
	}

	private void cleanAttachmentConnection(List<HttpGet> attachmentConnections) {
		for (HttpGet httpGet : attachmentConnections) {
			if (!httpGet.isAborted()) {
				httpGet.abort(); // assure low level resources are released
			}
		}
	}

	private void addReplyTo(MimeMessage mimeMessage, String replyTo) throws MessagingException {
		if (null != replyTo && replyTo.length() > 0) {
			List<Address> replyTos = new ArrayList<Address>();
			for (String part : replyTo.split("[,;\\s]")) {
				String address = part.trim();
				if (address.length() > 0) {
					replyTos.add(new InternetAddress(address));
				}
			}
			mimeMessage.setReplyTo(replyTos.toArray(new Address[replyTos.size()]));
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

