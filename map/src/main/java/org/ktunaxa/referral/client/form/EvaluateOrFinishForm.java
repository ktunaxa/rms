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
public class EvaluateOrFinishForm extends AbstractTaskForm {

	private CheckboxItem reportValues = new CheckboxItem();

	public EvaluateOrFinishForm() {
		super();

		reportValues.setName("reportValues");
		reportValues.setTitle("Report values");

		setFields(reportValues);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		reportValues.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_REPORT_VALUES));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.VAR_REPORT_VALUES + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(reportValues.getValue()));
		return result;
	}

}
