/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.smartgwt.client.widgets.form.fields.DateItem;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public class ReviewReferralForm extends AbstractTaskForm {

	private DateItem completionDeadline = new DateItem();

	public ReviewReferralForm() {
		super();

		completionDeadline.setName("finalDecisionConsistent");
		completionDeadline.setHint("Completion deadline");

		setFields(completionDeadline);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		completionDeadline.setValue(task.getVariables().
				get(KtunaxaBpmConstant.REFERRAL_CONTEXT_COMPLETION_DEADLINE));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.REFERRAL_CONTEXT_COMPLETION_DEADLINE,
				nullSafeToString(completionDeadline.getValue()));
		return result;
	}

}
