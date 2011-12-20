/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import org.geomajas.gwt.client.map.layer.Layer;
import org.geomajas.gwt.client.map.layer.VectorLayer;

import java.util.LinkedHashMap;

/**
 * {@link LayerBlock} for a referral layer.
 *
 * @author Joachim Van der Auwera
 */
public class ReferralLayerBlock extends LayerBlock {

	private VectorLayer layer;
	private DynamicForm form = new DynamicForm();
	ComboBoxItem status = new ComboBoxItem("Status");

	public ReferralLayerBlock(Layer<?> referral) {
		super(referral);
		this.layer = (VectorLayer) referral;
		
		ChangedHandler filterChangedHandler = new FilterChangedHandler();

		form.setWidth100();
		LinkedHashMap<String, String> statusValues = new LinkedHashMap<String, String>();
		statusValues.put("", "any");
		statusValues.put("1", "Incompletely created");
		statusValues.put("2", "In progress");
		statusValues.put("3", "Finished");
		statusValues.put("4", "Stopped");
		status.setValueMap(statusValues);
		status.setValue("");
		status.addChangedHandler(filterChangedHandler);
		form.setFields(status);
		addMember(form);
	}

	@Override
	protected void updateLayerEnabled(boolean enabled) {
		super.updateLayerEnabled(enabled);
		form.setDisabled(!enabled);
	}

	private class FilterChangedHandler implements ChangedHandler {

		public void onChanged(ChangedEvent changedEvent) {
			StringBuilder filter = new StringBuilder();
			
			String statusValue = status.getValueAsString();
			if (!"".equals(statusValue)) {
				filter.append("status.id = ");
				filter.append(statusValue);
			}
			
			layer.setFilter(filter.toString());
		}
	}
}
