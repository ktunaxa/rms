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
package org.ktunaxa.referral.server.command.dto;

import java.util.List;
import java.util.Set;

import org.geomajas.command.dto.GetConfigurationResponse;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Response DTO object for {@link org.ktunaxa.referral.server.command.GetReferralMapCommand} command.
 * 
 * @author Jan De Moerloose
 * @author Joachim Van der Auwera
 */
public class GetReferralMapResponse extends GetConfigurationResponse {

	private static final long serialVersionUID = 100L;

	private String cmisBaseUrl;

	private List<ReferenceLayerTypeDto> layerTypes;

	private List<ReferenceLayerDto> layers;

	private TaskDto task;

	private Feature referral;

	private Set<String> bpmRoles;

	private boolean admin;

	/**
	 * Get the Alfresco CMIS service URL.
	 *
	 * @return CMIS URL
	 */
	public String getCmisBaseUrl() {
		return cmisBaseUrl;
	}

	/**
	 * Set the Alfresco CMIS service URL.
	 *
	 * @param cmisBaseUrl CMIS URL
	 */
	public void setCmisBaseUrl(String cmisBaseUrl) {
		this.cmisBaseUrl = cmisBaseUrl;
	}

	public List<ReferenceLayerTypeDto> getLayerTypes() {
		return layerTypes;
	}

	public void setLayerTypes(List<ReferenceLayerTypeDto> layerTypes) {
		this.layerTypes = layerTypes;
	}

	public List<ReferenceLayerDto> getLayers() {
		return layers;
	}

	public void setLayers(List<ReferenceLayerDto> layers) {
		this.layers = layers;
	}

	/**
	 * Get the task.
	 *
	 * @return task
	 */
	public TaskDto getTask() {
		return task;
	}

	/**
	 * Set the current task.
	 *
	 * @param task task
	 */
	public void setTask(TaskDto task) {
		this.task = task;
	}

	/**
	 * Get the current referral.
	 *
	 * @return referral
	 */
	public Feature getReferral() {
		return referral;
	}

	/**
	 * Referral object.
	 *
	 * @param referral referral
	 */
	public void setReferral(Feature referral) {
		this.referral = referral;
	}

	/**
	 * Roles for the BPM tasks.
	 *
	 * @return BPM roles
	 */
	public Set<String> getBpmRoles() {
		return bpmRoles;
	}

	/**
	 * Roles for the BPM tasks.
	 *
	 * @param bpmRoles BPM roles
	 */
	public void setBpmRoles(Set<String> bpmRoles) {
		this.bpmRoles = bpmRoles;
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

}
