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
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.geomajas.layer.feature.FeatureTransaction;
import org.geomajas.layer.feature.attribute.DateAttribute;
import org.geomajas.layer.feature.attribute.IntegerAttribute;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.gui.MapLayout;
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
	private String provinceEngagementLevel;

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
		engagementComment.setTitle("Comment about changed engagement level");
		engagementComment.setWidth("*");
		engagementComment.setDisabled(true);

		engagementLevel.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				updateEngagementCommentStatus();
			}
		});

		setFields(completionDeadline, description, email, engagementLevel, engagementComment);
	}

	private void updateEngagementCommentStatus() {
		String ev = engagementLevel.getValue().toString();
		engagementComment.setDisabled(ev.equals(provinceEngagementLevel));
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
		provinceEngagementLevel = variables.get(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL);
		engagementLevel.setHint("Province:" + provinceEngagementLevel);
		engagementComment.setValue(variables.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT));
		updateEngagementCommentStatus();
	}

	@Override
	public Map<String, String> getVariables() {
		// actually get the values
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

		// update referral itself
		MapLayout mapLayout = MapLayout.getInstance();
		VectorLayer layer = mapLayout.getReferralLayer();
		Feature current = mapLayout.getCurrentReferral();
		Feature previous = new org.geomajas.gwt.client.map.feature.Feature(current, layer).toDto();
		Map<String, Attribute> attributes = current.getAttributes();
		((IntegerAttribute) attributes.get(KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL)).setValue(
				Integer.parseInt(result.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL))
		);
		((StringAttribute) attributes.get(KtunaxaConstant.ATTRIBUTE_PROJECT)).setValue(
				result.get(KtunaxaBpmConstant.VAR_REFERRAL_NAME)
		);
		((StringAttribute) attributes.get(KtunaxaConstant.ATTRIBUTE_EMAIL)).setValue(
				result.get(KtunaxaBpmConstant.VAR_EMAIL)
		);
		((DateAttribute) attributes.get(KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE)).setValue(
				completionDeadline.getValueAsDate()
		);
		final FeatureTransaction ft = new FeatureTransaction();
		ft.setLayerId(layer.getServerLayerId());
		ft.setOldFeatures(new Feature[] {previous});
		ft.setNewFeatures(new Feature[] {current});
		PersistTransactionRequest request = new PersistTransactionRequest();
		request.setFeatureTransaction(ft);
		request.setCrs(layer.getMapModel().getCrs());
		GwtCommand command = new GwtCommand(PersistTransactionRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback<CommandResponse>() {
			public void execute(CommandResponse response) {
				// all fine
			}
		});

		// return result
		return result;
	}
}