/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.form;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.ktunaxa.referral.client.gui.MapLayout;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

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
		String url = GWT.getHostPageBaseURL() + "d/reporting/f/" + KtunaxaConstant.LAYER_REFERRAL_SERVER_ID + "/" +
				REPORT_NAME + "." + FORMAT + "?filter=" +
				URL.encodeQueryString(ReferralUtil.createFilter(
						ReferralUtil.createId(MapLayout.getInstance().getCurrentReferral()))) +
				"&userToken=" + GwtCommandDispatcher.getInstance().getUserToken();
		com.google.gwt.user.client.Window.open(url, "_blank", null);
	}

}
