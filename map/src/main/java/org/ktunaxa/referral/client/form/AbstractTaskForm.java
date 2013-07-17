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

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;

import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.geometry.Geometry;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.Feature;
import org.geomajas.layer.feature.FeatureTransaction;
import org.ktunaxa.referral.client.gui.LayoutConstant;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.widget.CommunicationHandler;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.Map;

/**
 * Base class for building a task form.
 * 
 * @author Joachim Van der Auwera
 */
public abstract class AbstractTaskForm extends VLayout {

	private HTMLFlow taskTitle = new HTMLFlow();

	private DynamicForm[] forms;

	/**
	 * Constructor.
	 */
	public AbstractTaskForm() {
		super();

		setWidth100();
		setHeight100();
		setMembersMargin(LayoutConstant.MARGIN_SMALL);
		taskTitle.setWidth100();
		addMember(taskTitle);
	}

	/**
	 * Refresh the task, restetting the task title.
	 * 
	 * @param task task
	 */
	public void refresh(TaskDto task) {
		taskTitle.setContents("<h3>" + task.getName() + "</h3>" + "<div>" + task.getDescription() + "</div>");
	}

	/**
	 * Set the form field items.
	 * 
	 * @param formItems form field items
	 */
	public void setFields(FormItem... formItems) {
		DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setFields(formItems);
		form.setColWidths("160", "100%");
		form.setStyleName("taskBlockContent");
		setForms(form);
	}

	/**
	 * Set the forms for this task form. This method should only be called once!
	 * 
	 * @param forms forms to add
	 */
	public void setForms(DynamicForm... forms) {
		if (null != this.forms) {
			throw new IllegalStateException("setForms should only be called once with a non-null value");
		}
		this.forms = forms;
		if (null != forms) {
			for (DynamicForm form : forms) {
				addMember(form);
			}
		}
	}

	/**
	 * Validate the form value and return whether validation succeeded. This will display validation markers if needed.
	 * 
	 * @return true when validation succeeded
	 */
	public boolean validate() {
		boolean valid = true;
		if (null != forms) {
			for (DynamicForm form : forms) {
				valid &= form.validate();
			}
		}
		return valid;
	}

	/**
	 * Validate the form value using callbacks for the correct case. Display validation markers if needed.
	 * 
	 * @param valid action to run when validation succeeded
	 * @param invalid action to run when validation failed
	 */
	public void validate(Runnable valid, Runnable invalid) {
		if (validate()) {
			if (null != valid) {
				valid.run();
			}
		} else {
			if (null != invalid) {
				invalid.run();
			}
		}
	}

	/**
	 * Get the variables from the form.
	 * 
	 * @return form values
	 */
	public abstract Map<String, String> getVariables();

	/**
	 * Convert object to string in a way that works when the object is null.
	 * 
	 * @param object object to convert to string
	 * @return string for the object or null if null was passed
	 */
	protected String nullSafeToString(Object object) {
		if (null == object) {
			return "";
		}
		return object.toString();
	}

	/**
	 * Persist a referral.
	 * 
	 * @param previous
	 * @param current
	 */
	protected void persistReferral(Feature previous, Feature current) {
		VectorLayer referralLayer = MapLayout.getInstance().getReferralLayer();
		final FeatureTransaction ft = new FeatureTransaction();
		ft.setLayerId(referralLayer.getServerLayerId());
		ft.setOldFeatures(new Feature[] { previous });
		ft.setNewFeatures(new Feature[] { current });
		// set geometry to null for the request
		Geometry geometry = previous.getGeometry();
		previous.setGeometry(null);
		current.setGeometry(null);
		PersistTransactionRequest request = new PersistTransactionRequest();
		request.setFeatureTransaction(ft);
		request.setCrs(referralLayer.getMapModel().getCrs());
		GwtCommand command = new GwtCommand(PersistTransactionRequest.COMMAND);
		command.setCommandRequest(request);
		CommunicationHandler.get().execute(command, new AbstractCommandCallback<CommandResponse>() {

			public void execute(CommandResponse response) {
				// all fine
			}
		}, "Saving changes...");
		// reset geometry
		previous.setGeometry(geometry);
		current.setGeometry(geometry);
	}

}