/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.referral;

import java.util.Date;
import java.util.HashMap;

import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.gwt.client.widget.FeatureAttributeEditor;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.IntegerAttribute;
import org.geomajas.layer.feature.attribute.LongAttribute;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.ktunaxa.referral.client.referral.ReferralCreationWizard.WizardPage;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.widgets.Canvas;

/**
 * First page in the referral creation wizard: fill in the general referral meta-data.
 * 
 * @author Pieter De Graef
 */
public class ReferralInfoPage implements WizardPage {

	private FeatureAttributeEditor editor;
	
	private VectorLayer layer;

	public ReferralInfoPage(VectorLayer layer) {
		this.layer = layer;
		editor = new FeatureAttributeEditor(layer, false, new ReferralFormFactory());
		Feature feature = createReferralInstance(layer);
		editor.setFeature(feature);
		editor.setWidth100();
		editor.setHeight100();
	}

	public String getTitle() {
		return "Referral information";
	}

	public String getExplanation() {
		return "Fill in all referral related meta-data.";
	}

	public boolean validate() {
		return editor.validate();
	}

	public Canvas asWidget() {
		return editor;
	}

	public Feature getFeature() {
		return editor.getFeature();
	}

	public void setFeature(Feature feature) {
		if (feature != null) {
			editor.setFeature(feature);
		}
	}
	
	public void clear() {
		setFeature(createReferralInstance(layer));
	}
	
	private Feature createReferralInstance(VectorLayer layer) {
		Feature feature = new Feature(layer);
		// set defaults, TODO: make generic implementation calling FeatureModel.newInstance() on the server
		feature.setIntegerAttribute("primaryClassificationNumber", 3500);
		feature.setIntegerAttribute("secondaryClassificationNumber", 10);
		feature.setIntegerAttribute("calendarYear", 11);
		feature.setStringAttribute("externalProjectId", "-99");
		feature.setStringAttribute("externalFileId", "-99");
		feature.setIntegerAttribute("activeRetentionPeriod", 2);
		feature.setIntegerAttribute("semiActiveRetentionPeriod", 5);
		Date nextMonth = new Date();
		CalendarUtil.addMonthsToDate(nextMonth, 1);
		feature.setDateAttribute("responseDeadline", nextMonth);
		feature.setManyToOneAttribute("type", new AssociationValue(new LongAttribute(1L),
				new HashMap<String, PrimitiveAttribute<?>>()));
		feature.setManyToOneAttribute("finalDisposition", new AssociationValue(new IntegerAttribute(1),
				new HashMap<String, PrimitiveAttribute<?>>()));
		feature.setManyToOneAttribute("applicationType", new AssociationValue(new LongAttribute(1L),
				new HashMap<String, PrimitiveAttribute<?>>()));
		feature.setManyToOneAttribute("status", new AssociationValue(new LongAttribute(1L),
				new HashMap<String, PrimitiveAttribute<?>>()));
		feature.setIntegerAttribute("provincialAssessmentLevel", 1);
		feature.setIntegerAttribute("finalAssessmentLevel", 1);
		return feature;
	}

}