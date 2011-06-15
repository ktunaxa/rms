/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.List;

import org.geomajas.global.GeomajasConstant;
import org.geomajas.gwt.client.action.menu.SaveEditingAction;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.event.FeatureTransactionEvent;
import org.geomajas.gwt.client.map.event.FeatureTransactionHandler;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.LazyLoadCallback;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.gwt.client.widget.attribute.FeatureFormFactory;

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
public class CommentPanel extends VLayout {

	private FeatureAttributeEditor editor;

	private VectorLayer referralLayer;

	private String referralId;

	public CommentPanel() {
		setWidth100();
	}

	public void init(VectorLayer referralLayer, Feature referral) {
		// clean up (TODO update code)
		removeMembers(getMembers());
		this.referralLayer = referralLayer;
		this.referralId = referral.getId();
		editor = new FeatureAttributeEditor(referralLayer, false, new CommentsFormFactory());
		editor.setFeature(referral);
		MapModel mapModel = referralLayer.getMapModel();
		mapModel.addFeatureTransactionHandler(new EditingCallBack());
		addMember(editor);
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight("*");
		addMember(spacer);
	}

	/**
	 * Factory for {@link DocumentsForm}.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	class CommentsFormFactory implements FeatureFormFactory<Canvas> {

		public FeatureForm<Canvas> createFeatureForm(VectorLayer layer) {
			CommentsForm form = new CommentsForm(layer);
			// auto-save
			form.addItemChangedHandler(new ItemChangedHandler() {

				public void onItemChanged(ItemChangedEvent event) {
					MapModel mapModel = referralLayer.getMapModel();
					mapModel.getFeatureEditor().startEditing(new Feature[] { editor.getFeature() },
							new Feature[] { editor.getFeature() });
					SaveEditingAction action = new SaveEditingAction(mapModel);
					action.onClick(null);
				}
			});
			return form;
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
		}

		public void onTransactionSuccess(FeatureTransactionEvent event) {
			referralLayer.getFeatureStore().getFeature(referralId, GeomajasConstant.FEATURE_INCLUDE_ALL, this);
		}

	}
}