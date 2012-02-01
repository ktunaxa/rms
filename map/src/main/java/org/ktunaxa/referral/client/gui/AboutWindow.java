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

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.EmptyCommandRequest;
import org.geomajas.command.dto.CopyrightRequest;
import org.geomajas.command.dto.CopyrightResponse;
import org.geomajas.global.CopyrightInfo;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.gwt.client.widget.KeepInScreenWindow;

import java.util.Collection;

/**
 * About window for the application.
 *
 * @author Joachim Van der Auwera
 */
public class AboutWindow extends KeepInScreenWindow {

	private static final String SOURCE_URL = "https://github.com/ktunaxa/rms/";
	private static final String AGPL_URL = "http://www.gnu.org/licenses/";

	public AboutWindow() {
		VLayout layout = new VLayout();
		layout.setPadding(WidgetLayout.marginLarge);
		Img logo = new Img("[ISOMORPHIC]/images/ktunaxa.png");
		logo.setWidth(155);
		logo.setHeight(220);
		layout.addMember(logo);
		HTMLFlow flow = new HTMLFlow("<h2>Ktunaxa Referral Management System</h2>"
				+ "<p>This program is free software: you can redistribute it and/or modify "
				+ "it under the terms of the GNU Affero General Public License as published by "
				+ "the Free Software Foundation, either version 3 of the License, or "
				+ "(at your option) any later version.</p>"
				+ "<p>This program is distributed in the hope that it will be useful, "
				+ "but WITHOUT ANY WARRANTY; without even the implied warranty of "
				+ "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
				+ "GNU Affero General Public License for more details.</p>"
				+ "<p>You should have received a copy of the GNU Affero General Public License "
				+ "along with this program.  If not, see "
				+ "<a href='" + AGPL_URL + "'>" + AGPL_URL + "</a>.</p>"
				+ "<p>Source code: <a href='" + SOURCE_URL + "'>" + SOURCE_URL + "</a></p>");
		layout.addMember(flow);

		final HTMLFlow copyrightWidget = new HTMLFlow("Copyright info");
		layout.addMember(copyrightWidget);
		GwtCommand commandRequest = new GwtCommand(CopyrightRequest.COMMAND);
		commandRequest.setCommandRequest(new EmptyCommandRequest());
		GwtCommandDispatcher.getInstance().execute(commandRequest, new CommandCallback() {

			public void execute(CommandResponse response) {
				if (response instanceof CopyrightResponse) {
					Collection<CopyrightInfo> copyrights = ((CopyrightResponse) response).getCopyrights();
					StringBuilder sb = new StringBuilder("<h2>");
					sb.append("Copyright info:");
					sb.append("</h2><ul>");
					for (CopyrightInfo copyright : copyrights) {
						sb.append("<li>");
						sb.append(copyright.getKey());
						sb.append(" : ");
						sb.append("Licensed as:");
						sb.append(" ");
						sb.append(copyright.getCopyright());
						sb.append(" : <a target=\"_blank\" href=\"");
						sb.append(copyright.getLicenseUrl());
						sb.append("\">");
						sb.append(copyright.getLicenseName());
						sb.append("</a>");
						if (null != copyright.getSourceUrl()) {
							sb.append(" ");
							sb.append("Source URL");
							sb.append(" : ");
							sb.append(" <a target=\"_blank\" href=\"");
							sb.append(copyright.getSourceUrl());
							sb.append("\">");
							sb.append(copyright.getSourceUrl());
							sb.append("</a>");
						}
						sb.append("</li>");
					}
					sb.append("</ul>");
					copyrightWidget.setContents(sb.toString());
				}
			}
		});

		setHeaderIcon(WidgetLayout.iconGeomajas);
		setTitle("Ktunaxa RMS");
		setWidth(500);
		setHeight(550);
		setAutoCenter(true);
		setPadding(WidgetLayout.marginLarge);
		addItem(layout);
	}
}
