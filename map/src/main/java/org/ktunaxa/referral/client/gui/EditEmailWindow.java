/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.KeepInScreenWindow;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
/**
 * Shows an {@link EditEmailTemplateForm} used for editing {@link org.ktunaxa.referral.server.domain.Template}s.
 *
 * @author Emiel Ackermann
 */
public class EditEmailWindow extends KeepInScreenWindow {

	private static final String EDIT_EMAIL_TEMPLATE = "Edit email template";
	private final TaskDto dummy = new TaskDto();
	private final EditEmailTemplateForm form = new EditEmailTemplateForm();

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
		MyCloseClickHandler closeClickHandler = new MyCloseClickHandler();
		addCloseClickHandler(closeClickHandler);
		form.getCancelButton().addClickHandler(closeClickHandler);
		form.setFinishHandler(new Runnable() {
			public void run() {
				hide();
			}
		});
		form.getSaveButton().addClickHandler(new ClickHandler() {

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

	/**
	 * Handler to close the e-mail window. This also disables the buttons.
	 *
	 * @author Joachim Van der Auwera
	 */
	private class MyCloseClickHandler implements ClickHandler, CloseClickHandler {
		public void onClick(ClickEvent event) {
			hide();
			form.disableButtons();
		}

		public void onCloseClick(CloseClientEvent event) {
			onClick(null);
		}
	}
}
