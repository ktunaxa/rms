/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.security;

import org.geomajas.plugin.staticsecurity.configuration.LayerAuthorizationInfo;
import org.geomajas.security.BaseAuthorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Configuration object which includes the custom policy.
 *
 * @author Joachim Van der Auwera
 */
public class AppAuthorizationInfo extends LayerAuthorizationInfo {

	// do not serialize to assure this makes no difference for caching tiles
	private transient Set<String> bpmRoles;
	private transient boolean admin;

	/**
	 * Set some sensible default values giving access to everything...
	 */
	public AppAuthorizationInfo() {
		List<String> all = new ArrayList<String>();
		all.add(".*");
		setCommandsInclude(all);
		setCreateAuthorizedLayersInclude(all);
		setDeleteAuthorizedLayersInclude(all);
		setToolsInclude(all);
		setUpdateAuthorizedLayersInclude(all);
		setVisibleLayersInclude(all);
	}

	/**
	 * Set the BPM roles for the user.
	 *
	 * @param bpmRoles BPM roles
	 */
	public void setBpmRoles(Set<String> bpmRoles) {
		this.bpmRoles = bpmRoles;
	}

	/**
	 * Get the BPM roles for this user.
	 *
	 * @return BPM roles
	 */
	Set<String> getBpmRoles() {
		return bpmRoles;
	}

	/**
	 * Can this user administer the application?
	 *
	 * @return true when user is administrator
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Can this user administer the application?
	 *
	 * @param admin true when user is administrator
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public BaseAuthorization getAuthorization() {
		return new AppAuthorizationImpl(this);
	}
}
