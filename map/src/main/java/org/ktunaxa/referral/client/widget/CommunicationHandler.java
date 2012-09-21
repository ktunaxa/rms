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

import org.geomajas.command.CommandResponse;
import org.geomajas.gwt.client.Geomajas;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.Deferred;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.widget.featureinfo.client.widget.DockableWindow;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Shows dialog when waiting for command.
 * 
 * @author Jan De Moerloose
 * 
 */
public class CommunicationHandler {

	private static CommunicationHandler INSTANCE = new CommunicationHandler();

	public static CommunicationHandler get() {
		return INSTANCE;
	}

	public <R extends CommandResponse> Deferred execute(GwtCommand command, CommandCallback<R> callback,
			final String title) {
		return execute(command, callback, title, true, new Runnable() {

			@Override
			public void run() {
				// do nothing
			}
		});
	}

	public <R extends CommandResponse> Deferred execute(GwtCommand command, CommandCallback<R> callback,
			final String title, final Runnable onError) {
		return execute(command, callback, title, true, onError);
	}

	public <R extends CommandResponse> Deferred execute(GwtCommand command, CommandCallback<R> callback,
			final String title, boolean modal, final Runnable onError) {
		final Window window = createWindow(title, modal);
		Deferred deferred = GwtCommandDispatcher.getInstance().execute(command, callback);
		deferred.addCallback(new AbstractCommandCallback<R>() {

			@Override
			public void execute(R response) {
				window.destroy();
			}

			@Override
			public void onCommunicationException(Throwable error) {
				window.destroy();
				onError.run();
			}

			@Override
			public void onCommandException(CommandResponse response) {
				window.destroy();
				onError.run();
			}

		});
		return deferred;
	}

	public Window createWindow(final String title, boolean modal) {		
		if (modal) {
			Window window = createWindow(title);
			window.setIsModal(true);
			window.setShowModalMask(true);
			window.show();
			return window;
		} else {
			final Window window = createWindow(title);
			Timer timer = new Timer() {

				@Override
				public void run() {
					window.show();
				}
			};
			timer.schedule(3000);
			return window;
		}
	}

	private Window createWindow(String title) {
		VLayout layout = new VLayout(5);
		layout.setLayoutAlign(Alignment.CENTER);
		layout.setAlign(Alignment.CENTER);
		layout.setWidth100();
		Label label = new Label(title);
		label.setWidth100();
		label.setHeight(30);
		label.setAlign(Alignment.CENTER);
		layout.addMember(label);

		HTMLPane img = new HTMLPane();
		img.setLayoutAlign(Alignment.CENTER);
		img.setAlign(Alignment.CENTER);
		img.setWidth(20);
		img.setHeight(20);
		img.setContents("<img src=\"" + Geomajas.getIsomorphicDir()
				+ "/geomajas/ajax-loader.gif\" width=\"18\" height=\"18\" />");
		layout.addMember(img);
		Window w = new DockableWindow();
		w.setTitle("Waiting for server...");
		w.setAlign(Alignment.CENTER);
		w.setPadding(5);
		w.setHeight(100);
		w.setWidth(300);
		w.addItem(layout);
		w.setShowMinimizeButton(false);
		w.setShowCloseButton(true);
		w.setKeepInParentRect(true);
		w.setAutoCenter(true);
		return w;
	}

}
