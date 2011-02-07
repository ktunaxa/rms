/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.server.domain;

import java.util.Collection;
import java.util.Date;

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

	/** Lands referral unique number (11-0001, YY-0000, resets after each new year). */
	@Column(name = "land_referral_id")
	private String landReferralId;

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
	@Column(name = "external_project_id")
	private String externalProjectId;

	/** Document file id which tracks project/activity in provincial agency. */
	@Column(name = "external_file_id")
	private String externalFileId;

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
	 * Access type. Default as non-confidential. Are all projects deemed to be open to staff at Lands and Community if
	 * they have credentials?
	 */
	@Column(nullable = false, name = "confidential")
	private boolean confidential;

	// Deadline information:

	/** Date when initial referral package was received. */
	@Column(nullable = false, name = "receive_date")
	private Date receiveDate;

	/** Deadline date for response. */
	@Column(nullable = false, name = "response_date")
	private Date reponseDate;

	/** Active retention of record - in Years (2Y). */
	@Column(nullable = false, name = "active_retention_period")
	private int activeRetentionPeriod;

	/** Semi-active retention of record - in Years (5Y). */
	@Column(nullable = false, name = "semi_active_retention_period")
	private int semiActiveRetentionPeriod;

	/** Destruction (D), Permanent Retention - Archives (P), Selective Retention - Archives (SR). */
	@Column(nullable = false, name = "final_disposition")
	private int finalDisposition;

	// Other information:

	/** Provincial Agency's assessment or the appropriate engagement level. */
	@Column(name = "assessment_level")
	private int assessmentLevel;

	/** Using developed record classification annotation - 3500-10. */
	@Column(nullable = false, name = "record_classification")
	private String recordClassification;

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
	private Collection<Document> documents;

	/** The collection of all comments made on this referral. */
	@OneToMany(mappedBy = "referral", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Collection<ReferralComment> comments;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public Referral() {
	};

	public Referral(long id) {
		this.id = id;
	};

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
	 * Get the collection of all documents associated with this referral.
	 * 
	 * @return The collection of all documents associated with this referral.
	 */
	public Collection<Document> getDocuments() {
		return documents;
	}

	/**
	 * Set the collection of all documents associated with this referral.
	 * 
	 * @param documents
	 *            The new collection of associated documents.
	 */
	public void setDocuments(Collection<Document> documents) {
		this.documents = documents;
	}

	/**
	 * Get the collection of all comments made on this referral.
	 * 
	 * @return The collection of all comments made on this referral.
	 */
	public Collection<ReferralComment> getComments() {
		return comments;
	}

	/**
	 * Set the collection of all comments made on this referral.
	 * 
	 * @param comments
	 *            The new collection of comments.
	 */
	public void setComments(Collection<ReferralComment> comments) {
		this.comments = comments;
	}

	/**
	 * Get lands referral unique number (11-0001, YY-0000, resets after each new year).
	 * 
	 * @return Lands referral unique number (11-0001, YY-0000, resets after each new year).
	 */
	public String getLandReferralId() {
		return landReferralId;
	}

	/**
	 * Set the lands referral unique number (11-0001, YY-0000, resets after each new year).
	 * 
	 * @param landReferralId
	 *            The new value.
	 */
	public void setLandReferralId(String landReferralId) {
		this.landReferralId = landReferralId;
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

	/**
	 * Get the deadline date for response.
	 * 
	 * @return Deadline date for response.
	 */
	public Date getReponseDate() {
		return reponseDate;
	}

	/**
	 * Set the deadline date for response.
	 * 
	 * @param reponseDate
	 *            The new deadline.
	 */
	public void setReponseDate(Date reponseDate) {
		this.reponseDate = reponseDate;
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
	public int getFinalDisposition() {
		return finalDisposition;
	}

	/**
	 * Set the final disposition: Destruction (D), Permanent Retention - Archives (P), Selective Retention - Archives
	 * (SR).
	 * 
	 * @param finalDisposition
	 *            The new value.
	 */
	public void setFinalDisposition(int finalDisposition) {
		this.finalDisposition = finalDisposition;
	}

	/**
	 * Get the provincial Agency's assessment or the appropriate engagement level.
	 * 
	 * @return Provincial Agency's assessment or the appropriate engagement level.
	 */
	public int getAssessmentLevel() {
		return assessmentLevel;
	}

	/**
	 * Set the provincial Agency's assessment or the appropriate engagement level.
	 * 
	 * @param assessmentLevel
	 *            The new value.
	 */
	public void setAssessmentLevel(int assessmentLevel) {
		this.assessmentLevel = assessmentLevel;
	}

	/**
	 * Get the record classification annotation.
	 * 
	 * @return Using developed record classification annotation - 3500-10.
	 */
	public String getRecordClassification() {
		return recordClassification;
	}

	/**
	 * Set the record classification annotation.
	 * 
	 * @param recordClassification
	 *            The new value.
	 */
	public void setRecordClassification(String recordClassification) {
		this.recordClassification = recordClassification;
	}
}