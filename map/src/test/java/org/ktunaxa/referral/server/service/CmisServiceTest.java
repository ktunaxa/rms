/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
@ContextConfiguration(locations = {"/org/geomajas/spring/geomajasContext.xml", "/org/ktunaxa/referral/server/service/TestContext.xml" })
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
			document = service.create(documentName, mimeType, in);
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