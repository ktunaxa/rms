/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.dto;

import java.util.List;

import org.geomajas.command.dto.GetMapConfigurationResponse;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Response DTO object for {@link org.ktunaxa.referral.server.command.GetReferralMapCommand} command.
 * 
 * @author Jan De Moerloose
 */
public class GetReferralMapResponse extends GetMapConfigurationResponse {

	private static final long serialVersionUID = 100L;

	private String cmisBaseUrl;

	private List<ReferenceLayerTypeDto> layerTypes;

	private List<ReferenceLayerDto> layers;

	private TaskDto task;

	private Feature referral;

	public String getCmisBaseUrl() {
		return cmisBaseUrl;
	}
	
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

	public TaskDto getTask() {
		return task;
	}

	public void setTask(TaskDto task) {
		this.task = task;
	}

	public Feature getReferral() {
		return referral;
	}

	public void setReferral(Feature referral) {
		this.referral = referral;
	}

}
