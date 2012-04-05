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

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
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
 * Form to do a quick review of a new referral, verify engagement level and response deadline.
 *
 * @author Joachim Van der Auwera
 */
public class ReviewReferralForm extends AbstractTaskForm {

	private final TextAreaItem description = new TextAreaItem();
	private final TextItem email = new TextItem();
	private final CheckboxItem incomplete = new CheckboxItem();
	private final CheckboxItem change = new CheckboxItem();
	private final DateItem completionDeadline = new DateItem();
	private final SpinnerItem engagementLevel = new SpinnerItem();
	private final TextAreaItem engagementComment = new TextAreaItem();
	private String provinceEngagementLevel;

	/** 
	 * Construct a {@link ReviewReferralForm}.
	 */
	public ReviewReferralForm() {
		super();

		RegExpValidator mailValidator = new RegExpValidator();
		mailValidator.setExpression(KtunaxaConstant.MAIL_VALIDATOR_REGEX);

		description.setName("referralName");
		description.setTitle("Referral name");
		description.setWidth("*");

		email.setName("email");
		email.setTitle("Referral e-mail");
		email.setValidators(mailValidator);
		email.setWidth("*");

		change.setName("needChangeNotification");
		change.setTitle("Suggest new timeline/engagement level/feedback");
		change.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				propagateChangeStatus(change.getValueAsBoolean());
			}
		});

		incomplete.setName("incomplete");
		incomplete.setTitle("Referral incomplete");

		completionDeadline.setName("completionDeadline");
		completionDeadline.setTitle("Completion deadline");

		engagementLevel.setName("engagementLevel");
		engagementLevel.setTitle("Engagement level");
		engagementLevel.setMin(0);
		engagementLevel.setMax(3);

		engagementComment.setName("engagementComment");
		engagementComment.setTitle("Comment");
		engagementComment.setWidth("*");
		engagementComment.setDisabled(true);

		setFields(description, email, change, incomplete, completionDeadline, engagementLevel, engagementComment);
	}

	private void propagateChangeStatus(boolean changeValue) {
		incomplete.setDisabled(!changeValue);
		completionDeadline.setDisabled(!changeValue);
		engagementLevel.setDisabled(!changeValue);
		engagementComment.setDisabled(!changeValue);
	}

	private boolean isEngagementLevelChanged() {
		String ev = engagementLevel.getValue().toString();
		return !ev.equals(provinceEngagementLevel);
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
		String comment = variables.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT);
		engagementComment.setValue(comment);                                           
		boolean changeValue = isEngagementLevelChanged() || (null != comment && comment.length() > 0); 
		change.setValue(changeValue);
		incomplete.setValue(false);
		propagateChangeStatus(changeValue);
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
		result.put(KtunaxaBpmConstant.VAR_INCOMPLETE + KtunaxaBpmConstant.SET_BOOLEAN,
				Boolean.toString(incomplete.getValueAsBoolean()));
		boolean changeValue = change.getValueAsBoolean();
		result.put(KtunaxaBpmConstant.VAR_NEED_CHANGE_NOTIFICATION + KtunaxaBpmConstant.SET_BOOLEAN,
				Boolean.toString(changeValue));
		result.put(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL,
				provinceEngagementLevel);
		result.put(KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT,
				nullSafeToString(engagementComment.getValue()));
		// update referral itself
		MapLayout mapLayout = MapLayout.getInstance();
		VectorLayer layer = mapLayout.getReferralLayer();
		Feature current = mapLayout.getCurrentReferral();
		Feature previous = new org.geomajas.gwt.client.map.feature.Feature(current, layer).toDto();
		Map<String, Attribute> attributes = current.getAttributes();
		attributes.put(KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL, new IntegerAttribute(
				Integer.parseInt(result.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL))));
		attributes.put(KtunaxaConstant.ATTRIBUTE_PROJECT, new StringAttribute(
				result.get(KtunaxaBpmConstant.VAR_REFERRAL_NAME)));
		attributes.put(KtunaxaConstant.ATTRIBUTE_EMAIL, new StringAttribute(
				result.get(KtunaxaBpmConstant.VAR_EMAIL)));
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
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<CommandResponse>() {
			public void execute(CommandResponse response) {
				// all fine
			}
		});

		// return result
		return result;
	}
}