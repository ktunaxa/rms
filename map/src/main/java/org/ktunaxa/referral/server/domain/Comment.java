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

package org.ktunaxa.referral.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Base definition of a general comment. Extensions of this class will associate such comments with documents or
 * referrals.
 * 
 * @author Pieter De Graef
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

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

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public Comment() {
	};

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
	};
}