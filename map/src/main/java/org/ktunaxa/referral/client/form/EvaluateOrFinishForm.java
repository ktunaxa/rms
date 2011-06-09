/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

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
public class EvaluateOrFinishForm extends AbstractTaskForm {

	private CheckboxItem evaluateValues = new CheckboxItem();

	public EvaluateOrFinishForm() {
		super();

		evaluateValues.setName("evaluateValues");
		evaluateValues.setHint("Evaluate values");

		setFields(evaluateValues);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		evaluateValues.setValue(task.getVariables().
				get(KtunaxaBpmConstant.REFERRAL_CONTEXT_EVALUATE_VALUES));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.REFERRAL_CONTEXT_EVALUATE_VALUES,
				nullSafeToString(evaluateValues.getValue()));
		return result;
	}

}
