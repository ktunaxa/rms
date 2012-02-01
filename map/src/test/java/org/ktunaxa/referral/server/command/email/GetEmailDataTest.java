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
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;

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
		GetEmailDataRequest request = new GetEmailDataRequest();
		request.setNotifier(KtunaxaConstant.Email.LEVEL_0);
		request.setTask(task);
		request.setAttributes(new HashMap<String, String>());
		GetEmailDataResponse response = (GetEmailDataResponse) dispatcher.execute(
				GetEmailDataRequest.COMMAND, request, null, "en");
		String from = response.getFrom();
		assertThat(from).isEqualTo("bla@ktunaxa.org");
		String body = response.getBody();
		Assert.assertEquals("Referral 12345 Test referral\n\nWe have received this referral but do not think we " +
				"need to take action to process it. For us it has engagement level 0.\n\nKind regards,\n" +
				"Ktunaxa Nation Council", body);
	}

}

