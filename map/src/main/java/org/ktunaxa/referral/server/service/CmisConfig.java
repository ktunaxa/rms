/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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

	private String repository;

	private String folder;

	private String proxyHost;

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
}