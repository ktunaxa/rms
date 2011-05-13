/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral;

import java.util.Date;

import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.command.dto.PersistTransactionResponse;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.MapModelEvent;
import org.geomajas.gwt.client.map.event.MapModelHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.FeatureTransaction;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.util.WindowUtil;
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.wizard.Wizard;
import org.geomajas.gwt.client.widget.wizard.WizardWidget;
import org.ktunaxa.referral.server.command.request.CreateProcessRequest;
import org.ktunaxa.referral.server.command.request.UrlResponse;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

/**
 * Wizard to create a new referral.
 * 
 * @author Jan De Moerloose
 * 
 */
public class ReferralCreationWizard extends Wizard<ReferralData> {

	private MapWidget mapWidget;

	private ReferralData data;

	private VectorLayer layer;

	public ReferralCreationWizard() {
		super(new ReferralWizardView());
	}

	public void init() {
		// need layer instance to start editing
		mapWidget = new MapWidget("mapTestReferral", "app");
		mapWidget.setVisible(false);
		mapWidget.init();
		mapWidget.getMapModel().addMapModelHandler(new MapModelHandler() {

			public void onMapModelChange(MapModelEvent event) {
				layer = (VectorLayer) mapWidget.getMapModel().getLayer("referralLayer");
				if (layer != null) {
					addPage(new ReferralInfoPage());
					addPage(new AddGeometryPage(mapWidget));
					addPage(new AttachDocumentPage());
					addPage(new ReferralConfirmPage());
					start();
				}
			}
		});

	}

	@Override
	protected void onCancel() {
		start();
	}

	@Override
	protected void onFinish() {
		SC.confirm("Are you sure you want to create the referral ?", new BooleanCallback() {

			public void execute(Boolean value) {
				if (value != null && value) {
					getView().setLoading(true);
					final FeatureTransaction ft = new FeatureTransaction(layer, new Feature[0], new Feature[] { data
							.getFeature() });
					PersistTransactionRequest request = new PersistTransactionRequest();
					request.setFeatureTransaction(ft.toDto());
					final MapModel mapModel = layer.getMapModel();
					// assume layer crs
					request.setCrs(layer.getMapModel().getCrs());

					GwtCommand command = new GwtCommand(PersistTransactionRequest.COMMAND);
					command.setCommandRequest(request);

					GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

						public void execute(CommandResponse response) {
							if (response instanceof PersistTransactionResponse) {
								PersistTransactionResponse ptr = (PersistTransactionResponse) response;
								mapModel.applyFeatureTransaction(new FeatureTransaction(ft.getLayer(), ptr
										.getFeatureTransaction()));
								Feature newfeature = new Feature(ptr.getFeatureTransaction().getNewFeatures()[0], ft
										.getLayer());
								createProcess(newfeature);
							}
						}
					});
				}
			}
		});
	}

	private void start() {
		data = new ReferralData(layer);
		start(data);
	}

	private void createProcess(Feature feature) {
		CreateProcessRequest request = new CreateProcessRequest();
		Integer primary = (Integer) feature.getAttributeValue("primaryClassificationNumber");
		Integer secondary = (Integer) feature.getAttributeValue("secondaryClassificationNumber");
		Integer year = (Integer) feature.getAttributeValue("calendarYear");
		Integer number = (Integer) feature.getAttributeValue("number");
		final String referralId = ReferralUtil.createId(primary, secondary, year, number);
		request.setReferralId(referralId);
		request.setDescription((String) feature.getAttributeValue("projectName"));
		request.setEmail((String) feature.getAttributeValue("contactEmail"));
		request.setEngagementLevel((Integer) feature.getAttributeValue("provincialAssessmentLevel"));
		request.setCompletionDeadline((Date) feature.getAttributeValue("responseDeadline"));

		GwtCommand command = new GwtCommand(CreateProcessRequest.COMMAND);
		command.setCommandRequest(request);

		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback() {

			public void execute(CommandResponse response) {
				final UrlResponse urlResponse = (UrlResponse) response;
				if (response instanceof UrlResponse) {
					getView().setLoading(false);
					SC.confirm("Referral " + referralId + " successfully created. Create another ?",
							new BooleanCallback() {

								public void execute(Boolean value) {
									if (value != null && value) {
										start();
									} else {
										WindowUtil.setLocation(urlResponse.getUrl());
									}
								}
							});
				}
			}
		});

	}

	/**
	 * View part of the wizard.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public static class ReferralWizardView extends WizardWidget<ReferralData> {

		public ReferralWizardView() {
			super("Referral Creation Wizard", "Follow the steps below to create a "
					+ "new referral and add it to the system:");
		}

	}

}
