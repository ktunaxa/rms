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

	String LAYER_CRS = "EPSG:26911";

	String MAP_CRS = "EPSG:900913";

	String CREATE_REFERRAL_URL_PARAMETER = "createReferral";

	String SEARCH_REFERRAL_URL_PARAMETER = "searchReferral";

	String TITLE_BASE = "Referral Management System";

	String TITLE_SEPARATOR = " - ";

	String TITLE_GENERAL = TITLE_BASE + TITLE_SEPARATOR + "Mapping Dashboard";
	String TITLE_CREATE_REFERRAL = TITLE_BASE + TITLE_SEPARATOR + "Create referral";
	String TITLE_SEARCH_REFERRAL = TITLE_BASE + TITLE_SEPARATOR + "Search referral";
	
	String REFERRAL_LAYER_ID = "referralLayer";
	String REFERRAL_SERVER_LAYER_ID = "referral";

	String REFERENCE_BASE_LAYER_ID = "referenceBaseLayer";
	String REFERENCE_VALUE_LAYER_ID = "referenceValueLayer";

	String TARGET_REFERRAL_ATTRIBUTE_NAME = "targetReferral";
	String NUMBER_ATTRIBUTE_NAME = "number";


}
