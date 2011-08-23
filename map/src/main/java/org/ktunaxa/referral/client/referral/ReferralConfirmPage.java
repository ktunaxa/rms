/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.referral;

import org.geomajas.configuration.AttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.layer.feature.attribute.AssociationValue;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.widget.utility.smartgwt.client.wizard.WizardPage;
import org.ktunaxa.referral.client.gui.LayoutConstant;

/**
 * Page to confirm creation of a new referral.
 * 
 * @author Jan De Moerloose
 */
public class ReferralConfirmPage extends WizardPage<ReferralData> {

	private VLayout widget;

	private VLayout summaryLayout;

	public ReferralConfirmPage() {
		super();

		widget = new VLayout();
		widget.setMembersMargin(LayoutConstant.MARGIN_LARGE);
		widget.setWidth100();
		widget.setHeight100();

		summaryLayout = new VLayout();
		summaryLayout.setWidth100();
		summaryLayout.setHeight100();
		summaryLayout.setStyleName("summary");
		widget.addMember(summaryLayout);
	}

	public String getTitle() {
		return "Confirm";
	}

	public String getExplanation() {
		return "Confirm the referral creation or cancel the creation procedure and go back to the task board";
	}

	public boolean doValidate() {
		return true;
	}

	private String valueToString(Object value) {
		if (value instanceof AssociationValue) {
			AssociationValue asso = (AssociationValue) value;
			// is there a better way ?
			return asso.getAllAttributes().values().iterator().next().getValue().toString();
		}
		return value.toString();
	}

	public Canvas asWidget() {
		return widget;
	}

	@Override
	public void clear() {
		summaryLayout.setMembers();
	}

	@Override
	protected void show() {
		summaryLayout.setMembers();
		boolean even = true;
		FeatureInfo featureInfo = getWizardData().getLayer().getLayerInfo().getFeatureInfo();
		for (AttributeInfo info : featureInfo.getAttributes()) {
			Object value = getWizardData().getFeature().getAttributeValue(info.getName());
			SummaryLine line = new SummaryLine(info.getLabel(), value == null ? "" : valueToString(value));
			line.setStyleName(even ? "summaryLine" : "summaryLineDark");
			summaryLayout.addMember(line);
			even = !even;
		}
	}

	/**
	 * A line of the form summary.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	private class SummaryLine extends HLayout {

		public SummaryLine(String name, String value) {
			setHeight100();
			setWidth100();
			setStyleName("layerBlock");

			HTMLFlow nameFlow = new HTMLFlow("<div>" + name + "</div>");
			nameFlow.setWidth("50%");
			nameFlow.setLayoutAlign(VerticalAlignment.CENTER);
			nameFlow.setLayoutAlign(Alignment.LEFT);
			addMember(nameFlow);

			HTMLFlow valueFlow = new HTMLFlow("<div>" + value + "</div>");
			valueFlow.setWidth("50%");
			valueFlow.setLayoutAlign(VerticalAlignment.CENTER);
			valueFlow.setLayoutAlign(Alignment.RIGHT);
			addMember(valueFlow);
		}

	}


}