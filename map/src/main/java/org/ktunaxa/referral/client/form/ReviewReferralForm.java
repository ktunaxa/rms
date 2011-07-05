/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public class ReviewReferralForm extends AbstractTaskForm {

	private DateItem completionDeadline = new DateItem();
	private TextAreaItem description = new TextAreaItem();
	private TextItem email = new TextItem();
	private SpinnerItem engagementLevel = new SpinnerItem();
	private TextAreaItem engagementComment = new TextAreaItem();

	public ReviewReferralForm() {
		super();

		RegExpValidator mailValidator = new RegExpValidator();
		mailValidator.setExpression(KtunaxaConstant.MAIL_VALIDATOR_REGEX);

		completionDeadline.setName("completionDeadline");
		completionDeadline.setTitle("Completion deadline");

		description.setName("referralName");
		description.setTitle("Referral name");
		description.setWidth("*");

		email.setName("email");
		email.setTitle("Referral e-mail");
		email.setValidators(mailValidator);
		email.setWidth("*");

		engagementLevel.setName("engagementLevel");
		engagementLevel.setTitle("Engagement level");
		engagementLevel.setMin(0);
		engagementLevel.setMax(3);

		engagementComment.setName("engagementComment");
		engagementComment.setTitle("Comment about changes");
		engagementComment.setWidth("*");

		setFields(completionDeadline, description, email, engagementLevel, engagementComment);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		Map<String, String> variables = task.getVariables();
		DateTimeFormat formatter = DateTimeFormat.getFormat(KtunaxaBpmConstant.DATE_FORMAT);
		completionDeadline.setValue(formatter.parse(variables.get(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE)));
		description.setValue(variables.get(KtunaxaBpmConstant.VAR_REFERRAL_NAME));
		email.setValue(variables.get(KtunaxaBpmConstant.VAR_EMAIL));
		engagementLevel.setValue(variables.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL));
		engagementLevel.setHint("Province-" + variables.get(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL));
		engagementComment.setValue(variables.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		DateTimeFormat formatter = DateTimeFormat.getFormat(KtunaxaBpmConstant.DATE_FORMAT);

		result.put(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE,
				formatter.format(completionDeadline.getValueAsDate()));
		result.put(KtunaxaBpmConstant.VAR_REFERRAL_NAME,
				nullSafeToString(description.getValue()));
		result.put(KtunaxaBpmConstant.VAR_EMAIL,
				nullSafeToString(email.getValue()));
		result.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL,
				nullSafeToString(engagementLevel.getValue()));
		return result;
	}
}