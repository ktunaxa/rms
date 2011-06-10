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
