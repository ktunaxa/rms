/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Layout for searching referrals...
 * 
 * @author Pieter De Graef
 */
public class SearchReferralLayout extends VLayout {

	public SearchReferralLayout() {
		super();
		setWidth("100%");
		setOverflow(Overflow.VISIBLE);

		addMember(new Label("Not implemented yet."));
	}
}