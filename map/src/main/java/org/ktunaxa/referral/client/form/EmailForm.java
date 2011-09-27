/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import java.util.HashMap;
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

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
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
	private TextItem cc = new TextItem();
	private TextItem bcc = new TextItem();
	private TextItem subject = new TextItem();
	private TextAreaItem message = new TextAreaItem();
	private CheckboxItem sendMail = new CheckboxItem();
	private TaskDto task;

	public EmailForm(String notifier) {
		super();
		this.notifier = notifier;
		
		RegExpValidator mailValidator = new RegExpValidator();
		mailValidator.setExpression(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
		
		from.setName(KtunaxaConstant.Email.FROM_NAME);
		from.setTitle("From");
		from.setValidators(mailValidator);
		from.setWidth("100%");
		
		to.setName(KtunaxaConstant.Email.TO_NAME);
		to.setTitle("To");
		to.setDisabled(true);
		to.setWidth("100%");
		//TODO to not editable, add cc.
		
		cc.setName(KtunaxaConstant.Email.CC_NAME);
		cc.setTitle("Cc");
		cc.setValidators(mailValidator);
		cc.setWidth("100%");
		
		bcc.setName(KtunaxaConstant.Email.BCC_NAME);
		bcc.setTitle("Bcc");
		bcc.setValidators(mailValidator);
		bcc.setWidth("100%");
		
		subject.setName(KtunaxaConstant.Email.SUBJECT_NAME);
		subject.setTitle("Subject");
		subject.setWidth("100%");
		
		message.setShowTitle(false);
		message.setName(KtunaxaConstant.Email.MESSAGE_NAME);
		message.setColSpan(2);
		message.setWidth("100%");
		message.setHeight(200);
		
		sendMail.setTitle("Send mail when finished");
		sendMail.setValue(true);
		
		setStyleName("taskBlockContent");
		
		DynamicForm mail = new DynamicForm();
		mail.setWidth100();
		mail.setFields(from, to, cc, bcc, subject);
		mail.setColWidths("13%", "*");
		
		DynamicForm messageForm = new DynamicForm();
		messageForm.setWidth("87%");
		messageForm.setLayoutAlign(Alignment.RIGHT);
		messageForm.setFields(message);
		
		DynamicForm checkBox = new DynamicForm();
		checkBox.setWidth100();
		checkBox.setFields(sendMail);
		checkBox.setTitleWidth("70%");
		setForms(mail, messageForm, checkBox);
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
		Map<String, String> variables = new HashMap<String, String>();
		variables.put(from.getName(), from.getValueAsString());
		variables.put(to.getName(), to.getValueAsString());
		variables.put(cc.getName(), cc.getValueAsString());
		variables.put(bcc.getName(), bcc.getValueAsString());
		variables.put(subject.getName(), subject.getValueAsString());
		variables.put(message.getName(), message.getValueAsString());
		return variables;
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		this.task = task;
		setVariables();
	}

	@Override
	public boolean validate() {
		boolean result = super.validate();
//		if (result) {
//			result = sendMail.getValueAsBoolean();
//		}
//		if (result) {
//			Map<String, String> variables = getVariables();
//			//TODO Create SendEmailRequest.COMMAND
//			SendEmailRequest request = new SendEmailRequest();
//			request.setMailVariables(variables);
//			GwtCommand command = new GwtCommand(SendEmailRequest.COMMAND);
//			command.setCommandRequest(request);
//			GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<SendEmailResponse>() {
//				public void execute(SendEmailResponse response) {
//					response.mailIsSent();
//				}
//			});
//		}
		return result;
	}
}