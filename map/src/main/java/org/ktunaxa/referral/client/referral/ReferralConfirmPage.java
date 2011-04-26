/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral;

import com.google.gwt.user.client.Window;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.command.dto.PersistTransactionResponse;
import org.geomajas.configuration.AttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.FeatureTransaction;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.WizardPage;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.ktunaxa.referral.server.command.request.CreateProcessRequest;
import org.ktunaxa.referral.server.command.request.UrlResponse;

import java.util.Date;

/**
 * Page to confirm creation of a new referral.
 * 
 * @author Jan De Moerloose
 * 
 */
public class ReferralConfirmPage implements WizardPage {

	private Feature feature;

	private ReferralCreationWizard wizard;

	private VLayout widget;

	private VLayout summaryLayout;

	public ReferralConfirmPage(ReferralCreationWizard wizard) {
		this.wizard = wizard;
		widget = new VLayout();
		widget.setWidth100();
		widget.setHeight100();

		summaryLayout = new VLayout();
		summaryLayout.setWidth100();
		summaryLayout.setHeight100();
		summaryLayout.setStyleName("summary");
		widget.addMember(summaryLayout);

		IButton confirmButton = new IButton("Ok");
		IButton cancelButton = new IButton("Cancel");
		HLayout buttonLayout = new HLayout();
		buttonLayout.addMember(confirmButton);
		buttonLayout.addMember(cancelButton);
		buttonLayout.setAlign(Alignment.CENTER);
		confirmButton.addClickHandler(new ConfirmHandler());
		cancelButton.addClickHandler(new CancelHandler());
		widget.addMember(buttonLayout);
	}

	public String getTitle() {
		return "Confirm";
	}

	public String getExplanation() {
		return "Confirm the referral creation or cancel the creation procedure and go back to the task board";
	}

	public boolean validate() {
		return true;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
		summaryLayout.setMembers();
		if (feature != null) {
			boolean even = true;
			FeatureInfo featureInfo = feature.getLayer().getLayerInfo().getFeatureInfo();
			for (AttributeInfo info : featureInfo.getAttributes()) {
				Object value = feature.getAttributeValue(info.getName());
				SummaryLine line = new SummaryLine(info.getLabel(), value == null ? "" : valueToString(value));
				line.setStyleName(even ? "summaryLine" : "summaryLineDark");
				summaryLayout.addMember(line);
				even = !even;
			}
		}
	}

	private String valueToString(Object value) {
		if (value instanceof AssociationValue) {
			AssociationValue asso = (AssociationValue) value;
			// is there a better way ?
			return asso.getAttributes().values().iterator().next().getValue().toString();
		}
		return value.toString();
	}

	public Canvas asWidget() {
		return widget;
	}

	public void clear() {
		setFeature(null);
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	/**
	 * Applies feature transaction and moves back to first page.
	 */
	public class ConfirmHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			final FeatureTransaction ft = new FeatureTransaction(feature.getLayer(), new Feature[0],
					new Feature[] { feature });
			PersistTransactionRequest request = new PersistTransactionRequest();
			request.setFeatureTransaction(ft.toDto());
			final MapModel mapModel = feature.getLayer().getMapModel();
			// assume layer crs
			request.setCrs(feature.getLayer().getMapModel().getCrs());

			GwtCommand command = new GwtCommand(PersistTransactionRequest.COMMAND);
			command.setCommandRequest(request);

			GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

				public void execute(CommandResponse response) {
					if (response instanceof PersistTransactionResponse) {
						PersistTransactionResponse ptr = (PersistTransactionResponse) response;
						mapModel.applyFeatureTransaction(new FeatureTransaction(ft.getLayer(), ptr
								.getFeatureTransaction()));
						Feature newfeature = new Feature(ptr.getFeatureTransaction().getNewFeatures()[0], 
								ft.getLayer());
						setFeature(newfeature);
					}
					createProcess();
					// todo : show choice panel : new feature or bpm or map ?
					// wizard.refresh();
				}
			});
		}

	}

	private void createProcess() {
		CreateProcessRequest request = new CreateProcessRequest();
		Integer primary = (Integer) feature.getAttributeValue("primaryClassificationNumber");
		Integer secondary = (Integer) feature.getAttributeValue("secondaryClassificationNumber");
		Integer year = (Integer) feature.getAttributeValue("calendarYear");
		Integer number = (Integer) feature.getAttributeValue("number");
		String referralId = ReferralUtil.createId(primary, secondary, year, number);
		request.setReferralId(referralId);
		request.setDescription((String) feature.getAttributeValue("projectName"));
		request.setEmail((String) feature.getAttributeValue("contactEmail"));
		request.setEngagementLevel((Integer) feature.getAttributeValue("provincialAssessmentLevel"));
		request.setCompletionDeadline((Date) feature.getAttributeValue("reponseDate"));

		GwtCommand command = new GwtCommand(CreateProcessRequest.COMMAND);
		command.setCommandRequest(request);

		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

			public void execute(CommandResponse response) {
				if (response instanceof UrlResponse) {
					Window.Location.assign(((UrlResponse) response).getUrl());
				}
			}
		});

	}

	/**
	 * Moves back to first page.
	 */
	public class CancelHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			wizard.refresh();
		}

	}

	/**
	 * A line of the form summary.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class SummaryLine extends HLayout {

		public SummaryLine(String name, String value) {
			setHeight100();
			setWidth100();
			setStyleName("layerBlock");

			HTMLFlow nameFlow = new HTMLFlow("<div>" + name + "</div>");
			nameFlow.setWidth("50%");
			nameFlow.setLayoutAlign(VerticalAlignment.CENTER);
			nameFlow.setLayoutAlign(Alignment.LEFT);
			addMember(nameFlow);

			HTMLFlow valueFlow = new HTMLFlow("<div>" + value + "</div>");
			valueFlow.setWidth("50%");
			valueFlow.setLayoutAlign(VerticalAlignment.CENTER);
			valueFlow.setLayoutAlign(Alignment.RIGHT);
			addMember(valueFlow);
		}

	}

}