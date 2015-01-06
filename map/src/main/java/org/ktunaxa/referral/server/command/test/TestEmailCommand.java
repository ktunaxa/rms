package org.ktunaxa.referral.server.command.test;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.referral.server.command.dto.TestEmailRequest;
import org.ktunaxa.referral.server.command.dto.TestEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component(TestEmailRequest.COMMAND)
public class TestEmailCommand implements Command<TestEmailRequest, TestEmailResponse> {

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Override
	public TestEmailResponse getEmptyCommandResponse() {
		return new TestEmailResponse();
	}

	@Override
	public void execute(TestEmailRequest request, TestEmailResponse response) throws Exception {
		final String from = "rms@ktunaxa.org";
		final String to = request.getTo();
		if (null == to) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "to");
		}
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mimeMessage.setFrom(new InternetAddress(from));
				mimeMessage.setSubject("Test");
				mimeMessage.setText("Testing RMS");
			}
		};
		mailSender.send(preparator);
	}

}
