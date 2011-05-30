/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.server.command;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.geomajas.command.Command;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.dto.GetMapConfigurationRequest;
import org.geomajas.command.dto.GetMapConfigurationResponse;
import org.geomajas.command.dto.SearchFeatureRequest;
import org.geomajas.command.dto.SearchFeatureResponse;
import org.geomajas.global.GeomajasConstant;
import org.geomajas.layer.feature.SearchCriterion;
import org.geomajas.security.SecurityContext;
import org.ktunaxa.bpm.KtunaxaConfiguration;
import org.ktunaxa.referral.server.command.dto.GetReferralMapRequest;
import org.ktunaxa.referral.server.command.dto.GetReferralMapResponse;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.ktunaxa.referral.server.domain.ReferenceLayerType;
import org.ktunaxa.referral.server.dto.ReferenceLayerDto;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;
import org.ktunaxa.referral.server.service.DtoConverterService;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.ktunaxa.referral.server.service.ReferenceLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to get the RFA map configuration and some additional data.
 * 
 * @author Jan De Moerloose
 * 
 */
@Component
public class GetReferralMapCommand implements Command<GetReferralMapRequest, GetReferralMapResponse> {

	public static final String ATTRIBUTE_PRIMARY = "primaryClassificationNumber";

	public static final String ATTRIBUTE_SECONDARY = "secondaryClassificationNumber";

	public static final String ATTRIBUTE_YEAR = "calendarYear";

	public static final String ATTRIBUTE_NUMBER = "number";

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private SecurityContext securityContext;

	@Autowired
	private KtunaxaConfiguration ktunaxaConfiguration;

	@Autowired
	private ReferenceLayerService referenceLayerService;

	@Autowired
	private DtoConverterService converterService;

	@Autowired
	private TaskService taskService;

	public GetReferralMapResponse getEmptyCommandResponse() {
		return new GetReferralMapResponse();
	}

	public void execute(GetReferralMapRequest request, GetReferralMapResponse response) throws Exception {
		GetMapConfigurationResponse original = (GetMapConfigurationResponse) commandDispatcher.execute(
				GetMapConfigurationRequest.COMMAND, request, securityContext.getToken(), null);
		response.setMapInfo(original.getMapInfo());

		// Add the urls to the response:
		response.setBpmDashboardBaseUrl(ktunaxaConfiguration.getBpmDashboardBaseUrl());
		response.setMapDashboardBaseUrl(ktunaxaConfiguration.getMapDashboardBaseUrl());

		// Add the layer types to the response:
		List<ReferenceLayerType> layerTypes = referenceLayerService.findLayerTypes();
		List<ReferenceLayerTypeDto> typeDtos = new ArrayList<ReferenceLayerTypeDto>(layerTypes.size());
		for (ReferenceLayerType layerType : layerTypes) {
			typeDtos.add(converterService.toDto(layerType));
		}
		response.setLayerTypes(typeDtos);

		// Add the layers to the response:
		List<ReferenceLayer> layers = referenceLayerService.findLayers();
		List<ReferenceLayerDto> dtos = new ArrayList<ReferenceLayerDto>(layers.size());
		for (ReferenceLayer layer : layers) {
			dtos.add(converterService.toDto(layer));
		}
		response.setLayers(dtos);

		// Add the referral to the response:
		if (request.getReferralId() != null) {
			SearchFeatureRequest searchFeatureRequest = new SearchFeatureRequest();
			searchFeatureRequest.setBooleanOperator("AND");
			searchFeatureRequest.setCriteria(createCriteria(request.getReferralId()));
			searchFeatureRequest.setCrs(KtunaxaConstant.MAP_CRS);
			searchFeatureRequest.setLayerId(KtunaxaConstant.REFERRAL_SERVER_LAYER_ID);
			searchFeatureRequest.setMax(1);
			searchFeatureRequest.setFeatureIncludes(GeomajasConstant.FEATURE_INCLUDE_ALL);
			SearchFeatureResponse searchFeatureResponse = (SearchFeatureResponse) commandDispatcher.execute(
					SearchFeatureRequest.COMMAND, searchFeatureRequest, securityContext.getToken(), null);
			if (searchFeatureResponse.getFeatures().length > 0) {
				response.setReferral(searchFeatureResponse.getFeatures()[0]);
			}
		}
		// Add the task to the response:
		if (request.getTaskId() != null) {
			Task task = taskService.createTaskQuery().executionId(request.getTaskId()).singleResult();
			if (task != null) {
				response.setTask(converterService.toDto(task));
			}
		}
	}

	private SearchCriterion[] createCriteria(String referralId) {
		String[] values = parse(referralId);
		SearchCriterion[] criteria = new SearchCriterion[4];
		criteria[0] = new SearchCriterion(ATTRIBUTE_PRIMARY, "=", values[0]);
		criteria[1] = new SearchCriterion(ATTRIBUTE_SECONDARY, "=", values[1]);
		criteria[2] = new SearchCriterion(ATTRIBUTE_YEAR, "=", values[2]);
		criteria[3] = new SearchCriterion(ATTRIBUTE_NUMBER, "=", values[3]);
		return criteria;
	}

	private String[] parse(String referralId) {
		String[] result = new String[4];
		int position = referralId.indexOf('-');
		result[0] = referralId.substring(0, position);
		String temp = referralId.substring(position + 1);
		position = temp.indexOf('/');
		result[1] = temp.substring(0, position);
		temp = temp.substring(position + 1);
		position = temp.indexOf('-');
		result[2] = temp.substring(0, position);
		result[3] = temp.substring(position + 1);
		return result;
	}

}
