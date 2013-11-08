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

/**
 * Configuration object for the OpenCMIS service. We use such a bean to connect to the correct CMIS server.
 * 
 * @author Pieter De Graef
 */
public class CmisConfig {

	private String userName;

	private String password;

	private String url;

	private String urlBrowse;

	private String repository;

	private String folder;

	private String proxyHost;
	
	private String objectFactoryClass = "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl";
	
	private String atomPubExtension = "service/cmis";

	private int proxyPort;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * CMIS URL prefix used when passing URLs to the browser.
	 *
	 * @return CMIS URL prefix
	 */
	public String getUrlBrowse() {
		return urlBrowse;
	}

	/**
	 * CMIS URL prefix used when passing URLs to the browser.
	 *
	 * @param urlBrowse CMIS URL prefix
	 */
	public void setUrlBrowse(String urlBrowse) {
		this.urlBrowse = urlBrowse;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
	
	public String getObjectFactoryClass() {
		return objectFactoryClass;
	}
	
	public void setObjectFactoryClass(String objectFactoryClass) {
		this.objectFactoryClass = objectFactoryClass;
	}

	
	public String getAtomPubExtension() {
		return atomPubExtension;
	}

	
	public void setAtomPubExtension(String atomPubExtension) {
		this.atomPubExtension = atomPubExtension;
	}
	
	
}