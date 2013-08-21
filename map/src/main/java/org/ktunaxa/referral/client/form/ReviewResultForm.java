/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.client.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.LongAttribute;
import org.geomajas.layer.feature.attribute.ManyToOneAttribute;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.client.widget.CommunicationHandler;
import org.ktunaxa.referral.server.command.dto.FinishFinalReportTaskRequest;
import org.ktunaxa.referral.server.command.dto.FinishFinalReportTaskResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

/**
 * Form for sending evaluating the result and sending the result notification.
 *
 * @author Joachim Van der Auwera
 */
public class ReviewResultForm extends VerifyAndSendEmailForm {

	private static final String VALUE_INTRODUCTION = "The proposed land and resource development activity falls " +
			"within the territory of the Ktunaxa Nation in south-eastern British Columbia. Ktunaxa Nation has " +
			"Aboriginal Rights, including Title that are protected under Section 35 of the Constitution Act, 1982 of " +
			"Canada.\n" +
			"Further the Ktunaxa Nation filed a \"Statement of Intent\" to negotiate its outstanding Aboriginal " +
			"Title and Treaty Rights in their traditional territory of December of 1993 and negotiations are ongoing.";
	private static final String VALUE_CONCLUSION = "In closing I request a response that outlines how the " +
			"information we have provided and other information you may have gathered from your assessment of " +
			"potential infringements on Aboriginal Rights and Title was used and considered in the decision-making " +
			"process. If you have any questions, please contact Dora Gunn at 250-417-4022, extension 3115.";

	private CheckboxItem skipReportUpload;
	private RadioGroupItem decision;
	private TextAreaItem introduction;
	private TextAreaItem conclusion;
	private Button preview;
	private boolean initialized;

