/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

/**
 * Form used to display a pre formed default email using {@link org.ktunaxa.referral.server.domain.Template}.<br />
 * One EditEmailForm is created and is reloaded, when user switches between templates.
 * 
 * @author Emiel Ackermann
 */
package org.ktunaxa.referral.client.form;

import java.util.Map;

import org.geomajas.gwt.client.util.Html;
import org.geomajas.gwt.client.util.HtmlBuilder;
import org.ktunaxa.referral.client.gui.LayoutConstant;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

/**
 * Form used for editing a {@link Template} found in a linked database. 
 * @author Emiel Ackermann
 *
 */
public class EditEmailForm extends AbstractEmailForm {

	private final Button cancelButton = new Button("Cancel");
	private final Button resetButton = new Button("Reset");
	private final Button editButton = new Button("Edit");
	
	public EditEmailForm() {
		super(null);
		
		cancelButton.setShowRollOver(false);
		cancelButton.setLayoutAlign(VerticalAlignment.CENTER);
		resetButton.setShowRollOver(false);
		resetButton.setLayoutAlign(VerticalAlignment.CENTER);
		resetButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				fillTemplate();
				show();
			}
		});
		editButton.setShowRollOver(false);
		editButton.setLayoutAlign(VerticalAlignment.CENTER);
		
		setEditForm();
	}
	
	private void setEditForm() {
		StringBuilder explanation = new StringBuilder(HtmlBuilder.divClass("commentBlockInfo", 
				"Text between '${' and '}' is replaced with referral data, before the template is shown to an user. " +
				"Allowed placeholders for this template are:"));
		//TODO add a list depending on the notifier.
		explanation.append(HtmlBuilder.divClassHtmlContent("commentBlockInfo",
				"Copy/paste a placeholder to use it in the template."));
		explanation.append(HtmlBuilder.divClassHtmlContent("commentBlockInfo", 
				HtmlBuilder.tagClass(Html.Tag.SPAN, "commentBlockTitleText", "Warning: ") + 
				"Using unallowed text between the '${' and '}', will break the template."));
		HTMLFlow label = new HTMLFlow(explanation.toString());  
		label.setWidth100();  
		addMember(label);
		DynamicForm mail = new DynamicForm();
		mail.setWidth100();
		mail.setColWidths("13%", "87%");
		mail.setFields(from, subject);
		
		DynamicForm messageForm = new DynamicForm();
		messageForm.setWidth("87%");
		messageForm.setLayoutAlign(Alignment.RIGHT);
		messageForm.setHeight("*");
		messageForm.setFields(message);
		
		setForms(mail, messageForm);
		
		HLayout buttons = new HLayout(LayoutConstant.MARGIN_SMALL);
		buttons.setHeight(50);
		buttons.addMember(new LayoutSpacer());
		buttons.addMember(cancelButton);
		buttons.addMember(resetButton);
		buttons.addMember(editButton);
		
		addMember(buttons);
	}

	public void refresh(String notifier, TaskDto task) {
		this.notifier = notifier;
		this.task = task;
		fillTemplate();
	}
	
	@Override
	public Map<String, String> getVariables() {
		return null;
	}

	public Button getCancelButton() {
		return cancelButton;
	}
	
	public Button getEditButton() {
		return editButton;
	}
}
