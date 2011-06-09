/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.service;

/**
 * Some constants available to both client and sever.
 * 
 * @author Jan De Moerloose
 * 
 */
public interface KtunaxaConstant {

	String FORM_ID = "formId";
	
	String FORM_FILE = "file";
	
	String FORM_GEOMETRY = "geometry";

	String FORM_ERROR_MESSAGE = "errorMessage";

	String FORM_DOCUMENT_TITLE = "title";
	
	String FORM_DOCUMENT_ID = "documentId";

	String FORM_DOCUMENT_DISPLAY_URL = "displayUrl";

	String FORM_DOCUMENT_DOWNLOAD_URL = "downloadUrl";

	String LAYER_CRS = "EPSG:26911";

	String MAP_CRS = "EPSG:900913";

	String CREATE_REFERRAL_URL_PARAMETER = "createReferral";

	String SEARCH_REFERRAL_URL_PARAMETER = "searchReferral";

	String REFERRAL_LAYER_ID = "referralLayer";
	String REFERRAL_SERVER_LAYER_ID = "referral";

	String REFERENCE_BASE_LAYER_ID = "referenceBaseLayer";
	String REFERENCE_VALUE_LAYER_ID = "referenceValueLayer";

	String ATTRIBUTE_PRIMARY = "primaryClassificationNumber";
	String ATTRIBUTE_SECONDARY = "secondaryClassificationNumber";
	String ATTRIBUTE_YEAR = "calendarYear";
	String ATTRIBUTE_NUMBER = "number";
	String ATTRIBUTE_PROJECT = "projectName";
	String ATTRIBUTE_EMAIL = "contactEmail";
	String ATTRIBUTE_ENGAGEMENT_LEVEL_PROVINCE = "provincialAssessmentLevel";
	String ATTRIBUTE_ENGAGEMENT_LEVEL_FINAL = "finalAssessmentLevel";
	String ATTRIBUTE_RESPONSE_DEADLINE = "responseDeadline";
	String ATTRIBUTE_TARGET_REFERRAL = "targetReferral";
	String ATTRIBUTE_DOCUMENTS = "documents";
	String ATTRIBUTE_EXTERNAL_AGENCY = "externalAgencyName";
	String ATTRIBUTE_PROJECT_DESCRIPTION = "projectDescription";
	String ATTRIBUTE_PROJECT_BACKGROUND = "projectBackground";
	String ATTRIBUTE_DOCUMENT_ID = "documentId";
	String ATTRIBUTE_DOCUMENT_TITLE = "title";
	String ATTRIBUTE_DOCUMENT_DOWNLOAD_URL = "downloadUrl";
	String ATTRIBUTE_DOCUMENT_DISPLAY_URL = "displayUrl";

}