	/**
	 * Construct {@link ReviewResultForm}.
	 */
	public ReviewResultForm() {
		super(KtunaxaConstant.Email.RESULT);

		initTextArea(introduction, "Introduction");
		initTextArea(conclusion, "FinalNote");
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
			preview.addClickHandler(new SaveAndPreviewClickHandler());
		}
		Feature referral = MapLayout.getInstance().getCurrentReferral();
		Map<String, Attribute> attributes = referral.getAttributes();
		if (null == attributes.get(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_INTRODUCTION).getValue()) {
			attributes.put(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_INTRODUCTION,
					new StringAttribute(VALUE_INTRODUCTION));
		}
		if (null == attributes.get(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_CONCLUSION).getValue()) {
			attributes.put(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_CONCLUSION,
					new StringAttribute(VALUE_CONCLUSION));
		}
		introduction.setValue(attributes.get(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_INTRODUCTION).getValue());
		conclusion.setValue(attributes.get(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_CONCLUSION).getValue());
		decision.setValue(
				((AssociationValue) attributes.get(KtunaxaConstant.ATTRIBUTE_DECISION).getValue()).getId().toString());
	}

	@Override
	public void setForms(DynamicForm... forms) {
		preview = new Button("Preview");
		super.addMember(preview);
		
		skipReportUpload = new CheckboxItem();
		skipReportUpload.setTitle("Skip final report upload");
		skipReportUpload.setValue(false);

		introduction = new TextAreaItem();
		introduction.setTitle("Report introduction");
		conclusion = new TextAreaItem();
		conclusion.setTitle("Report conclusion");
		decision = new RadioGroupItem();
		decision.setTitle("Decision");
		LinkedHashMap<String, String> decisionValues = new LinkedHashMap<String, String>();
		decisionValues.put("1", "Unknown");
		decisionValues.put("2", "Approved");
		decisionValues.put("3", "Denied");
		decision.setValueMap(decisionValues);
		decision.setRequired(true);

		DynamicForm messageForm = new DynamicForm();
		messageForm.setWidth100();
		messageForm.setColWidths("13%", "*");
		messageForm.setFields(decision, introduction, conclusion);
		messageForm.setHeight("*");
		
		DynamicForm checkBoxForm = forms[forms.length - 1];
		// put all checkboxes on  top of each other
		checkBoxForm.setNumCols(2);
		checkBoxForm.setWidth100();
		List<FormItem> checkBoxes = new ArrayList<FormItem>();
		for (FormItem c : checkBoxForm.getFields()) {
			checkBoxes.add(c);
		}
		checkBoxes.add(skipReportUpload);
		checkBoxForm.setFields(checkBoxes.toArray(new FormItem[checkBoxes.size()]));
		checkBoxForm.setTitleWidth("70%");

		HTMLFlow divider = new HTMLFlow("<hr/>");
		divider.setWidth100();
		divider.setHeight(5);
		super.addMember(divider);

		// add the message form before
		DynamicForm[] elements = new DynamicForm[forms.length + 1];
		elements[0] = messageForm; // add message before
		System.arraycopy(forms, 0, elements, 1, forms.length);
		super.setForms(elements);
	}

	private void updateReferral(Runnable onUpdate) {
		// update referral itself
		MapLayout mapLayout = MapLayout.getInstance();
		VectorLayer layer = mapLayout.getReferralLayer();
		Feature current = mapLayout.getCurrentReferral();
		Feature previous = new org.geomajas.gwt.client.map.feature.Feature(current, layer).toDto();
		Map<String, Attribute> attributes = current.getAttributes();
		attributes.put(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_INTRODUCTION,
				new StringAttribute(introduction.getValueAsString()));
		attributes.put(KtunaxaConstant.ATTRIBUTE_FINAL_REPORT_CONCLUSION,
				new StringAttribute(conclusion.getValueAsString()));
		attributes.put(KtunaxaConstant.ATTRIBUTE_DECISION, new ManyToOneAttribute(new AssociationValue(
				new LongAttribute(Long.parseLong(decision.getValueAsString())),
				new HashMap<String, PrimitiveAttribute<?>>())));
		persistReferral(previous, current, onUpdate);
	}
	
	/**
	 * Should the report creation, upload to Alfresco and attachment to the referral be skipped ?
	 *
	 * @return true when all of this should be skipped
	 */
	protected boolean isSkipReportUpload() {
		return skipReportUpload.getValueAsBoolean();
	}


	@Override
	public void validate(Runnable valid, final Runnable invalid) {
		updateReferral(new Runnable() {
			
			@Override
			public void run() {
				if (validate()) {
					FinishFinalReportTaskRequest request = new FinishFinalReportTaskRequest();
					setEmailRequest(request);
					request.setSendMail(isSendMail());
					request.setSaveMail(isSaveMail());
					request.setSkipReportUpload(isSkipReportUpload());
					MapLayout mapLayout = MapLayout.getInstance();
					request.setReferralId(ReferralUtil.createId(mapLayout.getCurrentReferral()));
					request.setTaskId(mapLayout.getCurrentTask().getId());
					request.setVariables(getVariables());

					GwtCommand command = new GwtCommand(FinishFinalReportTaskRequest.COMMAND);
					command.setCommandRequest(request);
					CommunicationHandler.get().execute(
							command,
							new AbstractCommandCallback<FinishFinalReportTaskResponse>() {

								public void execute(FinishFinalReportTaskResponse response) {
									if (response.isSuccess()) {
										MapLayout mapLayout = MapLayout.getInstance();
										mapLayout.refreshReferral(true); // clear task
										mapLayout.focusTaskManager();
									} else {
										GwtCommandDispatcher.getInstance().onCommandException(response);
										invalid.run();
									}
								}
							},
							request.isSkipReportUpload() ? "Finishing task, skipping final report..."
									: "Preparing final report... ", invalid);
				} else {
					invalid.run();
				}
			}
		});
	}

	/**
	 * Click handler for preview button, update referral and display report.
	 *
	 * @author Joachim Van der Auwera
	 */
	private class SaveAndPreviewClickHandler implements ClickHandler {

		private PreviewRunnable previewRunnable;

		public void onClick(ClickEvent event) {
			if (null == previewRunnable) {
				previewRunnable = new PreviewRunnable();
			}
			updateReferral(previewRunnable);
		}
	}

	/**
	 * Runnable to actually display the preview after the referral was saved.
	 *
	 * @author Joachim Van der Auwera
	 */
	private class PreviewRunnable implements Runnable {

		private FinalReportClickHandler finalReportClickHandler;

		public void run() {
			if (null == finalReportClickHandler) {
				finalReportClickHandler = new FinalReportClickHandler();
			}
			finalReportClickHandler.onClick(new ClickEvent(null));
		}
	}


}
