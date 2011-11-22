/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
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
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public final class EmptyForm extends AbstractTaskForm {

	private final boolean finish;

	/**
	 * Create an empty form. The current referral will be marked as finished when requested.
	 *
	 * @param finish should current referral be marked finished when validating?
	 */
	public EmptyForm(boolean finish) {
		super();
		this.finish = finish;
	}

	@Override
	public boolean validate() {
		if (finish) {
			// need to mark referral as finished
			MapLayout mapLayout = MapLayout.getInstance();
			VectorLayer layer = mapLayout.getReferralLayer();
			Feature current = mapLayout.getCurrentReferral();
			Feature previous = new org.geomajas.gwt.client.map.feature.Feature(current, layer).toDto();
			Map<String, Attribute> attributes = current.getAttributes();
			attributes.put(KtunaxaConstant.ATTRIBUTE_STATUS, new ManyToOneAttribute(new AssociationValue(
					new LongAttribute(3L), new HashMap<String, PrimitiveAttribute<?>>())));
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
					// all fine
				}
			});
		}
		return super.validate();
	}

	@Override
	public Map<String, String> getVariables() {
		return new HashMap<String, String>();
	}

}
