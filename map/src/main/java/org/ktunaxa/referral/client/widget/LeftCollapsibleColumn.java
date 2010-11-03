/*
 * This file is part of Geomajas, a component framework for building
 * rich Internet applications (RIA) with sophisticated capabilities for the
 * display, analysis and management of geographic information.
 * It is a building block that allows developers to add maps
 * and other geographic data capabilities to their web applications.
 *
 * Copyright 2008-2010 Geosparc, http://www.geosparc.com, Belgium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
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

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Column which can be collapsed (to the left).
 *
 * @author Joachim Van der Auwera
 */
public class LeftCollapsibleColumn
		extends VLayout {

	private Label leftTitle;
	private VLayout leftMiddleLayout;
	private Canvas leftContent;

	public LeftCollapsibleColumn(int width) {
		super();
		this.setWidth(width);
		this.setHeight100();

		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setHeight(5);
		this.addMember(spacer);

		HLayout top = new HLayout();
		top.setHeight(30);
		top.setBackgroundImage("[ISOMORPHIC]/images/left_header.png");

		leftTitle = new Label();
		leftTitle.setWidth(width - 95);
		leftTitle.setHeight(10);
		leftTitle.setTop(10);
		leftTitle.setLeft(20);
		top.addChild(leftTitle);

		Img hideImg = new Img("[ISOMORPHIC]/images/left_hide.png");
		hideImg.setWidth(15);
		hideImg.setHeight(15);
		hideImg.setTop(10);
		hideImg.setLeft(width - 25);
		hideImg.setCursor(Cursor.HAND);
		hideImg.setTooltip("Hide this panel");
		hideImg.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				close();
			}
		});
		top.addChild(hideImg);

		this.addMember(top);

		leftMiddleLayout = new VLayout();
		leftMiddleLayout.setWidth100();
		leftMiddleLayout.setHeight100();
		leftMiddleLayout.setStyleName("lccContent");
		leftMiddleLayout.setBackgroundImage("[ISOMORPHIC]/images/left_bg.png");
		this.addMember(leftMiddleLayout);

		HLayout bottom = new HLayout();
		bottom.setHeight(11);
		bottom.setBackgroundImage("[ISOMORPHIC]/images/left_footer.png");
		this.addMember(bottom);

		close();
	}

	public void close() {
		this.hide();
	}

	public void show(String title, Canvas canvas) {
		if (leftContent != null && leftContent != canvas) {
			leftMiddleLayout.removeMember(leftContent);
			leftContent = canvas;
			leftMiddleLayout.addMember(canvas);
		} else if (leftContent == null) {
			leftContent = canvas;
			leftMiddleLayout.addMember(canvas);
		}
		leftTitle.setContents("<div style='font-size:14px; font-weight:bold;'>" + title + "</div>");
		this.show();
	}

}
