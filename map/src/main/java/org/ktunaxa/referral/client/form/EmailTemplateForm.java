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
import com.smartgwt.client.widgets.form.validator.Validator;

/**
 * Form used to display a pre formed default email using {@link org.ktunaxa.referral.server.domain.Template}.<br />
 * One EmailTemplateForm is created for each notifier.
 * 
 * @author Emiel Ackermann
 */
public class EmailTemplateForm extends AbstractEmailForm {
	
	private TextItem to = new TextItem();
	private TextItem cc = new TextItem();
	private CheckboxItem sendMail = new CheckboxItem();

	public EmailTemplateForm(String notifier) {
		super(notifier);
		
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
					from.setValidators(new Validator[]{});
					cc.setValidators(new Validator[]{});
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
	
	@SuppressWarnings("unchecked")
	public void fillTemplate() {
		super.fillTemplate();
		cc.setValue(""); // clear the cc field of old values.
		Feature currentReferral = MapLayout.getInstance().getCurrentReferral();
		Attribute<String> email = currentReferral.getAttributes().get(KtunaxaConstant.ATTRIBUTE_EMAIL);
		to.setValue(email.getValue());
		
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		this.task = task;
		fillTemplate();
	}

	@Override
	public boolean validate() {
		boolean result = super.validate();
		if (result) {
			if (sendMail.getValueAsBoolean()) {
				Map<String, String> variables = new HashMap<String, String>();
				variables.put(from.getName(), from.getValueAsString());
				variables.put(to.getName(), to.getValueAsString());
				variables.put(cc.getName(), cc.getValueAsString());
				variables.put(subject.getName(), subject.getValueAsString());
				variables.put(message.getName(), message.getValueAsString());
				SendEmailRequest request = new SendEmailRequest();
				request.setMailVariables(variables);
				GwtCommand command = new GwtCommand(SendEmailRequest.COMMAND);
				command.setCommandRequest(request);
				GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<SendEmailResponse>() {
					public void execute(SendEmailResponse response) {
						// response is not used.
					}
				});
			}
		}
		return result;
	}

	@Override
	public Map<String, String> getVariables() {
		return new HashMap<String, String>();
	}
}