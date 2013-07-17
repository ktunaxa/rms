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

import java.util.List;

import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.command.dto.PersistTransactionResponse;
import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.FeatureTransactionEvent;
import org.geomajas.gwt.client.map.event.FeatureTransactionHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.FeatureTransaction;
import org.geomajas.gwt.client.map.feature.LazyLoadCallback;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;
import org.ktunaxa.referral.client.widget.CommunicationHandler;
import org.ktunaxa.referral.server.command.PersistReferralCommand;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Panel for managing the comments of a referral. This panel display the full list of comments or a more detailed view
 * on a single comment.
 * 
 * @author Pieter De Graef
 */
public class CommentPanel extends FeatureEditorPanel {

	public CommentPanel() {
		setWidth100();
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		removeMembers(getMembers());
		setReferralLayer(referralLayer);
		FeatureAttributeEditor editor = new FeatureAttributeEditor(referralLayer, false, new CommentsFormFactory());
		editor.setFeature(referral);
		setEditor(editor);
		addMember(editor);
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight("*");
		addMember(spacer);
	}

	/**
	 * Factory for {@link DocumentsForm}.
	 * 
	 * @author Jan De Moerloose
	 */
	class CommentsFormFactory implements FeatureFormFactory<Canvas> {

		public FeatureForm<Canvas> createFeatureForm(VectorLayer layer) {
			CommentsForm form = new CommentsForm(layer);
			// auto-save
			form.addItemChangedHandler(new ItemChangedHandler() {

				public void onItemChanged(ItemChangedEvent event) {
					persistFeature();
				}
			});
			return form;
		}
	}

}