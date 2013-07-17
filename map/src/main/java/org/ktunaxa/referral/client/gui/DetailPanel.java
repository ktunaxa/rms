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

package org.ktunaxa.referral.client.gui;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;
import org.ktunaxa.referral.client.gui.MapLayout.Focus;
import org.ktunaxa.referral.client.referral.ReferralDetailForm;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Panel showing detail view of referral. Allows limited updates.
 * 
 * @author Jan De Moerloose
 */
public class DetailPanel extends FeatureEditorPanel {

	private String referralId;

	private RefreshButton refreshButton;

	private EditButton editButton;

	private SaveButton saveButton;

	private CancelButton cancelButton;

	public DetailPanel() {
		setMembersMargin(LayoutConstant.MARGIN_LARGE);
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// clean up (TODO update code)
		removeMembers(getMembers());
		setReferralLayer(referralLayer);
		this.referralId = referral.getId();
		FeatureAttributeEditor editor = new FeatureAttributeEditor(referralLayer, true, new ReferralDetailFormFactory());
		editor.setFeature(referral);
		editor.addItemChangedHandler(new ItemChangedHandler() {

			public void onItemChanged(ItemChangedEvent event) {
				updateButtonState(true);
			}

		});
		editor.setOverflow(Overflow.AUTO);
		setEditor(editor);
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		toolStrip.setPadding(2);
		toolStrip.setMembersMargin(LayoutConstant.MARGIN_SMALL);
		toolStrip.setHeight(32);
		toolStrip.setBackgroundImage("");
		toolStrip.setBorder("none");
		refreshButton = new RefreshButton();
		editButton = new EditButton();
		saveButton = new SaveButton();
		cancelButton = new CancelButton();
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setWidth("*");
		toolStrip.addMember(spacer);
		toolStrip.addMember(refreshButton);
		toolStrip.addMember(editButton);
		toolStrip.addMember(saveButton);
		toolStrip.addMember(cancelButton);
		addMember(toolStrip);
		addMember(editor);
		updateButtonState(false);
	}

	private void updateButtonState(boolean formChanged) {
		if (getEditor().isDisabled()) {
			saveButton.setDisabled(true);
			cancelButton.setDisabled(true);
			editButton.setDisabled(false);
		} else if (formChanged) {
			if (getEditor().validate()) {
				saveButton.setDisabled(false);
			} else {
				saveButton.setDisabled(true);
			}
			cancelButton.setDisabled(false);
			editButton.setDisabled(true);
		} else {
			saveButton.setDisabled(true);
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
	 */
	private class EditButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public EditButton() {
			setIcon(WidgetLayout.iconEdit);
			setIconWidth(LayoutConstant.ICON_BUTTON_LARGE_ICON_WIDTH);
			setIconHeight(LayoutConstant.ICON_BUTTON_LARGE_ICON_HEIGHT);
			setWidth(LayoutConstant.ICON_BUTTON_LARGE_WIDTH);
			setHeight(LayoutConstant.ICON_BUTTON_LARGE_HEIGHT);
			setTooltip("Edit referral");
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			if (getEditor().isDisabled()) {
				getEditor().setDisabled(false);
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
			setIcon(WidgetLayout.iconSave);
			setIconWidth(LayoutConstant.ICON_BUTTON_LARGE_ICON_WIDTH);
			setIconHeight(LayoutConstant.ICON_BUTTON_LARGE_ICON_HEIGHT);
			setWidth(LayoutConstant.ICON_BUTTON_LARGE_WIDTH);
			setHeight(LayoutConstant.ICON_BUTTON_LARGE_HEIGHT);
			setTooltip("Save referral");
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			persistFeature();
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
			setIcon(WidgetLayout.iconRemove);
			setIconWidth(LayoutConstant.ICON_BUTTON_LARGE_ICON_WIDTH);
			setIconHeight(LayoutConstant.ICON_BUTTON_LARGE_ICON_HEIGHT);
			setWidth(LayoutConstant.ICON_BUTTON_LARGE_WIDTH);
			setHeight(LayoutConstant.ICON_BUTTON_LARGE_HEIGHT);
			setTooltip("Cancel");
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			getEditor().reset();
			getEditor().setDisabled(true);
			updateButtonState(false);
		}
	}

	/**
	 * Button that refreshes the referral.
	 * 
	 * @author Jan De Moerloose
	 */
	private class RefreshButton extends IButton implements com.smartgwt.client.widgets.events.ClickHandler {

		public RefreshButton() {
			setIcon(WidgetLayout.iconRefresh);
			setIconWidth(LayoutConstant.ICON_BUTTON_LARGE_ICON_WIDTH);
			setIconHeight(LayoutConstant.ICON_BUTTON_LARGE_ICON_HEIGHT);
			setWidth(LayoutConstant.ICON_BUTTON_LARGE_WIDTH);
			setHeight(LayoutConstant.ICON_BUTTON_LARGE_HEIGHT);
			setTooltip("Refresh referral");
			addClickHandler(this);
		}

		public void onClick(ClickEvent event) {
			MapLayout.getInstance().refreshReferral(false, Focus.REFERRAL_DETAIL);
		}
	}
}