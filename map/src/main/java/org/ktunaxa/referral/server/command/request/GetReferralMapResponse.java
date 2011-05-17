/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command.request;

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
 * 
 */
public class GetReferralMapResponse extends GetMapConfigurationResponse {

	private String mapDashboardBaseUrl;

	private String bpmDashboardBaseUrl;

	private List<ReferenceLayerTypeDto> layerTypes;

	private List<ReferenceLayerDto> layers;

	private TaskDto task;

	private Feature referral;

	public String getMapDashboardBaseUrl() {
		return mapDashboardBaseUrl;
	}

	public void setMapDashboardBaseUrl(String mapDashboardBaseUrl) {
		this.mapDashboardBaseUrl = mapDashboardBaseUrl;
	}

	public String getBpmDashboardBaseUrl() {
		return bpmDashboardBaseUrl;
	}

	public void setBpmDashboardBaseUrl(String bpmDashboardBaseUrl) {
		this.bpmDashboardBaseUrl = bpmDashboardBaseUrl;
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
