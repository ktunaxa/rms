package org.ktunaxa.referral.server.command.bpm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.geomajas.geometry.Crs;
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
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract command with common methods for finding and updating the status of a referral.
 * 
 * @author Jan De Moerloose
 * 
 */
public abstract class AbstractReferralCommand {

	private final Logger log = LoggerFactory.getLogger(AbstractReferralCommand.class);

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected HistoryService historyService;

	@Autowired
	protected SecurityContext securityContext;

	@Autowired
	protected VectorLayerService vectorLayerService;

	@Autowired
	protected FilterService filterService;

	@Autowired
	protected GeoService geoService;

	@Autowired
	protected TaskService taskService;

	protected InternalFeature findReferral(String referralId) {
		// find referral
		Crs crs;
		try {
			crs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
			List<InternalFeature> features = vectorLayerService.getFeatures(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID,
					crs, filterService.parseFilter(ReferralUtil.createFilter(referralId)), null,
					VectorLayerService.FEATURE_INCLUDE_ATTRIBUTES);
			if (features.size() != 1) {
				if (features.isEmpty()) {
					log.error("Process found but not referral for {}.", referralId);
				} else {
					log.error("Multiple ({}) referrals found for id {}.", features.size(), referralId);
				}
			} else {
				// referral found
				InternalFeature orgReferral = features.get(0);
				return orgReferral;
			}
		} catch (GeomajasException e) {
			log.error("Process found but not referral for {" + referralId + "}.", e);
		}
		return null;
	}

	protected void updateStatus(InternalFeature orgReferral, String newStatus) throws GeomajasException {
		updateStatus(orgReferral, newStatus, null);
	}

	protected void updateStatus(InternalFeature orgReferral, String newStatus, String reason) throws GeomajasException {
		// update the status to the new status
		Crs crs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
		InternalFeature referral = orgReferral.clone();
		Map<String, Attribute> attributes = referral.getAttributes();
		List<InternalFeature> newFeatures = new ArrayList<InternalFeature>();
		newFeatures.add(referral);
		List<Attribute<?>> statusses = vectorLayerService.getAttributes(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID,
				KtunaxaConstant.ATTRIBUTE_STATUS, filterService.createTrueFilter());
		for (Attribute<?> status : statusses) {
			if (status instanceof ManyToOneAttribute) {
				if (newStatus.equals(((ManyToOneAttribute) status).getValue().getAttributeValue(
						KtunaxaConstant.ATTRIBUTE_STATUS_TITLE))) {
					attributes.put(KtunaxaConstant.ATTRIBUTE_STATUS, status);
				}
			}
		}
		if (reason != null) {
			attributes.put(KtunaxaConstant.ATTRIBUTE_STOP_REASON, new StringAttribute(reason));
		}
		vectorLayerService.saveOrUpdate(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs, Arrays.asList(orgReferral),
				Arrays.asList(referral));
		log.info("Set referral " + referral.getAttributes().get(KtunaxaConstant.ATTRIBUTE_FULL_ID).getValue()
				+ " to status " + newStatus);
	}

	protected <T> T getPrimitiveAttributeValue(InternalFeature feature, String name) {
		Attribute a = feature.getAttributes().get(name);
		return a == null ? null : (T) a.getValue();
	}

	protected void deleteTasks(String referralId) {
		// delete the associate tasks if any
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> tasks = taskQuery.processVariableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId)
				.list();
		for (Task task : tasks) {
			taskService.deleteTask(task.getId(), true);
		}
		HistoricTaskInstanceQuery historicTaskQuery = historyService.createHistoricTaskInstanceQuery();
		List<HistoricTaskInstance> histTasks = historicTaskQuery.processVariableValueEquals(
				KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId).list();
		for (HistoricTaskInstance task : histTasks) {
			taskService.deleteTask(task.getId(), true);
		}
	}

	protected void deleteProcesses(String referralId) {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
				.variableValueEquals(KtunaxaBpmConstant.VAR_REFERRAL_ID, referralId).list();
		if (!processInstances.isEmpty()) {
			// stop process instances (contrary to the names, these are all the executions for the process instance)
			for (ProcessInstance processInstance : processInstances) {
				ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
				// Must check if the instance is still there as delete cascades to sub-processes !
				if (!query.processInstanceId(processInstance.getId()).list().isEmpty()) {
					runtimeService.deleteProcessInstance(processInstance.getId(), "Requested by user "
							+ securityContext.getUserId());
					historyService.deleteHistoricProcessInstance(processInstance.getId());
				}
			}
		}
	}

}
