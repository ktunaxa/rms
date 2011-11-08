/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
		final String to = request.getFrom();
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
				mimeMessage.setText(request.getText());

				List<String> attachments = request.getAttachmentUrls();
				if (null != attachments && !attachments.isEmpty()) {
					MimeMultipart mp = new MimeMultipart();
					for (String url : attachments) {
						log.debug("add mime part for {}", url);
						HttpClient httpClient = new DefaultHttpClient();
						HttpGet httpGet = new HttpGet(url);
						attachmentConnections.add(httpGet);
						HttpResponse httpResponse = httpClient.execute(httpGet);
						mp.addBodyPart(new MimeBodyPart(httpResponse.getEntity().getContent()));
					}
					mimeMessage.setContent(mp);
					log.debug("message {}", mimeMessage);
				}
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

