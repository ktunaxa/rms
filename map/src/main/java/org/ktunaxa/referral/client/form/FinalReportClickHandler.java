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

package org.ktunaxa.referral.client.form;

import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Click handler to display (a preview of) the final report in a separate tab/window.
 *
 * @author Joachim Van der Auwera
 */
public class FinalReportClickHandler implements ClickHandler {

	public static final String REPORT_NAME = "finalReport";
	public static final String FORMAT = "pdf";
	

	/** {@inheritDoc} */
	public void onClick(ClickEvent event) {
		// added random q to avoid eager browser caching on preview
		String url = GWT.getHostPageBaseURL() + "d/reporting/f/" + KtunaxaConstant.LAYER_REFERRAL_SERVER_ID + "/" +
				REPORT_NAME + "." + FORMAT + "?filter=" +
				URL.encodeQueryString(ReferralUtil.createFilter(
						ReferralUtil.createId(MapLayout.getInstance().getCurrentReferral()))) +
				"&userToken=" + GwtCommandDispatcher.getInstance().getUserToken() + "&q=" + Random.nextInt();
		Window.open(url, "_blank", null);
	}

}
