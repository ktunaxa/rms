/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.client.referral;

import java.util.ArrayList;
import java.util.List;

import org.geomajas.gwt.client.map.feature.Feature;
import org.ktunaxa.referral.client.referral.event.WizardPageAddedEvent;
import org.ktunaxa.referral.client.referral.event.WizardPageAddedHandler;
import org.ktunaxa.referral.client.referral.event.WizardPageChangedEvent;
import org.ktunaxa.referral.client.referral.event.WizardPageChangedHandler;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Definition of the referral creation wizard.
 * 
 * @author Pieter De Graef
 */
public class ReferralCreationWizard extends VLayout {

	/**
	 * Definition of an individual page within this wizard.
	 * 
	 * @author Pieter De Graef
	 */
	public interface WizardPage {

		String getTitle();

		String getExplanation();

		boolean validate();

		Canvas asWidget();

		Feature getFeature();

		void setFeature(Feature feature);
		
		void clear();
	}

	private HandlerManager handlerManager;

	private String title;

	private VLayout leftLayout;

	private VLayout pageBody;

	private HTMLFlow pageTitleDiv;

	private List<WizardPage> pages;

	private List<String> pageTitles;

	private int currentIndex;

	public ReferralCreationWizard(String title, String helpText) {
		this.title = title;
		handlerManager = new HandlerManager(this);
		// setWidth(1000);
		setLayoutAlign(Alignment.CENTER);
		setStyleName("block");

		pages = new ArrayList<WizardPage>();
		pageTitles = new ArrayList<String>();
		addMember(createTitle());

		HLayout body = new HLayout();
		leftLayout = new VLayout(10);
		leftLayout.setStyleName("wizardLeftLayout");
		leftLayout.setSize("220", "100%");
		leftLayout.setLayoutRightMargin(10);

		HTMLFlow explanation = new HTMLFlow("<div style='font-size:12px;'>" + helpText + "</div>");
		leftLayout.addMember(explanation);
		body.addMember(leftLayout);

		VLayout rightLayout = new VLayout();
		rightLayout.setLayoutRightMargin(5);
		rightLayout.addMember(createPageTop());
		pageBody = new VLayout();
		rightLayout.addMember(pageBody);
		rightLayout.addMember(createPageBottom());
		body.addMember(rightLayout);

		addMember(body);
	}

	public final HandlerRegistration addWizardPageChangedHandler(final WizardPageChangedHandler handler) {
		return handlerManager.addHandler(WizardPageChangedHandler.TYPE, handler);
	}

	public final HandlerRegistration addWizardPageAddedHandler(final WizardPageAddedHandler handler) {
		return handlerManager.addHandler(WizardPageAddedHandler.TYPE, handler);
	}
	
	public void refresh() {
		for (WizardPage page : pages) {
			page.clear();		
		}
		goToStep(0);
	}

