/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.util.WidgetLayout;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.form.EditEmailForm;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
/**
 * Shows an {@link EditEmailForm} used for editing {@link org.ktunaxa.referral.server.domain.Template}s.
 *
 * @author Emiel Ackermann
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
		setModalMaskOpacity(WidgetLayout.modalMaskOpacity);
		centerInPage();
		setCanDragResize(true);
		setShowMinimizeButton(false);
		form = new EditEmailForm();
		addCloseClickHandler(new CloseClickHandler() {

			public void onCloseClick(CloseClientEvent event) {
				hide();
				form.disableButtons();
			}
		});
		form.getCancelButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				hide();
				form.disableButtons();
			}
		});
		form.setFinishHandler(new Runnable() {
			public void run() {
				hide();
			}
		});
		form.getEditButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				form.validate();
			}
		});
		fillDummyTask();
		addItem(form);
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
		dummy.addVariable(KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT,
				"${" + KtunaxaBpmConstant.VAR_ENGAGEMENT_COMMENT + "}");
		dummy.addVariable(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE,
				"${" + KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE + "}");
	}

	public void setEmailTemplate(String notifier) {
		form.refresh(notifier, dummy);
	}
}
