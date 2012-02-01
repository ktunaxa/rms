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
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Implementation of the basic {@link CmisService} that uses a Spring configuration bean to acquire the correct CMIS
 * connection parameters.
 * 
 * @author Pieter De Graef
 */
@Component
public class CmisServiceImpl implements CmisService {

	private final Logger log = LoggerFactory.getLogger(CmisConfig.class);

	@Autowired
	private CmisConfig config;

	private Repository repository;

	private Map<String, String> parameter = new HashMap<String, String>();
	
	@PostConstruct
	public void initialize() {
		if (StringUtils.hasText(config.getProxyHost())) {
			System.setProperty("java.net.useSystemProxies", "true");
			System.setProperty("http.proxyHost", config.getProxyHost());
			System.setProperty("http.proxyPort", Integer.toString(config.getProxyPort()));
			System.setProperty("https.proxyHost", config.getProxyHost());
			System.setProperty("https.proxyPort", Integer.toString(config.getProxyPort()));
		}
		parameter.put(SessionParameter.USER, config.getUserName());
		parameter.put(SessionParameter.PASSWORD, config.getPassword());
		String url = config.getUrl();
		if (!url.endsWith("/")) {
			url += "/";
		}
		parameter.put(SessionParameter.ATOMPUB_URL, url + "service/cmis");

		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		// Session locale.
		parameter.put(SessionParameter.LOCALE_ISO3166_COUNTRY, "");
		parameter.put(SessionParameter.LOCALE_ISO639_LANGUAGE, "nl");
		parameter.put(SessionParameter.LOCALE_VARIANT, "BE");

		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
		// parameter.put(SessionParameter.REPOSITORY_ID, "Main Repository");

		// This supposes only one repository is available at the URL.
	}

	public Session createSession() {
		checkRepository();
		return repository.createSession();
	}

	private void checkRepository() {
		if (repository == null) {
			SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
			try {
				repository = sessionFactory.getRepositories(parameter).get(0);
			} catch (Exception e) {
				log.error("Error: Initialize CmisService failed: " + e.getMessage(), e);
			}
		}
	}

	public String getBaseUrl() {
		return config.getUrl();
	}

	public Folder getWorkingFolder() throws IOException {
		try {
			Session session = createSession();
			Folder folder = session.getRootFolder();
			String path = config.getFolder();
			StringTokenizer tokenizer = new StringTokenizer(path, "/");
			while (tokenizer.hasMoreTokens()) {
				String name = tokenizer.nextToken();
				folder = getOrCreateSubFolder(folder, name);
			}
			return folder;
		} catch (CmisConnectionException e) {
			throw new IOException("The server is unreachable: " + e.getMessage(), e);
		} catch (CmisRuntimeException e) {
			throw new IOException("CMIS server error: " + e.getMessage(), e);
		}
	}

	public String getDownloadUrl(Document document) {
		return getDocumentUrl(document, "d/a/");
	}

	public String getDisplayUrl(Document document) {
		return getDocumentUrl(document, "d/d/");
	}

	private String getDocumentUrl(Document document, String restCommand) {
		String docId = document.getId().replaceAll("://", "/");
		return config.getUrlBrowse() + restCommand + docId + "/" + document.getContentStreamFileName();
	}

	public Document create(String documentName, String mimeType, InputStream in, String... folderNames)
			throws IOException {
		Folder folder = getWorkingFolder();

		for (String folderName : folderNames) {
			folder = getOrCreateSubFolder(folder, folderName);
		}

		// Go over all children and see if the document already exists:
		for (CmisObject object : folder.getChildren()) {
			if (documentName.equals(object.getName())) {
				throw new IOException("Document '" + documentName + "' already exists.");
			}
		}

		// New create the actual document:
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		properties.put(PropertyIds.NAME, documentName);
		properties.put(PropertyIds.CREATED_BY, config.getUserName());
		properties.put(PropertyIds.CHECKIN_COMMENT, "Create new document.");

		ContentStreamImpl contentStream = new ContentStreamImpl();
		contentStream.setFileName(documentName);
		contentStream.setMimeType(mimeType);
		contentStream.setStream(in);

		return folder.createDocument(properties, contentStream, VersioningState.NONE);
	}

	public Document read(String documentName) throws IOException {
		if (documentName == null) {
			throw new IOException("No document name was passed when trying to read.");
		}
		Folder folder = getWorkingFolder();

		// Go over all children and see if one matches the document we're looking for:
		for (CmisObject object : folder.getChildren()) {
			if (documentName.equals(object.getName())) {
				return (Document) object;
			}
		}

		// Throw an exception if the document was not found. Don't return null.
		throw new IOException("Document '" + documentName + "' could not be found.");
	}

	public void update(String documentName, String mimeType, InputStream in) throws IOException {
		Document document = read(documentName);

		ContentStreamImpl contentStream = new ContentStreamImpl();
		contentStream.setFileName(documentName);
		contentStream.setMimeType(mimeType);
		contentStream.setStream(in);

		document.setContentStream(contentStream, true);
	}

	public void delete(String documentName) throws IOException {
		Document document = read(documentName);
		document.delete(false);
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private Folder getOrCreateSubFolder(Folder parent, String orgName) throws IOException {
		String name = orgName.replace('/', '_').replace('\\', '_'); // remove invalid characters

		// Go over all children and see if one matches the folder we're looking for:
		for (CmisObject object : parent.getChildren()) {
			if (name.equalsIgnoreCase(object.getName())) {
				if (object instanceof Folder) {
					return (Folder) object;
				} else {
					throw new IOException("Folder '" + name +
							"' cannot be created, file with that name already exists.");
				}
			}
		}

		// If we reach here the folder does not exist. In that case, create it:
		Map<String, Object> newFolderProps = new HashMap<String, Object>();
		newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		newFolderProps.put(PropertyIds.NAME, name);
		return parent.createFolder(newFolderProps);
	}
}