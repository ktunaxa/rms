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

package org.ktunaxa.referral.server.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test cases for the CmisService.
 * 
 * @author Pieter De Graef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/org/geomajas/spring/geomajasContext.xml",
		"/org/ktunaxa/referral/server/service/TestContext.xml" })
public class CmisServiceTest {

	@Autowired
	private CmisService service;

	@Test
	public void testConnection() {
		Session session = service.createSession();
		Assert.assertNotNull(session);
	}

	@Test
	public void testWorkingFolder() throws IOException {
		Folder folder = service.getWorkingFolder();
		Assert.assertNotNull(folder);
	}

	@Test
	public void testCrudCycle() throws IOException {
		final String documentName = "my-test-doc.txt";
		final String mimeType = "text/html";

		InputStream in = getClass().getResourceAsStream("/org/ktunaxa/referral/server/service/test-document.txt");
		Assert.assertNotNull(in);

		// First we create a new document:
		Document document = null;
		try {
			document = service.create(documentName, mimeType, in, -1L);
		} catch (IOException e) {
			// If a previous test failed, then the document might still exist.
			service.delete(documentName);
		}
		Assert.assertNotNull(document);

		// Then we read the document again:
		document = service.read(documentName);
		Assert.assertNotNull(document);

		// Then we update the document:
		in = getClass().getResourceAsStream("/org/ktunaxa/referral/server/service/test-document-update.txt");
		Assert.assertNotNull(in);
		service.update(documentName, mimeType, in);

		// Then we delete the document again:
		service.delete(documentName);
	}
}