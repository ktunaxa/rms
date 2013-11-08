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
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.inmemory.ConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * In memory service for testing.
 * 
 * @author Jan De Moerloose
 * 
 */
public class InMemoryCmisService extends CmisServiceImpl {

	private final Logger log = LoggerFactory.getLogger(CmisConfig.class);

	@Autowired
	private CmisConfig config;

	private Repository repository;

	private Map<String, String> parameter = new HashMap<String, String>();

	private Session session;

	@PostConstruct
	public synchronized void initialize() {
		config = new CmisConfig();
		config.setFolder("Ktunaxa");
		config.setUrl("d/");
		config.setUrlBrowse("d/");

		parameter.put(SessionParameter.BINDING_TYPE, BindingType.LOCAL.value());

		// Session locale.
		parameter.put(SessionParameter.LOCALE_ISO3166_COUNTRY, "");
		parameter.put(SessionParameter.LOCALE_ISO639_LANGUAGE, "nl");
		parameter.put(SessionParameter.LOCALE_VARIANT, "BE");

		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS,
				"org.apache.chemistry.opencmis.client.runtime.repository.ObjectFactoryImpl");
		parameter.put(SessionParameter.LOCAL_FACTORY,
				"org.apache.chemistry.opencmis.inmemory.server.InMemoryServiceFactoryImpl");
		parameter.put(SessionParameter.REPOSITORY_ID, "Main Repository");
		parameter.put(ConfigConstants.REPOSITORY_ID, "Main Repository");

		// This supposes only one repository is available at the URL.
	}

	public synchronized Session createSession() {
		if (session == null) {
			checkRepository();
			session = repository.createSession();
		}
		return session;
	}

	private synchronized void checkRepository() {
		if (repository == null) {
			SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
			try {
				repository = sessionFactory.getRepositories(parameter).get(0);
			} catch (Exception e) {
				log.error("Error: Initialize CmisService failed: " + e.getMessage(), e);
			}
		}
	}

	public synchronized String getBaseUrl() {
		return super.getBaseUrl();
	}

	public synchronized Folder getWorkingFolder() throws IOException {
		return super.getWorkingFolder();
	}

	public synchronized String getDownloadUrl(Document document) {
		return getDocumentUrl(document, "save");
	}

	public synchronized String getDisplayUrl(Document document) {
		return getDocumentUrl(document, "open");
	}

	private synchronized String getDocumentUrl(Document document, String type) {
		String docId = document.getId();
		return config.getUrlBrowse() + "cmis/" + document.getContentStreamFileName() + "?id=" + docId + "&type=" + type;
	}

	public synchronized Document saveOrUpdate(String documentName, String mimeType, InputStream in, long contentLength,
			String... folderNames) throws IOException {
		return super.saveOrUpdate(documentName, mimeType, in, contentLength, folderNames);
	}

	public synchronized Document create(String documentName, String mimeType, InputStream in, long contentLength,
			String... folderNames) throws IOException {
		return super.create(documentName, mimeType, in, contentLength, folderNames);
	}

	protected synchronized Document create(String documentName, String mimeType, InputStream in, long contentLength,
			boolean canUpdate, String... folderNames) throws IOException {
		return super.create(documentName, mimeType, in, contentLength, canUpdate, folderNames);
	}

	public synchronized Document read(String documentName) throws IOException {
		return super.read(documentName);
	}

	public synchronized void update(String documentName, String mimeType, InputStream in) throws IOException {
		super.update(documentName, mimeType, in);
	}

	public synchronized void delete(String documentName) throws IOException {
		super.delete(documentName);
	}
}