/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import org.geomajas.command.CommandDispatcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ktunaxa.referral.server.command.dto.SendEmailRequest;
import org.ktunaxa.referral.server.command.dto.SendEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for {@link org.ktunaxa.referral.server.command.email.SendEmailCommand}. Should not be included in normal tests.
 *
 * @author Joachim Van der Auwera
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/org/geomajas/spring/geomajasContext.xml",
		"/org/ktunaxa/referral/server/HibernateContext.xml", "/org/ktunaxa/referral/server/mail.xml"})
public class SendEmailTest {

	@Autowired
	private CommandDispatcher dispatcher;

	@Test
	public void testSendEmail() throws Exception {
		SendEmailRequest request = new SendEmailRequest();
		request.setTo("joachim@progs.be");
		request.setFrom("bla@ktunaxa.org");
		request.setSubject("Simple mail subject");
		request.setText("Mail body text, no attachments.");
		SendEmailResponse response = (SendEmailResponse) dispatcher.execute(request.COMMAND, request, null, null);
		Assert.assertTrue(response.isSuccess());
	}

	@Test
	public void testSendEmailWithAttachment() throws Exception {
		SendEmailRequest request = new SendEmailRequest();
		request.setTo("joachim@progs.be");
		request.setFrom("bla@ktunaxa.org");
		request.setReplyTo("bla2@ktunaxa.org");
		//request.setCc("joachim@triathlon98.com");
		//request.setBcc("joachim@geosparc.com");
		request.setSubject("Mail subject");
		request.setText("Mail body text");
		List<String> urls = new ArrayList<String>();
		urls.add("http://apps.geomajas.org/alfresco/d/d/workspace/SpacesStore/" +
				"525225c3-f1eb-43ed-9ac2-e21c870733df/3500-10_11-0017-finalReport.pdf");
		request.setAttachmentUrls(urls);
		SendEmailResponse response = (SendEmailResponse) dispatcher.execute(request.COMMAND, request, null, null);
		Assert.assertTrue(response.isSuccess());
	}

}

