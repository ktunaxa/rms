<<<<<<< .mine
/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import java.util.Map;

import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * Form used to display a pre formed default email using {@link org.ktunaxa.referral.server.domain.Template}.
 * 
 * @author Emiel Ackermann
 */
public class EmailForm extends AbstractTaskForm {
	
	private String notifier;
	private TextItem from = new TextItem();
	private TextItem to = new TextItem();
	private TextItem subject = new TextItem();
	private TextAreaItem message = new TextAreaItem();
	private TaskDto task;

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
		DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setFields(from, to, subject, message);
		form.setStyleName("emailContent");
		setForms(form);
	}
	
	@Override
	public void show() {
		super.show();
		setVariables();
	}
	/**
	 * Get email data from db and put/convert it into values for the items of the form.
	 */
	@SuppressWarnings("unchecked")
	public void setVariables() {
		Feature currentReferral = MapLayout.getInstance().getCurrentReferral();
		Attribute<String> email = currentReferral.getAttributes().get(KtunaxaConstant.ATTRIBUTE_EMAIL);
		to.setValue(email.getValue());
		
		GetEmailDataRequest request = new GetEmailDataRequest(notifier);
		request.setTask(task);
		GwtCommand command = new GwtCommand(GetEmailDataRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetEmailDataResponse>() {
			public void execute(GetEmailDataResponse response) {
				from.setValue(response.getFrom());
				subject.setValue(response.getSubject());
				message.setValue(response.getBody());
			}
		});
	}

	/**
	 * Get the variables of the filled in email form. These are given to the {@link SendEmailCommand}.
	 */
	@Override
	public Map<String, String> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		this.task = task;
	}
=======
/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import java.util.Map;

import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * Form used to display a pre formed default email using {@link org.ktunaxa.referral.server.domain.Template}.
 * 
 * @author Emiel Ackermann
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
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetEmailDataResponse>() {
			public void execute(GetEmailDataResponse response) {
				from.setValue(response.getFrom());
				subject.setValue(response.getSubject());
				message.setValue(response.getBody());
			}
		});
	}

	@Override
	public Map<String, String> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}
>>>>>>> .r1461
}