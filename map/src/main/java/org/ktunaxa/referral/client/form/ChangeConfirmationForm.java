/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
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
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Form to handle the response about changed engagement level, response deadline or referral completeness.
 *
 * @author Joachim Van der Auwera
 */
public class ChangeConfirmationForm extends AbstractTaskForm {

	private final DateItem completionDeadline = new DateItem();
	private final SpinnerItem engagementLevel = new SpinnerItem();

	/** Construct a {@link org.ktunaxa.referral.client.form.ChangeConfirmationForm}. */
	public ChangeConfirmationForm() {
		super();

		RegExpValidator mailValidator = new RegExpValidator();
		mailValidator.setExpression(KtunaxaConstant.MAIL_VALIDATOR_REGEX);

		completionDeadline.setName("completionDeadline");
		completionDeadline.setTitle("Completion deadline");

		engagementLevel.setName("engagementLevel");
		engagementLevel.setTitle("Engagement level");
		engagementLevel.setMin(0);
		engagementLevel.setMax(3);

		setFields(completionDeadline, engagementLevel);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		Map<String, String> variables = task.getVariables();
		DateTimeFormat formatter = DateTimeFormat.getFormat(KtunaxaBpmConstant.DATE_FORMAT);
		completionDeadline.setValue(formatter.parse(variables.get(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE)));
		engagementLevel.setValue(variables.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL));
		String provinceEngagementLevel = variables.get(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL);
		engagementLevel.setHint("Province:" + provinceEngagementLevel);
	}

	@Override
	public Map<String, String> getVariables() {
		// actually get the values
		Map<String, String> result = new HashMap<String, String>();
		DateTimeFormat formatter = DateTimeFormat.getFormat(KtunaxaBpmConstant.DATE_FORMAT);

		result.put(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE,
				formatter.format(completionDeadline.getValueAsDate()));
		result.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL,
				nullSafeToString(engagementLevel.getValue()));
		// update referral itself
		MapLayout mapLayout = MapLayout.getInstance();
		VectorLayer layer = mapLayout.getReferralLayer();
		Feature current = mapLayout.getCurrentReferral();
		Feature previous = new org.geomajas.gwt.client.map.feature.Feature(current, layer).toDto();
		Map<String, Attribute> attributes = current.getAttributes();
		attributes.put(KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL, new IntegerAttribute(
				Integer.parseInt(result.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL))));
		attributes.put(KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE, new DateAttribute(
				completionDeadline.getValueAsDate()));
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