	public void addStep(WizardPage page) {
		final int index = leftLayout.getChildren().length;
		HTMLFlow stepButton = new HTMLFlow(index + ". " + page.getTitle());
		stepButton.setSize("100%", "30px");
		stepButton.setStyleName("wizardButton");
		stepButton.setCursor(Cursor.HAND);
		stepButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				goToStep(index - 1);
			}
		});
		leftLayout.addMember(stepButton);

		page.asWidget().setStyleName("wizardPageBody");
		page.asWidget().setSize("100%", "100%");
		pages.add(page);
		pageTitles.add(page.getExplanation());
		pageBody.addMember(page.asWidget());
		handlerManager.fireEvent(new WizardPageAddedEvent(index));
		if (index > 1) {
			page.asWidget().setVisible(false);
		} else {
			goToStep(0);
		}
	}

	public void goToStep(int index) {
		if (index < 0 || index >= pages.size()) {
			return;
		}

		// Copy the feature from the current page to the next:
		Feature feature = null;
		WizardPage currentPage = pages.get(currentIndex);
		if (currentPage != null) {
			feature = currentPage.getFeature();
		}
		WizardPage newPage = pages.get(index);
		if (newPage != null) {
			newPage.setFeature(feature);
		}

		// Highlight correct step button:
		for (int i = 1; i < leftLayout.getChildren().length; i++) {
			Canvas canvas = leftLayout.getChildren()[i];
			if ((i - 1) == index) {
				canvas.setStyleName("wizardButtonActive");
			} else {
				canvas.setStyleName("wizardButton");
			}
		}

		// Display correct page:
		for (int i = 0; i < pageBody.getChildren().length; i++) {
			Canvas canvas = pageBody.getChildren()[i];
			if (i == index) {
				canvas.setVisible(true);
			} else {
				canvas.setVisible(false);
			}
		}

		// Display correct title:
		pageTitleDiv.setContents(pageTitles.get(index));
		handlerManager.fireEvent(new WizardPageChangedEvent(index, pages.size() - 1));

		currentIndex = index;
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private HLayout createTitle() {
		HLayout titleCanvas = new HLayout(5);
		titleCanvas.setSize("100%", "24px");
		titleCanvas.setStyleName("blockTitle");

		HTMLFlow titleDiv = new HTMLFlow(title);
		titleDiv.setSize("100%", "24px");
		titleDiv.setStyleName("blockTitleText");

		titleCanvas.addMember(titleDiv);
		return titleCanvas;
	}

	private HLayout createPageTop() {
		HLayout layout = new HLayout(10);
		layout.setAlign(VerticalAlignment.CENTER);
		layout.setHeight(40);
		pageTitleDiv = new HTMLFlow();
		pageTitleDiv.setSize("100%", "40px");
		pageTitleDiv.setStyleName("wizardPageTitle");

		layout.addMember(pageTitleDiv);
		layout.addMember(new PreviousButton());
		layout.addMember(new NextButton());

		return layout;
	}

	private HLayout createPageBottom() {
		HLayout layout = new HLayout(10);
		layout.setAlign(VerticalAlignment.CENTER);
		layout.setHeight(40);
		layout.addMember(new LayoutSpacer());
		layout.addMember(new PreviousButton());
		layout.addMember(new NextButton());

		return layout;
	}

	/**
	 * Button that allows the user to go to the next page within the wizard.
	 * 
	 * @author Pieter De Graef
	 */
	private class NextButton extends IButton implements WizardPageChangedHandler, WizardPageAddedHandler, ClickHandler {

		private int index;

		public NextButton() {
			super("Next");
			addClickHandler(this);
			addWizardPageChangedHandler(this);
			addWizardPageAddedHandler(this);
			setLayoutAlign(VerticalAlignment.CENTER);
			setDisabled(true);
		}

		public void onWizardPageChanged(WizardPageChangedEvent event) {
			index = event.getIndex();
			if (index == event.getMaximum()) {
				setDisabled(true);
			} else {
				setDisabled(false);
			}
		}

		public void onClick(ClickEvent event) {
			goToStep(index + 1);
		}

		public void onWizardPageAdded(WizardPageAddedEvent event) {
			if (event.getIndex() > 1) {
				setDisabled(false);
			}
		}
	}

	/**
	 * Button the allows the user to go to the previous page within the wizard.
	 * 
	 * @author Pieter De Graef
	 */
	private class PreviousButton extends IButton implements WizardPageChangedHandler, ClickHandler {

		private int index;

		public PreviousButton() {
			super("Previous");
			addClickHandler(this);
			addWizardPageChangedHandler(this);
			setLayoutAlign(VerticalAlignment.CENTER);
			setDisabled(true);
		}

		public void onWizardPageChanged(WizardPageChangedEvent event) {
			index = event.getIndex();
			if (index == 0) {
				setDisabled(true);
			} else {
				setDisabled(false);
			}
		}

		public void onClick(ClickEvent event) {
			goToStep(index - 1);
		}
	}
}