package org.ktunaxa.referral.client.referral;

import java.util.Map;

import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.command.dto.PersistTransactionResponse;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.FeatureTransaction;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.Attribute;
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

public class ReferralConfirmPage implements WizardPage {

	private Feature feature;

	private ReferralCreationWizard wizard;

	private VLayout widget;

	private VLayout summaryLayout;

	public ReferralConfirmPage(VectorLayer layer, ReferralCreationWizard wizard) {
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
		if(feature != null) {
			boolean even = true;
			for (Map.Entry<String, Attribute> entry : feature.getAttributes().entrySet()) {
				Object value =  feature.getAttributeValue(entry.getKey());
				SummaryLine line = new SummaryLine(entry.getKey(), value == null ? "" : value.toString());
				line.setStyleName(even ? "summaryLine" : "summaryLineDark");
				summaryLayout.addMember(line);
				even = !even;
			}
		} else {
			summaryLayout.clear();
		}
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

	public class ConfirmHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			final FeatureTransaction ft = new FeatureTransaction(feature.getLayer(), new Feature[0],
					new Feature[] { feature });
			PersistTransactionRequest request = new PersistTransactionRequest();
			request.setFeatureTransaction(ft.toDto());
			final MapModel mapModel = feature.getLayer().getMapModel();
			// assume layer crs
			request.setCrs(feature.getLayer().getLayerInfo().getCrs());

			GwtCommand command = new GwtCommand(PersistTransactionRequest.COMMAND);
			command.setCommandRequest(request);

			GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

				public void execute(CommandResponse response) {
					if (response instanceof PersistTransactionResponse) {
						PersistTransactionResponse ptr = (PersistTransactionResponse) response;
						mapModel.applyFeatureTransaction(new FeatureTransaction(ft.getLayer(), ptr
								.getFeatureTransaction()));
					}
				}
			});
		}

	}

	public class CancelHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			wizard.refresh();
		}

	}

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