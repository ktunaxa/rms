/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.smartgwt.client.widgets.form.fields.CheckboxItem;
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
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Empty task form, not requiring input of any values.
 *
 * @author Joachim Van der Auwera
 */
public class ConcernsAddressedForm extends AbstractTaskForm {

	private CheckboxItem decisionConsistent = new CheckboxItem();

	public ConcernsAddressedForm() {
		super();

		decisionConsistent.setName("concernsAddressed");
		decisionConsistent.setTitle("Concerns addressed");
		decisionConsistent.setPrompt("The KLRA concerns have been addressed by the proponent");

		setFields(decisionConsistent);
	}

	@Override
	public void refresh(TaskDto task) {
		super.refresh(task);
		decisionConsistent.setValue(task.getVariables().
				get(KtunaxaBpmConstant.VAR_DECISION_CONSISTENT));
	}

	@Override
	public Map<String, String> getVariables() {
		Map<String, String> result = new HashMap<String, String>();
		result.put(KtunaxaBpmConstant.VAR_DECISION_CONSISTENT + KtunaxaBpmConstant.SET_BOOLEAN,
				nullSafeToString(decisionConsistent.getValue()));
		return result;
	}

	@Override
	public boolean validate() {
		if (decisionConsistent.getValueAsBoolean()) {
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
}
