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

import com.smartgwt.client.widgets.form.fields.CheckboxItem;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public class DiscussEvaluationForm extends AbstractTaskForm {

	private CheckboxItem finalDecisionConsistent = new CheckboxItem();

	public DiscussEvaluationForm() {
		super();

		finalDecisionConsistent.setName("finalDecisionConsistent");
		finalDecisionConsistent.setTitle("Consistent decision");
		finalDecisionConsistent.setPrompt("Provincial decision is consistent with KLRA response");

		setFields(finalDecisionConsistent);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		finalDecisionConsistent.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_FINAL_DECISION_CONSISTENT));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.VAR_FINAL_DECISION_CONSISTENT + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(finalDecisionConsistent.getValue()));
		return result;
	}
}
