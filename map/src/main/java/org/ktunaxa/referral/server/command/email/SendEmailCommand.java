/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.geomajas.command.Command;
import org.ktunaxa.referral.server.command.dto.SendEmailRequest;
import org.ktunaxa.referral.server.command.dto.SendEmailResponse;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

/**
 * Sends an email.
 * 
 * @author Emiel Ackermann
 *
 */
@Component
public class SendEmailCommand implements Command<SendEmailRequest, SendEmailResponse> {
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public SendEmailResponse getEmptyCommandResponse() {
		return new SendEmailResponse();
	}

	public void execute(SendEmailRequest request, SendEmailResponse response) throws Exception {
		final Map<String, String> mailVariables = request.getMailVariables();
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				//TODO What if there are several recipients per type. 
				//Is there a default separator in smart GWT, like ','?
				mimeMessage.setRecipient(Message.RecipientType.TO,
						new InternetAddress(mailVariables.get(KtunaxaConstant.Email.TO_NAME)));
				String cc = mailVariables.get(KtunaxaConstant.Email.CC_NAME);
				if (null != cc) {
					mimeMessage.setRecipient(Message.RecipientType.CC,
						new InternetAddress(cc));
				}
				String bcc = mailVariables.get(KtunaxaConstant.Email.BCC_NAME);
				if (null != bcc) {
					mimeMessage.setRecipient(Message.RecipientType.BCC,
						new InternetAddress(bcc));
				}
				mimeMessage.setFrom(new InternetAddress(mailVariables.get(KtunaxaConstant.Email.FROM_NAME)));
				mimeMessage.setSubject(mailVariables.get(KtunaxaConstant.Email.SUBJECT_NAME));
				mimeMessage.setText(mailVariables.get(KtunaxaConstant.Email.MESSAGE_NAME));
			}
		};
		mailSender.send(preparator);
	}
}

