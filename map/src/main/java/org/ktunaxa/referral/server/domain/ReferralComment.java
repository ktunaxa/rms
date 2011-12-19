/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A comment that is associated with a certain referral.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "referral_comment")
public class ReferralComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The referral to which this comment is associated. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referral_id", nullable = false)
	private Referral referral;


	/** The comment's title. */
	@Column(nullable = false, name = "title")
	private String title;

	/** The original textual content of the comment. Once saved, this is always left untouched. */
	@Column(nullable = false, name = "content")
	private String content;

	/** The moment when the comment was created. */
	@Column(nullable = false, name = "creation_date")
	private Date creationDate;

	/** The user who originally created the comment. */
	@Column(name = "created_by")
	private String createdBy;

	/** Should this comment be included in the eventual report? */
	@Column(nullable = false, name = "include_in_report")
	private boolean includeInReport;

	/**
	 * The new content for this comment. If this comment is added to the report than this value is used, not the
	 * original content.
	 */
	@Column(name = "report_content")
	private String reportContent;

	/** Used for sorting the comments. */
	@Column(name = "position")
	private int position;


	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The comment's unique identifier.
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

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * Set referral to which this comment is associated.
	 * 
	 * @param referral
	 *            the new value of referral
	 */
	public void setReferral(Referral referral) {
		this.referral = referral;
	}

	/**
	 * Get the referral to which this comment is associated.
	 * 
	 * @return The referral to which this comment is associated.
	 */
	public Referral getReferral() {
		return referral;
	}
	

	/**
	 * @return The comment's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the comment's title.
	 * 
	 * @param title
	 *            the new value.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return The original textual content of the comment. Once saved, this is always left untouched.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set the original textual content of the comment. Once saved, this is always left untouched.
	 * 
	 * @param content
	 *            The new value.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return The moment when the comment was created.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Set the moment when the comment was created.
	 * 
	 * @param creationDate
	 *            The new value.
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return The user who originally created the comment.
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set the user who originally created the comment.
	 * 
	 * @param createdBy
	 *            The new value.
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return Should this comment be included in the eventual report?
	 */
	public boolean isIncludeInReport() {
		return includeInReport;
	}

	/**
	 * Should this comment be included in the eventual report?
	 * 
	 * @param includeInReport
	 *            True if it should be added, false if not.
	 */
	public void setIncludeInReport(boolean includeInReport) {
		this.includeInReport = includeInReport;
	}

	/**
	 * The altered content for this comment. If this comment is added to the report than this value is used, not the
	 * original content - which is always left untouched.
	 * 
	 * @return Return the altered content.
	 */
	public String getReportContent() {
		return reportContent;
	}

	/**
	 * Set the new content for this comment. If this comment is added to the report than this value is used, not the
	 * original content - which is always left untouched.
	 * 
	 * @param reportContent
	 *            The new value.
	 */
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	/**
	 * Get general position of comment for final report. Sorts the comments (on position+creationDate).
	 *
	 * @return position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Set the general position of comment for final report. Sorts the comments (on position+creationDate).
	 *
	 * @param position position
	 */
	public void setPosition(int position) {
		this.position = position;
	}
}