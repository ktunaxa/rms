/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.ktunaxa.referral.server.command.dto.GetEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.GetEmailDataResponse;
import org.ktunaxa.referral.server.command.dto.UpdateEmailDataRequest;
import org.ktunaxa.referral.server.command.dto.UpdateEmailDataResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * Base form used to display a pre formed default email using {@link org.ktunaxa.referral.server.domain.Template}.
 * 
 * @author Emiel Ackermann
 */
public abstract class AbstractEmailForm extends AbstractTaskForm {

	protected String notifier;
	protected TextItem from = new TextItem();
	protected TextItem subject = new TextItem();
	protected TextAreaItem message = new TextAreaItem();
	protected TaskDto task;
	protected RegExpValidator multiAddress;
	protected RegExpValidator oneAddress;
	
	public AbstractEmailForm(String notifier) {
		this.notifier = notifier;
		setStyleName("taskBlockContent");
		
		oneAddress = new RegExpValidator();
		oneAddress.setExpression(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
		oneAddress.setErrorMessage("Invalid email address.");
		
		multiAddress = new RegExpValidator();
		multiAddress.setExpression(KtunaxaConstant.MULTIPLE_MAIL_VALIDATOR_REGEX);
		multiAddress.setErrorMessage("Invalid email address(es). Seperate with ', ' or '; '");
		
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
	}
	
	/**
	 * Get email data from db and put/convert it into values for the items of the form.
	 */
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
	}
	
	/**
	 * Update email data from altered template.
	 */
	public void updateTemplate() {
		UpdateEmailDataRequest request = new UpdateEmailDataRequest(notifier);
		request.setSubject(subject.getValueAsString());
		request.setFrom(from.getValueAsString());
		request.setBody(message.getValueAsString());
		GwtCommand command = new GwtCommand(UpdateEmailDataRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<UpdateEmailDataResponse>() {
			public void execute(UpdateEmailDataResponse response) {
				if (response.isUpdated()) {
					SC.say("Template has been succesfully edited.");
				}
			}
		});
	}
}
