/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.bpm;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.geomajas.command.Command;
import org.geomajas.command.CommandResponse;
import org.geomajas.geometry.Crs;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.layer.feature.attribute.ManyToOneAttribute;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.geomajas.security.SecurityContext;
import org.geomajas.service.FilterService;
import org.geomajas.service.GeoService;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.command.dto.CloseReferralRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Stop the process for the current given referral and mark it as stopped.
 *
 * @author Joachim Van der Auwera
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class CloseReferralCommand implements Command<CloseReferralRequest, CommandResponse> {

	private final Logger log = LoggerFactory.getLogger(CloseReferralCommand.class);

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private SecurityContext securityContext;

	@Autowired
	private VectorLayerService vectorLayerService;

	@Autowired
	private FilterService filterService;

	@Autowired
	private GeoService geoService;

	public CommandResponse getEmptyCommandResponse() {
		return new CommandResponse();
	}

	public void execute(CloseReferralRequest request, CommandResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().
				variableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId).list();
		if (!processInstances.isEmpty()) {
			// find referral, update status and set reason
			Crs crs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
			List<InternalFeature> features = vectorLayerService.getFeatures(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID,
					crs,
					filterService.parseFilter(ReferralUtil.createFilter(referralId)), null,
					VectorLayerService.FEATURE_INCLUDE_ATTRIBUTES);
			if (features.size() != 1) {
				if (features.isEmpty()) {
					log.error("Process found but not referral for {}.", referralId);
				} else {
					log.error("Multiple ({}) referrals found for id {}.", features.size(), referralId);
				}
			} else {
				InternalFeature orgReferral = features.get(0);
				InternalFeature referral = orgReferral.clone();
				Map<String, Attribute> attributes = referral.getAttributes();
				List<InternalFeature> newFeatures = new ArrayList<InternalFeature>();
				newFeatures.add(referral);
				List<Attribute<?>> statusses = vectorLayerService.getAttributes(
						KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, KtunaxaConstant.ATTRIBUTE_STATUS,
						filterService.createTrueFilter());
				for (Attribute<?> status : statusses) {
					if (status instanceof ManyToOneAttribute) {
						if (KtunaxaConstant.STATUS_STOPPED.equals(((ManyToOneAttribute) status).getValue().
								getAttributeValue(KtunaxaConstant.ATTRIBUTE_STATUS_TITLE))) {
							attributes.put(KtunaxaConstant.ATTRIBUTE_STATUS, status);
						}
					}
				}
				attributes.put(KtunaxaConstant.ATTRIBUTE_STOP_REASON, new StringAttribute(request.getReason()));
				vectorLayerService.saveOrUpdate(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs, features, newFeatures);

				// stop process instances (contrary to the names, these are all the executions for the process instance)
				for (ProcessInstance processInstance : processInstances) {
					runtimeService.deleteProcessInstance(processInstance.getId(),
							"Requested by user " + securityContext.getUserId());
				}
			}
		}
	}
}
