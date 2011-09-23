/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import java.util.Map;

import org.geomajas.command.CommandResponse;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.command.email.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.email.GetEmailDataResponse;
import org.ktunaxa.referral.server.domain.Template;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * Form used to display a pre formed default email using {@link org.ktunaxa.referral.server.domain.Template}.
 * 
 * @author Emiel Ackermann
 *
 */
public class EmailForm extends AbstractTaskForm {
	
	private String notifier;
	private TextItem from = new TextItem();
	private TextItem to = new TextItem();
	private TextItem subject = new TextItem();
	private TextAreaItem message = new TextAreaItem();

	public EmailForm(String notifier) {
		super();
		this.notifier = notifier;
		RegExpValidator mailValidator = new RegExpValidator();
		mailValidator.setExpression(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
		
		from.setName("fromAddress");
		from.setTitle("From: ");
		from.setValidators(mailValidator);
		
		to.setName("toAddress");
		to.setTitle("To: ");
		to.setValidators(mailValidator);
		
		subject.setName("subject");
		subject.setTitle("Subject: ");
		
		message.setName("message");
	}
	
	@Override
	public void show() {
		super.show();
		setVariables();
	}
	
	@SuppressWarnings("unchecked")
	public void setVariables() {
		Feature currentReferral = MapLayout.getInstance().getCurrentReferral();
		Attribute<String> email = currentReferral.getAttributes().get(KtunaxaConstant.ATTRIBUTE_EMAIL);
		to.setValue(email.getValue());
		
		GetEmailDataRequest request = new GetEmailDataRequest(notifier);
		GwtCommand command = new GwtCommand(GetEmailDataRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<CommandResponse>() {
			public void execute(CommandResponse response) {
				Template domain = ((GetEmailDataResponse) response).getTemplate();
				from.setValue(domain.getMailSender());
				subject.setValue(domain.getTitle()); //TODO check title and convert it into something readable.
				message.setValue(domain.getStringContent());
			}
		});
	}

	@Override
	public Map<String, String> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}
}