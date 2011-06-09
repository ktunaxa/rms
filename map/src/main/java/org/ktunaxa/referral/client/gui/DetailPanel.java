/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.List;

import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.action.menu.SaveEditingAction;
import org.geomajas.gwt.client.i18n.I18nProvider;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.FeatureTransactionEvent;
import org.geomajas.gwt.client.map.event.FeatureTransactionHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.LazyLoadCallback;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;
import org.ktunaxa.referral.client.referral.ReferralDetailForm;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Panel showing detail view of referral. Allows limited updates.
 * 
 * @author Jan De Moerloose
 */
public class DetailPanel extends VLayout {

	private FeatureAttributeEditor editor;

	private VectorLayer referralLayer;

	private String referralId;

	private EditButton editButton;

	private SaveButton saveButton;

	private CancelButton cancelButton;

	private ResetButton resetButton;

	public DetailPanel() {
		super(10);
		setSize("100%", "100%");
		setPadding(10);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		this.referralLayer = referralLayer;
		this.referralId = referral.getId();
		MapModel mapModel = referralLayer.getMapModel();
		mapModel.addFeatureTransactionHandler(new EditingCallBack());
		editor = new FeatureAttributeEditor(referralLayer, true, new ReferralDetailFormFactory());
		editor.setFeature(referral);
		editor.addItemChangedHandler(new ItemChangedHandler() {

			public void onItemChanged(ItemChangedEvent event) {
				updateButtonState(true);
			}

		});
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(2);
		toolStrip.setMembersMargin(5);
		toolStrip.setHeight(30);
		editButton = new EditButton();
		saveButton = new SaveButton();
		resetButton = new ResetButton();
		cancelButton = new CancelButton();
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setWidth("*");
		toolStrip.addMember(spacer);
		toolStrip.addMember(editButton);
		toolStrip.addMember(saveButton);
		toolStrip.addMember(resetButton);
		toolStrip.addMember(cancelButton);
		addMember(toolStrip);
		addMember(editor);
		updateButtonState(false);
	}

	private void updateButtonState(boolean formChanged) {
		if (editor.isDisabled()) {
			saveButton.setDisabled(true);
			resetButton.setDisabled(true);
			cancelButton.setDisabled(true);
			editButton.setDisabled(false);
		} else if (formChanged) {
			if (editor.validate()) {
				saveButton.setDisabled(false);
			} else {
				saveButton.setDisabled(true);
			}
			resetButton.setDisabled(false);
			cancelButton.setDisabled(false);
			editButton.setDisabled(true);
		} else {
			saveButton.setDisabled(true);
			resetButton.setDisabled(true);
			cancelButton.setDisabled(false);
			editButton.setDisabled(true);
		}
	}

	/**
	 * Factory for {@link ReferralDetailForm}.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class ReferralDetailFormFactory implements FeatureFormFactory<DynamicForm> {

		public FeatureForm<DynamicForm> createFeatureForm(VectorLayer layer) {
			return new ReferralDetailForm(layer);
		}

	}

	/**
	 * Button that enables editing.
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	private class EditButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public EditButton() {
			setIcon("[ISOMORPHIC]/geomajas/osgeo/edit.png");
			setShowDisabledIcon(false);
			setTitle(I18nProvider.getAttribute().btnEditTitle());
			setTooltip(I18nProvider.getAttribute().btnEditTooltip());
			addClickHandler(this);
			setWidth(80);
		}

		public void onClick(ClickEvent event) {
			if (editor.isDisabled()) {
				editor.setDisabled(false);
			}
			updateButtonState(false);
		}

	}

	/**
	 * Button that commits to server.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	private class SaveButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public SaveButton() {
			setIcon("[ISOMORPHIC]/geomajas/osgeo/save1.png");
			setShowDisabledIcon(false);
			setTitle(I18nProvider.getAttribute().btnSaveTitle());
			setTooltip(I18nProvider.getAttribute().btnSaveTooltip());
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			MapModel mapModel = referralLayer.getMapModel();
			mapModel.getFeatureEditor().startEditing(new Feature[] { editor.getFeature() },
					new Feature[] { editor.getFeature() });
			SaveEditingAction action = new SaveEditingAction(mapModel);
			action.onClick(null);
			updateButtonState(false);
		}
	}

	/**
	 * Button that resets unsaved form changes.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	private class ResetButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public ResetButton() {
			setIcon("[ISOMORPHIC]/geomajas/osgeo/undo.png");
			setShowDisabledIcon(false);
			setTitle(I18nProvider.getAttribute().btnResetTitle());
			setTooltip(I18nProvider.getAttribute().btnResetTooltip());
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			editor.reset();
			updateButtonState(false);
		}
	}

	/**
	 * Button that resets the form and disables editing.
	 * 
	 * @author Jan De Moerloose
	 *
	 */
	private class CancelButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public CancelButton() {
			setIcon("[ISOMORPHIC]/geomajas/osgeo/quit.png");
			setShowDisabledIcon(false);
			setTitle(I18nProvider.getAttribute().btnCancelTitle());
			setTooltip(I18nProvider.getAttribute().btnCancelTooltip());
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			editor.reset();
			editor.setDisabled(true);
			updateButtonState(false);
		}
	}

	/**
	 * callback to show the edited feature.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class EditingCallBack implements LazyLoadCallback, FeatureTransactionHandler {

		public void execute(List<Feature> response) {
			editor.setFeature(response.iterator().next());
			updateButtonState(false);
		}

		public void onTransactionSuccess(FeatureTransactionEvent event) {
			referralLayer.getFeatureStore().getFeature(referralId, GeomajasConstant.FEATURE_INCLUDE_ALL, this);
		}

	}

}