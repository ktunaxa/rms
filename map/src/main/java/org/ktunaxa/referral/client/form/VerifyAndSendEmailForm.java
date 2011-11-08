/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.command.dto.SendEmailRequest;
import org.ktunaxa.referral.server.command.dto.SendEmailResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;

/**
 * Form used to display a pre formed default email using {@link org.ktunaxa.referral.server.domain.Template}.
 * <p />
 * One EmailTemplateForm is created for each notifier.
 * 
 * @author Emiel Ackermann
 */
public class VerifyAndSendEmailForm extends AbstractTaskForm {
	
	private final String notifier;
	private final TextItem from = new TextItem();
	private final TextItem subject = new TextItem();
	private final TextAreaItem message = new TextAreaItem();
	private final TextItem to = new TextItem();
	private final TextItem cc = new TextItem();
	private final CheckboxItem sendMail = new CheckboxItem();
	private final RegExpValidator oneAddress;
	private final RegExpValidator multiAddress;
	private TaskDto task;

	public VerifyAndSendEmailForm(String notifier) {
		super();
		this.notifier = notifier;
		setStyleName("taskBlockContent");

		oneAddress = new RegExpValidator();
		oneAddress.setExpression(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
		oneAddress.setErrorMessage("Invalid email address.");
		multiAddress = new RegExpValidator();
		multiAddress.setExpression(KtunaxaConstant.MULTIPLE_MAIL_VALIDATOR_REGEX);
		multiAddress.setErrorMessage("Invalid email address(es). Separate with ', ' or '; '");

		from.setName(KtunaxaConstant.Email.FROM_NAME);
		from.setTitle("From");
		from.setValidators(oneAddress);
		from.setWidth("100%");
		from.setRequired(true);
		from.setRequiredMessage("Sender email address required.");

		subject.setName(KtunaxaConstant.Email.SUBJECT_NAME);
		subject.setTitle("Subject");
		subject.setWidth("100%");

		message.setShowTitle(false);
		message.setName(KtunaxaConstant.Email.MESSAGE_NAME);
		message.setColSpan(2);
		message.setWidth("100%");
		message.setMinHeight(100);
		message.setHeight("*");
		
		to.setName(KtunaxaConstant.Email.TO_NAME);
		to.setTitle("To");
		to.setDisabled(true);
		to.setWidth("100%");
		
		cc.setName(KtunaxaConstant.Email.CC_NAME);
		cc.setTitle("Cc");
		cc.setValidators(multiAddress);
		cc.setWidth("100%");

		sendMail.setTitle("Send mail when finished");
		sendMail.setValue(true);
		sendMail.addChangeHandler(new ChangeHandler() {
			
			public void onChange(ChangeEvent event) {
				Boolean disable = sendMail.getValueAsBoolean(); // == pre-change value.
				from.setDisabled(disable);
				cc.setDisabled(disable);
				subject.setDisabled(disable);
				message.setDisabled(disable);
				if (disable) {
					from.setValidators();
					cc.setValidators();
				} else {
					from.setValidators(oneAddress);
					cc.setValidators(multiAddress);
				}
			} 
		});
		setSendForm();
	}

	private void setSendForm() {
		DynamicForm mail = new DynamicForm();
		mail.setWidth100();
		mail.setFields(from, to, cc, subject);
		mail.setColWidths("13%", "*");
		
		DynamicForm messageForm = new DynamicForm();
		messageForm.setWidth("87%");
		messageForm.setLayoutAlign(Alignment.RIGHT);
		messageForm.setFields(message);
		messageForm.setHeight("*");
		
		DynamicForm checkBox = new DynamicForm();
		checkBox.setWidth100();
		checkBox.setFields(sendMail);
		checkBox.setTitleWidth("70%");
		setForms(mail, messageForm, checkBox);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		this.task = task;
		fillTemplate();
	}

	/**
	 * Get email data from db and put/convert it into values for the items of the form.
	 */
	@SuppressWarnings("unchecked")
	public void fillTemplate() {
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
		cc.setValue(""); // clear the cc field of old values.
		Feature currentReferral = MapLayout.getInstance().getCurrentReferral();
		Attribute<String> email = currentReferral.getAttributes().get(KtunaxaConstant.ATTRIBUTE_EMAIL);
		to.setValue(email.getValue());
	}

	@Override
	public void validate(final Runnable valid, final Runnable invalid) {
		if (validate()) {
			if (isSendMail()) {
				SendEmailRequest request = new SendEmailRequest();
				setEmailRequest(request);
				GwtCommand command = new GwtCommand(SendEmailRequest.COMMAND);
				command.setCommandRequest(request);
				GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<SendEmailResponse>() {
					public void execute(SendEmailResponse response) {
						if (response.isSuccess()) {
							valid.run();
						} else {
							SC.say("Could not send e-mail.");
							invalid.run();
						}
					}
				});
			} else {
				valid.run();
			}
		} else {
			invalid.run();
		}
	}

	/**
	 * Should the e-mail be sent or not?
	 *
	 * @return truen when -email should be sent
	 */
	protected boolean isSendMail() {
		return sendMail.getValueAsBoolean();
	}

	/**
	 * Fill the parameters for sending the e-mail from the form.
	 *
	 * @param request {@link SendEmailRequest} request object
	 */
	protected void setEmailRequest(SendEmailRequest request) {
		request.setFrom(from.getValueAsString());
		request.setReplyTo(from.getValueAsString());
		request.setBcc(KtunaxaConstant.EMAIL_BCC);
		request.setTo(to.getValueAsString());
		request.setCc(cc.getValueAsString());
		request.setSubject(subject.getValueAsString());
		request.setText(message.getValueAsString());
	}

	@Override
	public Map<String, String> getVariables() {
		return new HashMap<String, String>();
	}
}