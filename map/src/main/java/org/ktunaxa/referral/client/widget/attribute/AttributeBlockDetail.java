/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.widget.attribute;

import org.geomajas.gwt.client.i18n.I18nProvider;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.layer.feature.attribute.AssociationValue;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * An {@link AttributeBlockDetail} is a widget that shows a detail form of a one-to-many value.
 * 
 * @author Jan De Moerloose
 * 
 * @param <W> the actual widget
 */
public class AttributeBlockDetail<W extends Widget> extends VLayout {

	private FeatureForm<W> form;

	/**
	 * The ToolStrip that contains the different buttons (save, reset, ...).
	 */
	private ToolStrip toolStrip;

	private IButton backButton;

	private IButton saveButton;

	private IButton resetButton;
	
	private AssociationValue value;

	public AttributeBlockDetail(FeatureForm<W> form) {
		this.form = form;
		form.setDisabled(false);
		form.addItemChangedHandler(new ItemChangedHandler() {

			public void onItemChanged(ItemChangedEvent event) {
				updateButtonState(true);
			}

		});
		buildGui();
	}

	public IButton getBackButton() {
		return backButton;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public AssociationValue getValue() {
		return value;
	}
	
	public boolean validate() {
		return form.validate();
	}

	private void buildGui() {
		setSize("100%", "100%");

		toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(2);
		toolStrip.setMembersMargin(5);
		backButton = new BackButton();
		saveButton = new SaveButton();
		resetButton = new ResetButton();
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setWidth("*");
		toolStrip.addMember(spacer);
		toolStrip.addMember(saveButton);
		toolStrip.addMember(resetButton);
		toolStrip.addMember(backButton);

		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.addMember(toolStrip);
		layout.addMember(form.getWidget());
		addMember(layout);
		updateButtonState(false);
	}
	
	private void updateButtonState(boolean formChanged) {
		if (formChanged) {
			saveButton.setDisabled(false);
		} else {
			saveButton.setDisabled(true);
		}
		if (formChanged) {
			resetButton.setDisabled(false);
		} else {
			resetButton.setDisabled(true);
		}
	}

	/** Definition of the Save button. */
	private class SaveButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public SaveButton() {
			setIcon("[ISOMORPHIC]/geomajas/osgeo/save1.png");
			setShowDisabledIcon(false);
			setTitle(I18nProvider.getAttribute().btnSaveTitle());
			setTooltip(I18nProvider.getAttribute().btnSaveTooltip());
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			form.fromForm(value);
			updateButtonState(false);
		}
	}

	// -------------------------------------------------------------------------
	// Private class BackButton:
	// -------------------------------------------------------------------------

	/** Definition of the Back button to go back to the list. */
	private class BackButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public BackButton() {
			setIcon("[ISOMORPHIC]/geomajas/osgeo/undo.png");
			setShowDisabledIcon(false);
			setTitle("Back");
			setTooltip("Go back to the list");
		}

		public void onClick(ClickEvent event) {
			form.toForm(value);
			updateButtonState(false);
		}
	}

	// -------------------------------------------------------------------------
	// Private class ResetButton:
	// -------------------------------------------------------------------------

	/** Definition of the Reset button that resets the features original attribute values. */
	private class ResetButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public ResetButton() {
			setIcon("[ISOMORPHIC]/geomajas/osgeo/undo.png");
			setShowDisabledIcon(false);
			setTitle(I18nProvider.getAttribute().btnResetTitle());
			setTooltip(I18nProvider.getAttribute().btnResetTooltip());
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			form.toForm(value);
			updateButtonState(false);
		}
	}

	// -------------------------------------------------------------------------
	// Private class CancelButton:
	// -------------------------------------------------------------------------


	public void showDetails(AssociationValue value) {
		this.value = value;
		form.clear();
		form.toForm(value);
		updateButtonState(false);
	}

}