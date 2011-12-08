/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.Feature;
import org.geomajas.layer.feature.FeatureTransaction;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.LongAttribute;
import org.geomajas.layer.feature.attribute.ManyToOneAttribute;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.geomajas.layer.feature.attribute.StringAttribute;
import org.geomajas.plugin.rasterizing.client.image.ImageUrlService;
import org.geomajas.plugin.rasterizing.client.image.ImageUrlServiceImpl;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.command.dto.FinishFinalReportTaskRequest;
import org.ktunaxa.referral.server.command.dto.FinishFinalReportTaskResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

		HTMLFlow divider = new HTMLFlow("<hr/>");
		divider.setWidth100();
		divider.setHeight(5);
		super.addMember(divider);

		// Add extra fields in the form
		DynamicForm[] elements = new DynamicForm[forms.length + 1];
		elements[0] = messageForm;
		System.arraycopy(forms, 0, elements, 1, forms.length);

		super.setForms(elements);
	}

	@Override
	public Map<String, String> getVariables() {
		updateReferral(null);
		return super.getVariables();
	}

	private void updateReferral(final Runnable onUpdate) {
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
		final FeatureTransaction ft = new FeatureTransaction();
		ft.setLayerId(layer.getServerLayerId());
		ft.setOldFeatures(new Feature[] {previous});
		ft.setNewFeatures(new Feature[] {current});
		PersistTransactionRequest request = new PersistTransactionRequest();
		request.setFeatureTransaction(ft);
		request.setCrs(layer.getMapModel().getCrs());
		GwtCommand command = new GwtCommand(PersistTransactionRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback<CommandResponse>() {
			public void execute(CommandResponse response) {
				if (null != onUpdate) {
					onUpdate.run();
				}
			}
		});
	}

	@Override
	public void validate(Runnable valid, final Runnable invalid) {
		if (validate()) {
			FinishFinalReportTaskRequest request = new FinishFinalReportTaskRequest();
			setEmailRequest(request);
			request.setSendMail(isSendMail());
			MapLayout mapLayout = MapLayout.getInstance();
			request.setReferralId(ReferralUtil.createId(mapLayout.getCurrentReferral()));
			request.setTaskId(mapLayout.getCurrentTask().getId());
			request.setVariables(getVariables());

			GwtCommand command = new GwtCommand(FinishFinalReportTaskRequest.COMMAND);
			command.setCommandRequest(request);
			GwtCommandDispatcher.getInstance().execute(command,
					new AbstractCommandCallback<FinishFinalReportTaskResponse>() {
						public void execute(FinishFinalReportTaskResponse response) {
							if (response.isSuccess()) {
								MapLayout mapLayout = MapLayout.getInstance();
								mapLayout.setReferralAndTask(mapLayout.getCurrentReferral(), null); // clear task
								mapLayout.focusBpm();
							} else {
								GwtCommandDispatcher.getInstance().onCommandException(response);
								invalid.run();
							}
						}
					});
		} else {
			invalid.run();
		}
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
