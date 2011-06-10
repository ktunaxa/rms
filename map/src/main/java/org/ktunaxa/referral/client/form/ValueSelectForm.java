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
	private CheckboxItem communityInput = new CheckboxItem();

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

		communityInput.setName("communityInput");
		communityInput.setTitle("Request community input");

		DynamicForm values = new DynamicForm();
		values.setWidth100();
		values.setIsGroup(true);
		values.setGroupTitle("Which values need to be evaluated?");
		values.setFields(evalAquatic, evalArchaeological, evalCultural, evalEcological, evalTreaty);

		DynamicForm community = new DynamicForm();
		community.setWidth100();
		community.setIsGroup(true);
		community.setGroupTitle("Is community input needed?");
		community.setFields(communityInput);

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
		communityInput.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_COMMUNITY_INPUT));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_AQUATIC,
				nullSafeToString(evalAquatic.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_ARCHAEOLOGICAL,
				nullSafeToString(evalArchaeological.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_CULTURAL,
				nullSafeToString(evalCultural.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_ECOLOGICAL,
				nullSafeToString(evalEcological.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_TREATY,
				nullSafeToString(evalTreaty.getValue()));
		result.put(KtunaxaBpmConstant.VAR_COMMUNITY_INPUT,
				nullSafeToString(communityInput.getValue()));
		return result;
	}

}
