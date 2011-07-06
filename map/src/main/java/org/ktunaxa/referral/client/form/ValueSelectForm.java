/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public class ValueSelectForm extends AbstractTaskForm {

	private CheckboxItem evalAquatic = new CheckboxItem();
	private CheckboxItem evalArchaeological = new CheckboxItem();
	private CheckboxItem evalCultural = new CheckboxItem();
	private CheckboxItem evalEcological = new CheckboxItem();
	private CheckboxItem evalTreaty = new CheckboxItem();
	private CheckboxItem communityAInput = new CheckboxItem();
	private CheckboxItem communityBInput = new CheckboxItem();
	private CheckboxItem communityCInput = new CheckboxItem();
	private CheckboxItem communityDInput = new CheckboxItem();

	public ValueSelectForm() {
		super();

		evalAquatic.setName("evalAquatic");
		evalAquatic.setTitle("Evaluate aquatic values");

		evalArchaeological.setName("evalArchaeological");
		evalArchaeological.setTitle("Evaluate archaeological values");

		evalCultural.setName("evalCultural");
		evalCultural.setTitle("Evaluate cultural values");

		evalEcological.setName("evalEcological");
		evalEcological.setTitle("Evaluate ecological values");

		evalTreaty.setName("evalTreaty");
		evalTreaty.setTitle("Evaluate treaty values");

		communityAInput.setName("communityAInput");
		communityAInput.setTitle("Request A community input");

		communityBInput.setName("communityBInput");
		communityBInput.setTitle("Request B community input");

		communityCInput.setName("communityCInput");
		communityCInput.setTitle("Request C community input");

		communityDInput.setName("communityDInput");
		communityDInput.setTitle("Request D community input");

		DynamicForm values = new DynamicForm();
		values.setWidth100();
		values.setIsGroup(true);
		values.setGroupTitle("Which values need to be evaluated?");
		values.setFields(evalAquatic, evalArchaeological, evalCultural, evalEcological, evalTreaty);

		DynamicForm community = new DynamicForm();
		community.setWidth100();
		community.setIsGroup(true);
		community.setGroupTitle("Is community input needed?");
		community.setFields(communityAInput, communityBInput, communityCInput, communityDInput);

		setForms(values, community);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		evalAquatic.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_EVALUATE_AQUATIC));
		evalArchaeological.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_EVALUATE_ARCHAEOLOGICAL));
		evalCultural.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_EVALUATE_CULTURAL));
		evalEcological.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_EVALUATE_ECOLOGICAL));
		evalTreaty.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_EVALUATE_TREATY));
		communityAInput.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_COMMUNITY_A_INPUT));
		communityBInput.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_COMMUNITY_B_INPUT));
		communityCInput.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_COMMUNITY_C_INPUT));
		communityDInput.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_COMMUNITY_D_INPUT));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_AQUATIC + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(evalAquatic.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_ARCHAEOLOGICAL + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(evalArchaeological.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_CULTURAL + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(evalCultural.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_ECOLOGICAL + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(evalEcological.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_TREATY + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(evalTreaty.getValue()));
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_A_INPUT + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(communityAInput.getValue()));
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_B_INPUT + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(communityBInput.getValue()));
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_C_INPUT + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(communityCInput.getValue()));
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_D_INPUT + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(communityDInput.getValue()));
		return result;
	}

}
