package org.ktunaxa.referral.client.deskmanager;

import org.geomajas.gwt.client.command.CommunicationExceptionCallback;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.i18n.I18nProvider;
import org.geomajas.gwt.client.util.CrocEyeNotificationHandler;
import org.geomajas.gwt.client.util.Log;
import org.geomajas.gwt.client.util.Notify;
import org.geomajas.plugin.deskmanager.client.gwt.common.UserApplicationRegistry;
import org.geomajas.plugin.deskmanager.client.gwt.manager.ManagerApplicationLoader;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.widgets.layout.Layout;

public class KtunaxaManagerEntryPoint implements EntryPoint {

	private void init() {
		UserApplicationRegistry reg = UserApplicationRegistry.getInstance();
		reg.register(new KtunaxaUserApplication());

		// Register crock eye notificator.
		Notify.getInstance().setHandler(CrocEyeNotificationHandler.getInstance());
		GwtCommandDispatcher.getInstance().setCommandExceptionCallback(CrocEyeNotificationHandler.getInstance());
		GwtCommandDispatcher.getInstance().setCommunicationExceptionCallback(new CommunicationExceptionCallback() {

			@Override
			public void onCommunicationException(Throwable error) {
				// Hide communication errors from the user, but report to server (try once)
				String msg = I18nProvider.getGlobal().commandCommunicationError() + ":\n" + error.getMessage();
				Log.logError(msg, error);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		init();

		Layout layout = new Layout();
		layout.setWidth100();
		layout.setHeight100();

		ManagerApplicationLoader.getInstance().loadManager(layout);
		layout.draw();

	}

}
