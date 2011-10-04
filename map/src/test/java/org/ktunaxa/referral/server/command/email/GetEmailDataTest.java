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
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for {@link GetEmailDataCommand}.
 *
 * @author Emiel Ackermann
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/org/geomajas/spring/geomajasContext.xml",
		"/org/ktunaxa/referral/server/HibernateContext.xml"})
public class GetEmailDataTest {

	@Autowired
	private CommandDispatcher dispatcher;

	@Test
	public void testGetEmailDataRequest() throws Exception {
		TaskDto task = new TaskDto();
		task.addVariable("referralId", "12345");
		task.addVariable("referralName", "Test referral");
		GetEmailDataRequest request = new GetEmailDataRequest(KtunaxaConstant.Email.LEVEL_0);
		request.setTask(task);
		GetEmailDataResponse response = (GetEmailDataResponse) dispatcher.execute(GetEmailDataRequest.COMMAND, request, null, "en");
		String from = response.getFrom();
		Assert.assertTrue("bla@ktunaxa.org".equals(from));
		String body = response.getBody();
		Assert.assertTrue("Referral 12345 Test referral\n\nWe have received this referral but do not think we need to take action to process it. For us it has engagement level 0.\n\nKind regards,\nKtunaxa Nation Council".equals(body));
	}

}

