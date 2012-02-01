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

