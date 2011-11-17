/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.Date;

/**
 * Window to allow administrators to create a system report.
 *
 * @author Joachim Van der Auwera
 */
public class SystemReportWindow extends Window {

	private static final String WINDOW_TITLE = "System reporting";

	private static final int YEAR = 0;
	private static final int MONTH = 1;
	private static final int LAST = -1;
	private static final int CURRENT = 0;
	private static final int FIRST_YEAR = 1900;

	private final DateItem fromDate = new DateItem();
	private final DateItem tillDate = new DateItem();

	public SystemReportWindow() {
		setWidth(430);
		setHeight(200);
		setTitle(WINDOW_TITLE);
		setMembersMargin(LayoutConstant.MARGIN_LARGE);
		setIsModal(true);
		setShowModalMask(true);
		setModalMaskOpacity(WidgetLayout.modalMaskOpacity);
		centerInPage();
		setCanDragResize(true);
		setShowMinimizeButton(false);
		addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClientEvent event) {
				hide();
			}
		});

		fromDate.setName("fromDate");
		fromDate.setTitle("From date");
		tillDate.setName("tillDate");
		tillDate.setTitle("Till date");

		VLayout formLayout = new VLayout(LayoutConstant.MARGIN_LARGE);

		HLayout selectButtons = new HLayout(LayoutConstant.MARGIN_SMALL);
		selectButtons.setHeight(24);
		selectButtons.addMember(new PeriodButton("This year", YEAR, CURRENT));
		selectButtons.addMember(new PeriodButton("Last year", YEAR, LAST));
		selectButtons.addMember(new PeriodButton("This month", MONTH, CURRENT));
		selectButtons.addMember(new PeriodButton("Last month", MONTH, LAST));
		formLayout.addMember(selectButtons);

		DynamicForm datesForm = new DynamicForm();
		datesForm.setWidth100();
		datesForm.setColWidths("30%", "70%");
		datesForm.setFields(fromDate, tillDate);
		formLayout.addMember(datesForm);

		HLayout reportButtons = new HLayout(LayoutConstant.MARGIN_SMALL);
		reportButtons.setHeight(24);
		reportButtons.addMember(new LayoutSpacer());
		reportButtons.addMember(new ReportButton("Excel report", "xls"));
		reportButtons.addMember(new ReportButton("PDF report", "pdf"));

		formLayout.addMember(reportButtons);

		addItem(formLayout);
	}

	private Date dateStart(Date base, int what, int how) {
		return dateCalc(base, what, how, 0);
	}

	private Date dateEnd(Date base, int what, int how) {
		return dateCalc(base, what, how + 1, -1);
	}

	private Date dateCalc(Date base, int what, int how, int addDays) {
		int[] vector = toDateVector(base);
		vector[what] += how;
		for (int i = what + 1; i <= 2; i++) {
			vector[i] = 1;
		}
		vector[2] += addDays; // Date class handles out-of-range values in a way to make this work
		return toDate(vector);
	}

	@SuppressWarnings("deprecation")
	private static Date toDate(int[] dateVector) {
		return new Date(dateVector[0] - FIRST_YEAR, dateVector[1] - 1, dateVector[2]);
	}

	@SuppressWarnings("deprecation")
	private static int[] toDateVector(Date date) {
		return new int[] { date.getYear() + FIRST_YEAR, date.getMonth() + 1, date.getDate() };
	}

	/**
	 * Button to select a period.
	 *
	 * @author Joachim Van der Auwera
	 */
	private class PeriodButton extends IButton {

		public PeriodButton(String label, final int what, final int how) {
			super(label);
			this.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Date now = new Date();
					fromDate.setValue(dateStart(now, what, how));
					tillDate.setValue(dateEnd(now, what, how));
				}
			});
		}
	}

	/**
	 * Button to create a report.
	 *
	 * @author Joachim Van der Auwera
	 */
	private class ReportButton extends IButton {

		public ReportButton(String label, final String format) {
			super(label);
			this.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					String url = GWT.getHostPageBaseURL();
					url += "d/reporting/f/" + KtunaxaConstant.LAYER_REFERRAL_SERVER_ID + "/" + "systemReport." + format;
					com.google.gwt.user.client.Window.open(url, "_blank", null);
				}
			});
		}
	}
}
