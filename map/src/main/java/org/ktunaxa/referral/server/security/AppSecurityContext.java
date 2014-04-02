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

package org.ktunaxa.referral.server.security;

import org.geomajas.internal.security.DefaultSecurityContext;
import org.geomajas.plugin.deskmanager.domain.BaseGeodesk;
import org.geomajas.plugin.deskmanager.domain.Geodesk;
import org.geomajas.plugin.deskmanager.domain.LayerModel;
import org.geomajas.plugin.deskmanager.domain.security.Territory;
import org.geomajas.plugin.deskmanager.domain.security.dto.Role;
import org.geomajas.plugin.deskmanager.security.DeskmanagerSecurityContext;
import org.geomajas.security.Authentication;
import org.geomajas.security.BaseAuthorization;
import org.hibernate.criterion.Criterion;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Custom security context for this application.
 *
 * @author Joachim Van der Auwera
 */
@Scope(value = "thread", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppSecurityContext extends DefaultSecurityContext implements AppAuthorization, DeskmanagerSecurityContext {

	// new authorization

	public Set<String> getBpmRoles() {
		Set<String> roles = new HashSet<String>();
		for (Authentication authentication : getSecurityServiceResults()) {
			for (BaseAuthorization authorization : authentication.getAuthorizations()) {
				if (authorization instanceof AppAuthorization) {
					AppAuthorization appAuthorization = (AppAuthorization) authorization;
					Set<String> authRoles = appAuthorization.getBpmRoles();
					if (null != authRoles) {
						roles.addAll(authRoles);
					}
				}
			}
		}
		return roles;
	}

	public boolean isAdmin() {
		for (Authentication authentication : getSecurityServiceResults()) {
			for (BaseAuthorization authorization : authentication.getAuthorizations()) {
				if (authorization instanceof AppAuthorization) {
					if (((AppAuthorization) authorization).isAdmin()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isGeodeskUseAllowed(String publicGeodeskId) {
		return true;
	}

	@Override
	public boolean saveAllowed(LayerModel layerModel) {
		return true;
	}

	@Override
	public boolean deleteAllowed(LayerModel layerModel) {
		return false;
	}

	@Override
	public boolean readAllowed(LayerModel lm) {
		return true;
	}

	@Override
	public Criterion getFilterLayerModels() {
		return null;
	}

	@Override
	public boolean readAllowed(BaseGeodesk blueprint) {
		return true;
	}

	@Override
	public boolean saveAllowed(BaseGeodesk blueprint) {
		return true;
	}

	@Override
	public boolean deleteAllowed(BaseGeodesk blueprint) {
		return true;
	}

	@Override
	public Criterion getFilterBlueprints() {
		return null;
	}

	@Override
	public boolean readAllowed(Geodesk geodesk) {
		return true;
	}

	@Override
	public boolean saveAllowed(Geodesk geodesk) {
		return true;
	}

	@Override
	public boolean deleteAllowed(Geodesk geodesk) {
		return true;
	}

	@Override
	public Criterion getFilterGeodesks() {
		return null;
	}

	@Override
	public boolean isShapeFileUploadAllowed(String clientLayerId) {
		return true;
	}

	@Override
	public Territory getTerritory() {
		Territory t = new Territory();
		t.setId(0);
		return t;
	}

	@Override
	public Role getRole() {
		return Role.ADMINISTRATOR;
	}

	@Override
	public String getFullName() {
		return "admin";
	}
	
	

}
