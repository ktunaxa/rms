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

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * A document that can be associated with a certain referral. This document has some meta-data to further clarify it's
 * contents, and can possibly be included in the final report.
 * 
 * @author Pieter De Graef
 */
public class DocumentDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** This document's title. */
	private String title;

	/** A short description to further clarify this document. */
	private String description;

//	/** Content keywords as a comma separated list. */
//	private String keywords;

	/** What type of document being stored in system - Internal response, external response, etc... */
	private DocumentTypeDto type;

	/** The unique identifier for the document within the CMS. */
	private String documentId;

	/** The date at which this document was added to the associated referral. */
	private Date additionDate;

	/** The user who added this document to the associated referral. */
	private String addedBy;

	/** Is this document visible only to KLRA staff? */
	private boolean confidential;

	/** Should this document be added to the final report? */
	private boolean includeInReport;

	/** The associated referral. */
	private ReferralDto referral;

	/** The collection of all comments made on this document. */
	private Collection<DocumentCommentDto> comments;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public DocumentDto() {
	};

	public DocumentDto(long id) {
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
	 * Get this document's title.
	 * 
	 * @return This document's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set this document's title.
	 * 
	 * @param title
	 *            The new title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return A short description to further clarify this document.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set a short description to further clarify this document.
	 * 
	 * @param description
	 *            The new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the unique identifier for the document within the CMS.
	 * 
	 * @return The document identifier.
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * Set the unique identifier for the document within the CMS.
	 * 
	 * @param documentId
	 *            The new value.
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/**
	 * Return the date at which this document was added to the associated referral.
	 * 
	 * @return The addition-date.
	 */
	public Date getAdditionDate() {
		return additionDate;
	}

	/**
	 * Set the date at which this document was added to the associated referral.
	 * 
	 * @param additionDate
	 *            The new date.
	 */
	public void setAdditionDate(Date additionDate) {
		this.additionDate = additionDate;
	}

	/**
	 * Return the user who added this document to the associated referral.
	 * 
	 * @return The user who added this document to the associated referral.
	 */
	public String getAddedBy() {
		return addedBy;
	}

	/**
	 * Set the user who added this document to the associated referral.
	 * 
	 * @param addedBy
	 *            The new value.
	 */
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	/**
	 * Return of this document is visible only to KLRA staff.
	 * 
	 * @return Is this document visible only to KLRA staff?
	 */
	public boolean isConfidential() {
		return confidential;
	}

	/**
	 * Determine whether or not this document is visible only to KLRA staff.
	 * 
	 * @param confidential
	 *            True or false.
	 */
	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}

	/**
	 * Return if this document should be added to the final report.
	 * 
	 * @return Should this document be added to the final report?
	 */
	public boolean isIncludeInReport() {
		return includeInReport;
	}

	/**
	 * Determine whether or not this document should be added to the final report.
	 * 
	 * @param includeInReport
	 *            True or false.
	 */
	public void setIncludeInReport(boolean includeInReport) {
		this.includeInReport = includeInReport;
	}

	/**
	 * The associated referral.
	 * 
	 * @return The associated referral.
	 */
	public ReferralDto getReferral() {
		return referral;
	}

	/**
	 * Set the associated referral.
	 * 
	 * @param referral
	 *            The associated referral.
	 */
	public void setReferral(ReferralDto referral) {
		this.referral = referral;
	}

	/**
	 * Get the collection of all comments made on this document.
	 * 
	 * @return The collection of all comments made on this document.
	 */
	public Collection<DocumentCommentDto> getComments() {
		return comments;
	}

	/**
	 * Set the collection of all comments made on this document.
	 * 
	 * @param comments
	 *            The new collection of comments.
	 */
	public void setComments(Collection<DocumentCommentDto> comments) {
		this.comments = comments;
	}

//	/**
//	 * Get content keywords as a comma separated list.
//	 * 
//	 * @return Content keywords as a comma separated list.
//	 */
//	public String getKeywords() {
//		return keywords;
//	}
//
//	/**
//	 * Set the content keywords as a comma separated list.
//	 * 
//	 * @param keywords
//	 *            The new keywords.
//	 */
//	public void setKeywords(String keywords) {
//		this.keywords = keywords;
//	}

	/**
	 * Get the type of document being stored in system - Internal response, external response, etc...
	 * 
	 * @return What type of document being stored in system - Internal response, external response, etc...
	 */
	public DocumentTypeDto getType() {
		return type;
	}

	/**
	 * Set the type of document being stored in system - Internal response, external response, etc...
	 * 
	 * @param type
	 *            The new document type.
	 */
	public void setType(DocumentTypeDto type) {
		this.type = type;
	}
}