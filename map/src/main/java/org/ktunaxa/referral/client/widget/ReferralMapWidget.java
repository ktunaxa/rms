/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.widget;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.command.CommandResponse;
import org.geomajas.gwt.client.command.CommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.widget.MapWidget;
import org.ktunaxa.referral.server.command.request.GetReferralMapRequest;
import org.ktunaxa.referral.server.command.request.GetReferralMapResponse;

/**
 * Map widget that overrides initialization to use a custom command for getting the configuration. The command response
 * can be consumed by preconfigurable callback functions.
 * 
 * @author Jan De Moerloose
 * 
 */
public class ReferralMapWidget extends MapWidget {

	private String referralId;

	private String taskId;

	private List<MapCallback> callbacks = new ArrayList<MapCallback>();

	/**
	 * Constructs a referral map for the specified referral and task id.
	 * 
	 * @param id map id
	 * @param applicationId application id
	 * @param referralId referral id
	 * @param taskId task id
	 */
	public ReferralMapWidget(String id, String applicationId, String referralId, String taskId) {
		super(id, applicationId);
		this.referralId = referralId;
		this.taskId = taskId;
	}

	@Override
	public void init() {
		GwtCommand commandRequest = new GwtCommand(GetReferralMapRequest.COMMAND);
		commandRequest.setCommandRequest(new GetReferralMapRequest(id, applicationId, referralId, taskId));
		GwtCommandDispatcher.getInstance().execute(commandRequest, new CommandCallback() {

			public void execute(CommandResponse response) {
				if (response instanceof GetReferralMapResponse) {
					GetReferralMapResponse r = (GetReferralMapResponse) response;
					initializationCallback(r);
					for (MapCallback callback : callbacks) {
						callback.onResponse(r);
					}
				}
			}
		});
	}

	/**
	 * Add a callback function to be processed after intialization.
	 * 
	 * @param callback a callback function
	 */
	public void addMapCallback(MapCallback callback) {
		callbacks.add(callback);
	}

	public String getReferralId() {
		return referralId;
	}

	public String getTaskId() {
		return taskId;
	}

	/**
	 * Callback function that will be called when all response data is present and the map is initialized.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public interface MapCallback {

		/**
		 * Perform an action for the specified response.
		 * 
		 * @param response the map response
		 */
		void onResponse(GetReferralMapResponse response);
	}

}
