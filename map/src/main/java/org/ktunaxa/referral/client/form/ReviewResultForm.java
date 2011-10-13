/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

/**
 * Form for sending evaluating the result and sending the result notification.
 *
 * @author Joachim Van der Auwera
 */
public class ReviewResultForm extends VerifyAndSendEmailForm {

	private TextAreaItem introduction;
	private TextAreaItem finalNote;
	private Button preview;
	private boolean initialized;

	public ReviewResultForm() {
		super(KtunaxaConstant.Email.RESULT);

		initTextArea(introduction, "Introduction");
		initTextArea(finalNote, "FinalNote");
	}

	private void initTextArea(TextAreaItem textArea, String name) {
		textArea.setShowTitle(false);
		textArea.setName(name);
		textArea.setColSpan(2);
		textArea.setWidth("100%");
		textArea.setMinHeight(100);
		textArea.setHeight("150");
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		if (!initialized) {
			initialized = true;
			preview.addClickHandler(new FinalReportClickHandler());
		}
	}

	@Override
	public void setForms(DynamicForm... forms) {
		introduction = new TextAreaItem();
		finalNote = new TextAreaItem();
		preview = new Button("Preview");

		super.addMember(preview);

		DynamicForm messageForm = new DynamicForm();
		messageForm.setWidth("87%");
		messageForm.setLayoutAlign(Alignment.RIGHT);
		messageForm.setFields(introduction, finalNote);
		messageForm.setHeight("*");

		// Add extra fields in the form
		DynamicForm[] elements = new DynamicForm[forms.length + 1];
		elements[0] = messageForm;
		System.arraycopy(forms, 0, elements, 1, forms.length);

		super.setForms(elements);
	}


}
