/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.form.EditEmailForm;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
/**
 * Contains a {@link EditEmailForm} used for editing {@link Template}s.
 * @author Emiel Ackermann
 *
 */
public class EditEmailWindow extends Window {

	private static final String EDIT_EMAIL_TEMPLATE = "Edit email template";
	private final TaskDto dummy = new TaskDto();
	private EditEmailForm form;

	public EditEmailWindow() {
		setWidth(700);
		setHeight(700);
		setTitle(EDIT_EMAIL_TEMPLATE);
		setMembersMargin(LayoutConstant.MARGIN_LARGE);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		setCanDragResize(true);
		setShowMinimizeButton(false);
		addCloseClickHandler(new CloseClickHandler() {

			public void onCloseClick(CloseClientEvent event) {
				hide();
			}
		});
		form = new EditEmailForm();
		form.getCancelButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hide();
			}
		});
		form.getEditButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				boolean result = form.validate();
				if (result) {
					// result = update template.
				}
				if (result) {
					hide();
				}
			}
		});
		addItem(form);
		fillDummyTask();
	}

	private void fillDummyTask() {
		dummy.addVariable(KtunaxaBpmConstant.VAR_REFERRAL_ID, 
				"${" + KtunaxaBpmConstant.VAR_REFERRAL_ID + "}");
		dummy.addVariable(KtunaxaBpmConstant.VAR_REFERRAL_NAME, 
				"${" + KtunaxaBpmConstant.VAR_REFERRAL_NAME + "}");
		dummy.addVariable(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL, 
				"${" + KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL + "}");
		dummy.addVariable(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL, 
				"${" + KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL + "}");
		dummy.addVariable(KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT, "" +
				"${" + KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT + "}");
		dummy.addVariable(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE, "" +
				"${" + KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE + "}");
	}

	public void setEmailTemplate(String notifier) {
		form.refresh(notifier, dummy);
	}
}
