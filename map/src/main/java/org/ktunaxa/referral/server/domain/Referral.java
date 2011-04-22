/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

/**
 * The central referral object.<br/>
 * TODO automatic sorting of all collections by date?
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "referral")
public class Referral {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Lands Referral ID: 3500-10/11-0001
	 * - 3500: Primary Classification Number (3500 = Referrals)
	 * - 10: Secondary Classification Number (10 = Specific Referrals).  Can be any digit from 01 to 99
	 * - 11: Current calendar year when referral record was created
	 * - 0001: Unique Referral Number.  Each year they will start back at one (1)
	 * Transient field, all parts are stored separately in the database
	 */
	
	@Column(name = "primary_classificiation_nr", nullable = false, updatable = false)
	private int primaryClassificationNumber = 3500;

	@Column(name = "secondary_classificiation_nr", nullable = false, updatable = false)
	private int secondaryClassificationNumber = 10;

	@Column(name = "calendar_year", nullable = true, updatable = false)
	private int calendarYear = 11;

	/** Lands referral unique number 1 -> 9999 (sequence, resets after each new year). */
	@Column(name = "number", nullable = true, insertable = false, updatable = false)
	private int number;

	// General project information:

	/** The proponent of this project. */
	@Column(nullable = false, name = "applicant_name")
	private String applicantName;

	/** The activity project name. */
	@Column(nullable = false, name = "project_name")
	private String projectName;

	/** Proposed project (activity) description. */
	@Column(nullable = false, name = "project_description")
	private String projectDescription;

	/** Descriptive location where project/activity is occurring. */
	@Column(nullable = false, name = "project_location")
	private String projectLocation;

	/**
	 * Relevant background information on application, current activities if any, or any other known plans associated
	 * with activity.
	 */
	@Column(name = "project_background")
	private String projectBackground;

	/** Where an activity will require the input of other provincial agencies. This common number will link them all. */
	@Column(nullable = false, name = "external_project_id")
	private String externalProjectId = "-99";

	/** Document file id which tracks project/activity in provincial agency. */
	@Column(nullable = false, name = "external_file_id")
	private String externalFileId = "-99";

	/** Name of provincial or external client agency who submitted referral. */
	@Column(name = "external_agency_name")
	private String externalAgencyName;

	// General contact information:

	/** Provincial agency or external client contact name. */
	@Column(nullable = false, name = "contact_name")
	private String contactName;

	/** Provincial agency or external client contact email address. */
	@Column(nullable = false, name = "contact_email")
	private String contactEmail;

	/** Provincial agency or external client contact phone number. */
	@Column(nullable = false, name = "contact_phone")
	private String contactPhone;

	/** Provincial agency or external client contact complete physical address. */
	@Column(name = "contact_address")
	private String contactAddress;

	// General referral information:

	/** Type of activity occurring in the land: Forestry, Mining, etc... */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id", nullable = false)
	private ReferralType type;

	/** The type of application: New, Renewal, Amendment, Replacement, ... */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "application_type_id", nullable = false)
	private ReferralApplicationType applicationType;

	/**
	 * If the application type is a renewal, amendment or replacement of an existing referral, this is that target
	 * referral. Otherwise this object is null.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_referral_id")
	private Referral targetReferral;

	/**
	 * Access type. Default as non-confidential. Are all projects deemed to be open to staff at Lands and Community if
	 * they have credentials?
	 */
	@Column(nullable = false, name = "confidential")
	private boolean confidential;

	// Deadline information:

	/** Date when initial referral package was received. */
	@Column(nullable = false, name = "receive_date")
	private Date receiveDate = new Date();

	/** Date when initial referral package was received. */
	@Column(nullable = true, name = "create_date", insertable = false, updatable = false)
	private Date createDate;

	/** Date when initial referral package was received. */
	@Column(nullable = true, name = "create_user", insertable = false, updatable = false)
	private String createUser;

	/** The date when the official response is sent to the contact. */
	@Column(nullable = false, name = "response_date")
	private Date reponseDate;

	/** Deadline date for response. */
	@Column(nullable = false, name = "response_deadline")
	private Date reponseDeadline;

	/** Active retention of record - in Years (2Y). */
	@Column(nullable = false, name = "active_retention_period")
	private int activeRetentionPeriod = 2;

	/** Semi-active retention of record - in Years (5Y). */
	@Column(nullable = false, name = "semi_active_retention_period")
	private int semiActiveRetentionPeriod = 5;

	/** Destruction (D), Permanent Retention - Archives (P), Selective Retention - Archives (SR). */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "final_disposition_id", nullable = false)
	private ReferralDispositionType finalDisposition = ReferralDispositionType.DEFAULT;

	// Other information:

	/** Provincial Agency's assessment or the appropriate engagement level. */
	@Column(name = "provincial_assessment_level")
	private int provincialAssessmentLevel = 1;

	/** Provincial Agency's assessment or the appropriate engagement level. */
	@Column(name = "final_assessment_level")
	private int finalAssessmentLevel = 1;

	/** What is the current status of this referral? (new, in progress, approved, denied) */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id", nullable = false)
	private ReferralStatus status;

	/** The geometry that represents this referral on the map. */
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(nullable = false, name = "geom")
	private Geometry geometry;

	/** The collection of all documents associated with this referral. */
	@OneToMany(mappedBy = "referral", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@OrderBy("title asc")
	private List<Document> documents = new ArrayList<Document>();

	/** The collection of all comments made on this referral. */
	@OneToMany(mappedBy = "referral", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<ReferralComment> comments = new ArrayList<ReferralComment>();

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public Referral() {
	}

	public Referral(long id) {
		this.id = id;
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The aspect's unique identifier.
	 * 
	 * @param id
	 *            The new value for the identifier.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the value of the identifier.
	 * 
	 * @return the value of the identifier.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get the geometry that represents this referral on the map.
	 * 
	 * @return The geometry that represents this referral on the map.
	 */
	public Geometry getGeometry() {
		return geometry;
	}

	/**
	 * Set the geometry that represents this referral on the map.
	 * 
	 * @param geometry
	 *            The new geometry.
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	/**
	 * Get the current status of this referral (new, in progress, approved, denied).
	 * 
	 * @return the current status of this referral? (new, in progress, approved, denied)
	 */
	public ReferralStatus getStatus() {
		return status;
	}

	/**
	 * Set the current status of this referral? (new, in progress, approved, denied).
	 * 
	 * @param status
	 *            The new status.
	 */
	public void setStatus(ReferralStatus status) {
		this.status = status;
	}

	/**
	 * Get the full name of the person to contact concerning any and all updates in processing this referral.
	 * 
	 * @return The full name of the person to contact concerning any and all updates in processing this referral.
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Set the full name of the person to contact concerning any and all updates in processing this referral.
	 * 
	 * @param contactName
	 *            The new contact name.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Get the email address to mail to concerning any and all updates in processing this referral.
	 * 
	 * @return The email address to mail to concerning any and all updates in processing this referral.
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * Set the email address to mail to concerning any and all updates in processing this referral.
	 * 
	 * @param contactEmail
	 *            The new contact email address.
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * Get the list of all documents associated with this referral.
	 * 
	 * @return The list of all documents associated with this referral.
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * Set the list of all documents associated with this referral.
	 * 
	 * @param documents
	 *            The new list of associated documents.
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	/**
	 * Get the list of all comments made on this referral.
	 * 
	 * @return The list of all comments made on this referral.
	 */
	public List<ReferralComment> getComments() {
		return comments;
	}

	/**
	 * Set the list of all comments made on this referral.
	 * 
	 * @param comments
	 *            The new list of comments.
	 */
	public void setComments(List<ReferralComment> comments) {
		this.comments = comments;
	}
	
	public int getPrimaryClassificationNumber() {
		return primaryClassificationNumber;
	}

	
	public void setPrimaryClassificationNumber(int primaryClassificationNumber) {
		this.primaryClassificationNumber = primaryClassificationNumber;
	}

	
	public int getSecondaryClassificationNumber() {
		return secondaryClassificationNumber;
	}

	
	public void setSecondaryClassificationNumber(int secondaryClassificationNumber) {
		this.secondaryClassificationNumber = secondaryClassificationNumber;
	}

	
	public int getCalendarYear() {
		return calendarYear;
	}

	
	public void setCalendarYear(int calendarYear) {
		this.calendarYear = calendarYear;
	}

	/**
	 * Get unique number (resets after each new year).
	 * 
	 * @return number referral unique number.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Set unique number (resets after each new year).
	 * 
	 * @param number
	 *            The new value.
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Get the proponent of this project.
	 * 
	 * @return The proponent of this project.
	 */
	public String getApplicantName() {
		return applicantName;
	}

	/**
	 * Set the proponent of this project.
	 * 
	 * @param applicantName
	 *            The new value.
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * Get the activity project name.
	 * 
	 * @return The activity project name.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Set the activity project name.
	 * 
	 * @param projectName
	 *            The new value
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Get the proposed project (activity) description.
	 * 
	 * @return Proposed project (activity) description.
	 */
	public String getProjectDescription() {
		return projectDescription;
	}

	/**
	 * Set the proposed project (activity) description.
	 * 
	 * @param projectDescription
	 *            The new value.
	 */
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	/**
	 * Get the descriptive location where project/activity is occurring.
	 * 
	 * @return Descriptive location where project/activity is occurring.
	 */
	public String getProjectLocation() {
		return projectLocation;
	}

	/**
	 * Set the descriptive location where project/activity is occurring.
	 * 
	 * @param projectLocation
	 *            The new value.
	 */
	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	/**
	 * Get relevant background information on application, current activities if any, or any other known plans
	 * associated with activity.
	 * 
	 * @return Relevant background information on application, current activities if any, or any other known plans
	 *         associated with activity.
	 */
	public String getProjectBackground() {
		return projectBackground;
	}

	/**
	 * Set relevant background information on application, current activities if any, or any other known plans
	 * associated with activity.
	 * 
	 * @param projectBackground
	 *            The new value.
	 */
	public void setProjectBackground(String projectBackground) {
		this.projectBackground = projectBackground;
	}

	/**
	 * Get where an activity will require the input of other provincial agencies. This common number will link them all.
	 * 
	 * @return Where an activity will require the input of other provincial agencies. This common number will link them
	 *         all.
	 */
	public String getExternalProjectId() {
		return externalProjectId;
	}

	/**
	 * Set where an activity will require the input of other provincial agencies. This common number will link them all.
	 * 
	 * @param externalProjectId
	 *            The new value.
	 */
	public void setExternalProjectId(String externalProjectId) {
		this.externalProjectId = externalProjectId;
	}

	/**
	 * Get the document file id which tracks project/activity in provincial agency.
	 * 
	 * @return Document file id which tracks project/activity in provincial agency.
	 */
	public String getExternalFileId() {
		return externalFileId;
	}

	/**
	 * Set the document file id which tracks project/activity in provincial agency.
	 * 
	 * @param externalFileId
	 *            The new value
	 */
	public void setExternalFileId(String externalFileId) {
		this.externalFileId = externalFileId;
	}

	/**
	 * Get the name of provincial or external client agency who submitted referral.
	 * 
	 * @return Name of provincial or external client agency who submitted referral.
	 */
	public String getExternalAgencyName() {
		return externalAgencyName;
	}

	/**
	 * Set the name of provincial or external client agency who submitted referral.
	 * 
	 * @param externalAgencyName
	 *            The new value.
	 */
	public void setExternalAgencyName(String externalAgencyName) {
		this.externalAgencyName = externalAgencyName;
	}

	/**
	 * Get the provincial agency or external client contact phone number.
	 * 
	 * @return Provincial agency or external client contact phone number.
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * Set the provincial agency or external client contact phone number.
	 * 
	 * @param contactPhone
	 *            The new value.
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * Get the provincial agency or external client contact complete physical address.
	 * 
	 * @return Provincial agency or external client contact complete physical address.
	 */
	public String getContactAddress() {
		return contactAddress;
	}

	/**
	 * Set the provincial agency or external client contact complete physical address.
	 * 
	 * @param contactAddress
	 *            The new value.
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	/**
	 * Get the type of activity occurring in the land: Forestry, Mining, etc...
	 * 
	 * @return Type of activity occurring in the land: Forestry, Mining, etc...
	 */
	public ReferralType getType() {
		return type;
	}

	/**
	 * Set the type of activity occurring in the land: Forestry, Mining, etc...
	 * 
	 * @param type
	 *            The new value.
	 */
	public void setType(ReferralType type) {
		this.type = type;
	}

	/**
	 * Get the type of application: New, Renewal, Amendment, Replacement, ...
	 * 
	 * @return The type of application: New, Renewal, Amendment, Replacement, ...
	 */
	public ReferralApplicationType getApplicationType() {
		return applicationType;
	}

	/**
	 * Set the type of application: New, Renewal, Amendment, Replacement, ...
	 * 
	 * @param applicationType
	 *            The new value.
	 */
	public void setApplicationType(ReferralApplicationType applicationType) {
		this.applicationType = applicationType;
	}

	/**
	 * If the application type is a renewal, amendment or replacement of an existing referral, this is that target
	 * referral. Otherwise this object is null.
	 * 
	 * @return The target referral, is there is one.
	 */
	public Referral getTargetReferral() {
		return targetReferral;
	}

	/**
	 * If the application type is a renewal, amendment or replacement of an existing referral, this is that target
	 * referral. Otherwise this object is null.
	 * 
	 * @param targetReferral
	 *            Set the target referral if there is one.
	 */
	public void setTargetReferral(Referral targetReferral) {
		this.targetReferral = targetReferral;
	}

	/**
	 * Get the access type. Default as non-confidential. Are all projects deemed to be open to staff at Lands and
	 * Community if they have credentials?
	 * 
	 * @return Access type. Default as non-confidential. Are all projects deemed to be open to staff at Lands and
	 *         Community if they have credentials?
	 */
	public boolean isConfidential() {
		return confidential;
	}

	/**
	 * Set the access type. Default as non-confidential. Are all projects deemed to be open to staff at Lands and
	 * Community if they have credentials?
	 * 
	 * @param confidential
	 *            The new value.
	 */
	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}

	/**
	 * Get the date when initial referral package was received.
	 * 
	 * @return Date when initial referral package was received.
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}

	/**
	 * Set the date when initial referral package was received.
	 * 
	 * @param receiveDate
	 *            The new value.
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	
	public Date getCreateDate() {
		return createDate;
	}

	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	public String getCreateUser() {
		return createUser;
	}

	
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * Get the date when the official response is sent to the contact.
	 * 
	 * @return The date when the official response is sent to the contact.
	 */
	public Date getReponseDate() {
		return reponseDate;
	}

	/**
	 * Set the date when the official response is sent to the contact.
	 * 
	 * @param reponseDate
	 *            The date when the response is given.
	 */
	public void setReponseDate(Date reponseDate) {
		this.reponseDate = reponseDate;
	}

	/**
	 * Get the deadline date for response.
	 * 
	 * @return Deadline date for response.
	 */
	public Date getReponseDeadline() {
		return reponseDeadline;
	}

	/**
	 * Set the deadline date for response.
	 * 
	 * @param reponseDeadline
	 *            The new deadline for the response.
	 */
	public void setReponseDeadline(Date reponseDeadline) {
		this.reponseDeadline = reponseDeadline;
	}

	/**
	 * Get the active retention of record - in Years (2Y).
	 * 
	 * @return Active retention of record - in Years (2Y).
	 */
	public int getActiveRetentionPeriod() {
		return activeRetentionPeriod;
	}

	/**
	 * Set the active retention of record - in Years (2Y).
	 * 
	 * @param activeRetentionPeriod
	 *            The new value.
	 */
	public void setActiveRetentionPeriod(int activeRetentionPeriod) {
		this.activeRetentionPeriod = activeRetentionPeriod;
	}

	/**
	 * Get the semi-active retention of record - in Years (5Y).
	 * 
	 * @return Semi-active retention of record - in Years (5Y).
	 */
	public int getSemiActiveRetentionPeriod() {
		return semiActiveRetentionPeriod;
	}

	/**
	 * Set the semi-active retention of record - in Years (5Y).
	 * 
	 * @param semiActiveRetentionPeriod
	 *            The new value.
	 */
	public void setSemiActiveRetentionPeriod(int semiActiveRetentionPeriod) {
		this.semiActiveRetentionPeriod = semiActiveRetentionPeriod;
	}

	/**
	 * Get the final disposition: Destruction (D), Permanent Retention - Archives (P), Selective Retention - Archives
	 * (SR).
	 * 
	 * @return Destruction (D), Permanent Retention - Archives (P), Selective Retention - Archives (SR).
	 */
	public ReferralDispositionType getFinalDisposition() {
		return finalDisposition;
	}

	/**
	 * Set the final disposition: Destruction (D), Permanent Retention - Archives (P), Selective Retention - Archives
	 * (SR).
	 * 
	 * @param finalDisposition
	 *            The new value.
	 */
	public void setFinalDisposition(ReferralDispositionType finalDisposition) {
		this.finalDisposition = finalDisposition;
	}

	/**
	 * Get the provincial Agency's assessment or the appropriate engagement level.
	 * 
	 * @return Provincial Agency's assessment or the appropriate engagement level.
	 */
	public int getProvincialAssessmentLevel() {
		return provincialAssessmentLevel;
	}

	/**
	 * Set the provincial Agency's assessment or the appropriate engagement level.
	 * 
	 * @param provincialAssessmentLevel
	 *            The new value.
	 */
	public void setProvincialAssessmentLevel(int provincialAssessmentLevel) {
		this.provincialAssessmentLevel = provincialAssessmentLevel;
	}

	/**
	 * Get the final assessment or the appropriate engagement level.
	 * 
	 * @return final assessment or the appropriate engagement level.
	 */
	public int getFinalAssessmentLevel() {
		return finalAssessmentLevel;
	}

	/**
	 * Set the final assessment or the appropriate engagement level.
	 * 
	 * @param finalAssessmentLevel
	 *            The new value.
	 */
	public void setFinalAssessmentLevel(int finalAssessmentLevel) {
		this.finalAssessmentLevel = finalAssessmentLevel;
	}
	
	/**
	 * Splits the referral id to store the first part in the database
	 */
//	private void splitReferralId() {
//		if (landReferralId != null && landReferralId.length() == 15) {
//			setPrimaryClassificationNumber(Integer.parseInt(landReferralId.substring(0, 4)));
//			setSecondaryClassificationNumber(Integer.parseInt(landReferralId.substring(5, 7)));
//			setCalendarYear(Integer.parseInt(landReferralId.substring(8, 10)));
//		}
//	}
//
//	/**
//	 * Joins the first part of the referral id with the unique number 
//	 */
//	private void joinReferralId() {
//		if(getNumber() > 0) {
//			StringBuilder builder = new StringBuilder();
//			builder.append(String.format("%04d", getPrimaryClassificationNumber()));
//			builder.append("-");
//			builder.append(String.format("%02d", getSecondaryClassificationNumber()));
//			builder.append("/");
//			builder.append(String.format("%02d", getCalendarYear()));
//			builder.append("-");
//			builder.append(String.format("%04d", getNumber()));
//	    	setLandReferralId(builder.toString());
//		}
//    }

}