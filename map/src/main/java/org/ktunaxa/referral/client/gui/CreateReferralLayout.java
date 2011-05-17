/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.ktunaxa.referral.client.referral.ReferralCreationWizard;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.ReferralWizardView;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Layout for creating new referrals. This layout will display a wizard.
 * 
 * @author Pieter De Graef
 */
public class CreateReferralLayout extends VLayout {

	private ReferralCreationWizard wizard;

	public CreateReferralLayout() {
		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);

		VLayout body = new VLayout();
		body.setMargin(10);
		wizard = new ReferralCreationWizard();
		body.addMember((ReferralWizardView) wizard.getView());
		addMember(body);
		wizard.init();
	}
		
	
}