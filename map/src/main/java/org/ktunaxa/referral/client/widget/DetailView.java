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

package org.ktunaxa.referral.client.widget;

import java.io.Serializable;

import org.ktunaxa.referral.client.gui.LayoutConstant;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * General definition of the detailed view of a single object of type T. It is possible to add a pane to this widget.
 * Usually this pane will be used to add a form so the central object can be edited there.<br/>
 * TODO be more specific about that pane.
 * 
 * @author Pieter De Graef
 * 
 * @param <T>
 *            The main object of interest. These are usually objects of the domain model of the application.
 */
public class DetailView<T extends Serializable> extends VLayout {

	private T object;

	private Button backButton;

	private Canvas body;

	public DetailView() {
		builGui();
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	// TODO be more specific about this pane. It should be able to get and set a T (at least).
	public void setPane(Canvas pane) {
		for (int i = body.getChildren().length - 1; i >= 0; i--) {
			body.removeChild(body.getChildren()[i]);
		}
		pane.setSize("100%", "100%");
		body.addChild(pane);
	}

	public Button getBackButton() {
		return backButton;
	}

	private void builGui() {
		setSize("100%", "100%");

		HLayout header = new HLayout(LayoutConstant.MARGIN_LARGE);
		header.setSize("100%", "40");

		HTMLFlow title = new HTMLFlow("This is a title. It serves no purpose yet.");
		title.setSize("100%", "40");
		header.addMember(title);

		backButton = new Button("Back to list");
		header.addMember(backButton);
		addMember(header);

		body = new VLayout();
		body.setSize("100%", "40");
		addMember(body);
	}
}