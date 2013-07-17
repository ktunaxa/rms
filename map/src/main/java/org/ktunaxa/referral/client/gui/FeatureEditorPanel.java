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

import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.command.dto.PersistTransactionResponse;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.FeatureTransaction;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.ktunaxa.referral.client.widget.CommunicationHandler;
import org.ktunaxa.referral.server.command.PersistReferralCommand;

import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Common base class for feature editor panels.
 * 
 * @author Jan De Moerloose
 * 
 */
public class FeatureEditorPanel extends VLayout {

	private VectorLayer referralLayer;

	private FeatureAttributeEditor editor;

	protected void persistFeature() {
		FeatureTransaction tx = new FeatureTransaction(referralLayer, new Feature[] { editor.getFeature() },
				new Feature[] { editor.getFeature() });
		PersistTransactionRequest request = new PersistTransactionRequest();
		org.geomajas.layer.feature.FeatureTransaction txDto = tx.toDto();
		// remove the geometry to improve performance !!!
		clearGeometries(txDto);
		request.setFeatureTransaction(txDto);
		request.setCrs(referralLayer.getMapModel().getCrs());
		GwtCommand command = new GwtCommand(PersistReferralCommand.COMMAND);
		command.setCommandRequest(request);
		CommunicationHandler.get().execute(command, new AbstractCommandCallback<PersistTransactionResponse>() {

			@Override
			public void execute(PersistTransactionResponse response) {
				Feature f = new Feature(response.getFeatureTransaction().getNewFeatures()[0], referralLayer);
				Feature current = editor.getFeature();
				// reset the geometry !!!
				f.setGeometry(current.getGeometry());
				editor.setFeature(f);
			}
		}, "Saving referral...");

	}

	public VectorLayer getReferralLayer() {
		return referralLayer;
	}

	public void setReferralLayer(VectorLayer referralLayer) {
		this.referralLayer = referralLayer;
	}

	public FeatureAttributeEditor getEditor() {
		return editor;
	}

	public void setEditor(FeatureAttributeEditor editor) {
		this.editor = editor;
	}

	private void clearGeometries(org.geomajas.layer.feature.FeatureTransaction tx) {
		for (org.geomajas.layer.feature.Feature f : tx.getOldFeatures()) {
			f.setGeometry(null);
		}
		for (org.geomajas.layer.feature.Feature f : tx.getNewFeatures()) {
			f.setGeometry(null);
		}
	}

}
