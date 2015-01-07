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

package org.ktunaxa.referral.client.form;

import java.util.HashMap;
import java.util.Map;

import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public class ValueSelectForm extends AbstractTaskForm {

	private CheckboxItem evalAquatic = new CheckboxItem();
	private CheckboxItem evalArchaeological = new CheckboxItem();
	private CheckboxItem evalCultural = new CheckboxItem();
	private CheckboxItem evalTerrestrial = new CheckboxItem();
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

		evalTerrestrial.setName("evalTerrestrial");
		evalTerrestrial.setTitle("Evaluate terrestrial values");

		evalTreaty.setName("evalTreaty");
		evalTreaty.setTitle("Evaluate treaty values");

		communityAInput.setName("communityAInput");
		communityAInput.setTitle("Inform Akisqnuk community liaison");

		communityBInput.setName("communityBInput");
		communityBInput.setTitle("Inform Lower Kootenay community liaison");

		communityCInput.setName("communityCInput");
		communityCInput.setTitle("Inform St. Marys community liaison");

		communityDInput.setName("communityDInput");
		communityDInput.setTitle("Inform Tobacco Plains community liaison");

		DynamicForm values = new DynamicForm();
		values.setWidth100();
		values.setIsGroup(true);
		values.setGroupTitle("Which values need to be evaluated?");
		values.setFields(evalAquatic, evalArchaeological, evalCultural, evalTerrestrial, evalTreaty);

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
		evalTerrestrial.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_EVALUATE_TERRESTRIAL));
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
		result.put(KtunaxaBpmConstant.VAR_EVALUATE_TERRESTRIAL + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(evalTerrestrial.getValue()));
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
