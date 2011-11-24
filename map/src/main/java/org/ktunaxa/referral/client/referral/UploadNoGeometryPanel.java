/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.util.Html;
import org.geomajas.gwt.client.util.HtmlBuilder;
import org.ktunaxa.referral.client.gui.LayoutConstant;
import org.ktunaxa.referral.client.referral.event.GeometryUploadHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel to not upload a geometry.
 * 
 * @author Emiel Ackermann
 *
 */
public class UploadNoGeometryPanel extends VLayout implements UploadGeometryPanel {

	public static final String NAME = "UploadNoGeometryPanel";

	private CheckboxItem box;

	private HTMLFlow note;
	
	private boolean valid;
	
	public UploadNoGeometryPanel() {
		setMembersMargin(LayoutConstant.MARGIN_SMALL);
		DynamicForm form = new DynamicForm();
		form.setColWidths("4%", "*");
		box = new CheckboxItem();
		box.setValue(valid);
		box.setAlign(Alignment.LEFT);
		box.setTitle("Continue without adding a geometry.");
		box.addChangeHandler(new ChangeHandler() {
			
			public void onChange(ChangeEvent event) {
				valid = !box.getValueAsBoolean();
				note.setVisible(valid);
			}
		});
		form.setFields(box);
		addMember(form);
		note = createNote();
		addMember(note);
	}

	public void setFeature(Feature feature) {
		// no implementation
	}

	public HandlerRegistration addGeometryUploadHandler(GeometryUploadHandler handler) {
		// no implementation
		return null;
	}

	private HTMLFlow createNote() {
		HTMLFlow flow = new HTMLFlow(
				HtmlBuilder.divStyleHtmlContent(AddGeometryPage.FLOW_NOTE_STYLE, 
						HtmlBuilder.tag(Html.Tag.B, "Note: ") +
						"If you do not add a geometry, no feature will be placed on the map."));
		flow.setWidth100();
		flow.setVisible(false);
		return flow;
	}
	
	public boolean validate() {
		return valid;
	}
}
