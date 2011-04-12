/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.cmis.client.AlfrescoDocument;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test for adding tags to a document. (disabled for now)
 * 
 * @author Pieter De Graef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/org/ktunaxa/referral/server/service/TestContext.xml" })
public class CmisTagTest {

	@Test
	public void run() {

	}

	// @Test
	public void testCrudCycle() throws IOException {
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, "admin");
		parameter.put(SessionParameter.PASSWORD, "sp@rklingH2O");

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, "http://alfresco.geomajas.org/alfresco/");
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		// Session locale.
		parameter.put(SessionParameter.LOCALE_ISO3166_COUNTRY, "");
		parameter.put(SessionParameter.LOCALE_ISO639_LANGUAGE, "nl");
		parameter.put(SessionParameter.LOCALE_VARIANT, "BE");

		// set the object factory
		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

		// create session
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Session session = factory.getRepositories(parameter).get(0).createSession();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.NAME, "tagged-document");
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		// properties.put("my:imageClassification", "hello");

		InputStream in = getClass().getResourceAsStream("/org/ktunaxa/referral/server/service/test-document.txt");
		ContentStreamImpl contentStream = new ContentStreamImpl();
		contentStream.setFileName("tagged-document");
		contentStream.setMimeType("text/html");
		contentStream.setStream(in);

		Document doc = session.getRootFolder().createDocument(properties, contentStream, VersioningState.NONE);

		AlfrescoDocument alfDoc = (AlfrescoDocument) doc;
		alfDoc.addAspect("P:cm:taggable");
		// if (alfDoc.hasAspect("P:cm:titled")) {
		// Map<String, Object> propertiez = new HashMap<String, Object>();
		// List<String> tags = new ArrayList<String>(1);
		// tags.add("my-tag");
		// propertiez.put("cm:taggable", tags);
		// alfDoc.updateProperties(propertiez);
		// }
	}
}