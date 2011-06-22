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
import org.geomajas.gwt.client.widget.MapWidget;
import org.geomajas.gwt.client.widget.wizard.Wizard;
import org.geomajas.gwt.client.widget.wizard.WizardWidget;
import org.ktunaxa.referral.server.command.dto.CreateProcessRequest;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

/**
 * Wizard to create a new referral.
 * 
 * @author Jan De Moerloose
 */
public class ReferralCreationWizard extends Wizard<ReferralData> {

	private MapWidget mapWidget;

	private ReferralData data;

	private VectorLayer layer;

	private Runnable finishAction;

	public ReferralCreationWizard(Runnable finishAction) {
		super(new ReferralWizardView());
		this.finishAction = finishAction;
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
					addPage(new AddGeometryPage(mapWidget));
					addPage(new ReferralInfoPage());
					addPage(new AttachDocumentPage());
					addPage(new ReferralConfirmPage());
					start();
				}
			}
		});
	}

	@Override
	protected void onCancel() {
		// start();
		finishAction.run();
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

					GwtCommandDispatcher.getInstance().execute(command, new CommandCallback<CommandResponse>() {

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
		final String referralId = ReferralUtil.createId(feature);
		request.setReferralId(referralId);
		request.setDescription((String) feature.getAttributeValue(KtunaxaConstant.ATTRIBUTE_PROJECT));
		request.setEmail((String) feature.getAttributeValue(KtunaxaConstant.ATTRIBUTE_EMAIL));
		request.setEngagementLevel((Integer) feature
				.getAttributeValue(KtunaxaConstant.ATTRIBUTE_ENGAGEMENT_LEVEL_PROVINCE));
		request.setCompletionDeadline((Date) feature.getAttributeValue(KtunaxaConstant.ATTRIBUTE_RESPONSE_DEADLINE));

		GwtCommand command = new GwtCommand(CreateProcessRequest.COMMAND);
		command.setCommandRequest(request);

		GwtCommandDispatcher.getInstance().execute(command, new CommandCallback<CommandResponse>() {

			public void execute(CommandResponse response) {
				getView().setLoading(false);
				SC.confirm("Referral " + referralId + " successfully created. Create another ?", new BooleanCallback() {

					public void execute(Boolean value) {
						if (value != null && value) {
							start();
						} else {
							finishAction.run();
						}
					}
				});
			}
		});
	}

	/**
	 * View part of the wizard.
	 * 
	 * @author Jan De Moerloose
	 */
	public static class ReferralWizardView extends WizardWidget<ReferralData> {

		public ReferralWizardView() {
			super("Referral Creation Wizard", "Follow the steps below to create a "
					+ "new referral and add it to the system:");
		}

	}
}
