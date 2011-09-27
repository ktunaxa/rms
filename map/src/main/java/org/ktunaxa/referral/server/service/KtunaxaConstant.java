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
 * @author Joachim Van der Auwera
 */
public interface KtunaxaConstant {

	double KTUNAXA_TERRITORY_MIN_X = 383546.5;
	double KTUNAXA_TERRITORY_MAX_X = 715337.9;
	double KTUNAXA_TERRITORY_MIN_Y = 5427332.2;
	double KTUNAXA_TERRITORY_MAX_Y = 5816801.6;

	double KTUNAXA_TERRITORY_BUFFER = 10000;
	double KTUNAXA_TERRITORY_BUFFER_MIN_X = KTUNAXA_TERRITORY_MIN_X - KTUNAXA_TERRITORY_BUFFER;
	double KTUNAXA_TERRITORY_BUFFER_MAX_X = KTUNAXA_TERRITORY_MAX_X + KTUNAXA_TERRITORY_BUFFER;
	double KTUNAXA_TERRITORY_BUFFER_MIN_Y = KTUNAXA_TERRITORY_MIN_Y - KTUNAXA_TERRITORY_BUFFER;
	double KTUNAXA_TERRITORY_BUFFER_MAX_Y = KTUNAXA_TERRITORY_MAX_Y + KTUNAXA_TERRITORY_BUFFER;

	double KTUNAXA_TERRITORY_MERCATOR_MIN_X = -13215308.489834055;
	double KTUNAXA_TERRITORY_MERCATOR_MAX_X = -12671536.917193668;
	double KTUNAXA_TERRITORY_MERCATOR_MIN_Y = 6268307.742732821;
	double KTUNAXA_TERRITORY_MERCATOR_MAX_Y = 6891281.903592376;

	String FORM_ID = "formId";
	String FORM_REFERRAL = "refId";
	String FORM_FILE = "file";
	String FORM_GEOMETRY = "geometry";
	String FORM_ERROR_MESSAGE = "errorMessage";
	String FORM_DOCUMENT_TITLE = "title";	
	String FORM_DOCUMENT_ID = "documentId";
	String FORM_DOCUMENT_DISPLAY_URL = "displayUrl";
	String FORM_DOCUMENT_DOWNLOAD_URL = "downloadUrl";
	
	String LAYER_CRS = "EPSG:26911";
	int LAYER_SRID = 26911;
	String MAP_CRS = "EPSG:900913";
	int MAP_SRID = 900913;

	String APPLICATION = "app";
	String MAP_MAIN = "mapMain";
	String MAP_CREATE_REFERRAL = "mapCreateReferral";

	String CREATE_REFERRAL_URL_PARAMETER = "createReferral";

	String URL_DOCUMENT_UPLOAD = "../d/upload/referral/document";

	String LAYER_OSM_ID = "clientLayerOsm";
	String LAYER_REFERENCE_BASE_ID = "clientLayerReferenceBase";
	String LAYER_REFERENCE_BASE_SERVER_ID = "layerReferenceBase";
	String LAYER_REFERENCE_VALUE_ID = "clientLayerReferenceValue";
	String LAYER_REFERENCE_VALUE_SERVER_ID = "layerReferenceValue";
	String LAYER_REFERRAL_ID = "clientLayerReferral";
	String LAYER_REFERRAL_SERVER_ID = "layerReferral";

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
	String ATTRIBUTE_COMMENTS = "comments";
	String ATTRIBUTE_EXTERNAL_AGENCY_TYPE = "externalAgencyType";
	String ATTRIBUTE_EXTERNAL_AGENCY = "externalAgencyName";
	String ATTRIBUTE_PRIORITY = "priority";
	String ATTRIBUTE_PROJECT_DESCRIPTION = "projectDescription";
	String ATTRIBUTE_PROJECT_BACKGROUND = "projectBackground";
	String ATTRIBUTE_FINAL_DISPOSITION = "finalDisposition";
	String ATTRIBUTE_APPLICATION_TYPE = "applicationType";
	String ATTRIBUTE_STATUS = "status";
	String ATTRIBUTE_TYPE = "type";
	String ATTRIBUTE_EXTERNAL_PROJECT_ID = "externalProjectId";
	String ATTRIBUTE_EXTERNAL_FILE_ID = "externalFileId";
	String ATTRIBUTE_ACTIVE_RETENTION_PERIOD = "activeRetentionPeriod";
	String ATTRIBUTE_SEMI_ACTIVE_RETENTION_PERIOD = "semiActiveRetentionPeriod";
	String ATTRIBUTE_CONFIDENTIAL = "confidential";

	String ATTRIBUTE_DOCUMENT_ID = "documentId";
	String ATTRIBUTE_DOCUMENT_TITLE = "title";
	String ATTRIBUTE_DOCUMENT_DESCRIPTION = "description";
	String ATTRIBUTE_DOCUMENT_INCLUDE_IN_REPORT = "includeInReport";
	String ATTRIBUTE_DOCUMENT_CONFIDENTIAL = "confidential";
	String ATTRIBUTE_DOCUMENT_TYPE = "type";
	String ATTRIBUTE_DOCUMENT_DOWNLOAD_URL = "downloadUrl";
	String ATTRIBUTE_DOCUMENT_DISPLAY_URL = "displayUrl";
	String ATTRIBUTE_DOCUMENT_ADDED_BY = "addedBy";
	String ATTRIBUTE_DOCUMENT_ADDITION_DATE = "additionDate";
	String ATTRIBUTE_COMMENT_TITLE = "title";
	String ATTRIBUTE_COMMENT_CONTENT = "content";
	String ATTRIBUTE_COMMENT_CREATION_DATE = "creationDate";
	String ATTRIBUTE_COMMENT_CREATED_BY = "createdBy";
	String ATTRIBUTE_COMMENT_INCLUDE_IN_REPORT = "includeInReport";
	String ATTRIBUTE_COMMENT_REPORT_CONTENT = "reportContent";

	String MAIL_VALIDATOR_REGEX = "^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z]{2,4}$";

	String GEOMARK_BASE_URL = "http://delivery.apps.gov.bc.ca/pub/geomark/geomarks/";
	String GEOMARK_SHAPE_REQUEST = "/asPolygon.shpz?srid=";
	String GEOMARK_WKT_REQUEST = "/asPolygon.wkt?srid=";
	
	/**
	 * Constants used for building and sending emails.
	 * @author Emiel Ackermann
	 *
	 */
	public interface Email {
		/**
		 * The notifiers/identifiers for retrieving {@link Template} objects from the DB.
		 */
		String LEVEL_0 = "notify.level0";
		String START = "notify.start";
		String CHANGE = "notify.change.engagementLevel";
		String RESULT = "notify.result";
		/**
		 * Field names.
		 */
		String FROM_NAME = "from";
		String TO_NAME = "to";
		String SUBJECT_NAME = "subject";
		String MESSAGE_NAME = "message";
		String CC_NAME = "cc";
		String BCC_NAME = "bcc";
	}
}
