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

	@PostConstruct
	public void initialize() {
		if (StringUtils.hasText(config.getProxyHost())) {
			System.setProperty("java.net.useSystemProxies", "true");
			System.setProperty("http.proxyHost", config.getProxyHost());
			System.setProperty("http.proxyPort", Integer.toString(config.getProxyPort()));
			System.setProperty("https.proxyHost", config.getProxyHost());
			System.setProperty("https.proxyPort", Integer.toString(config.getProxyPort()));
		}

		SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		parameter.put(SessionParameter.USER, config.getUserName());
		parameter.put(SessionParameter.PASSWORD, config.getPassword());
		if (config.getUrl().endsWith("/")) {
			parameter.put(SessionParameter.ATOMPUB_URL, config.getUrl() + "service/cmis");
		} else {
			parameter.put(SessionParameter.ATOMPUB_URL, config.getUrl() + "/service/cmis");
		}
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		// Session locale.
		parameter.put(SessionParameter.LOCALE_ISO3166_COUNTRY, "");
		parameter.put(SessionParameter.LOCALE_ISO639_LANGUAGE, "nl");
		parameter.put(SessionParameter.LOCALE_VARIANT, "BE");

		parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
		// parameter.put(SessionParameter.REPOSITORY_ID, "Main Repository");

		// This supposes only one repository is available at the URL.
		try {
			repository = sessionFactory.getRepositories(parameter).get(0);
		} catch (Exception e) {
			log.error("Error: Initialize CmisService failed: " + e.getMessage(), e);
		}
	}

	public Session createSession() {
		return repository.createSession();
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

	public String getDocumentUrl(Document document) {
		String url = config.getUrl();
		if (url.endsWith("/")) {
			url += "d/a/";
		} else {
			url += "/d/a/";
		}
		String docId = document.getId().replaceAll("://", "/");
		return url + docId + "/" + document.getContentStreamFileName();
	}

	public Document create(String documentName, String mimeType, InputStream in) throws IOException {
		Folder folder = getWorkingFolder();

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

	private Folder getOrCreateSubFolder(Folder parent, String name) {
		// Go over all children and see if one matches the folder we're looking for:
		for (CmisObject object : parent.getChildren()) {
			if (name.equalsIgnoreCase(object.getName())) {
				return (Folder) object;
			}
		}

		// If we reach here the folder does not exist. In that case, create it:
		Map<String, Object> newFolderProps = new HashMap<String, Object>();
		newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		newFolderProps.put(PropertyIds.NAME, name);
		return parent.createFolder(newFolderProps);
	}
}