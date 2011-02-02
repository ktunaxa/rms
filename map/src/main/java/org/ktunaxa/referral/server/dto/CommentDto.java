package org.ktunaxa.referral.server.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Base definition of a general comment. Extensions of this class will associate such comments with documents or
 * referrals.
 * 
 * @author Pieter De Graef
 */
public class CommentDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** The comment's title. */
	private String title;

	/** The original textual content of the comment. Once saved, this is always left untouched. */
	private String content;

	/** The moment when the comment was created. */
	private Date creationDate;

	/** The user who originally created the comment. */
	private String createdBy;

	/** Should this comment be included in the eventual report? */
	private boolean includeInReport;

	/**
	 * The new content for this comment. If this comment is added to the report than this value is used, not the
	 * original content.
	 */
	private String checkedContent;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public CommentDto() {
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
	public String getCheckedContent() {
		return checkedContent;
	}

	/**
	 * Set the new content for this comment. If this comment is added to the report than this value is used, not the
	 * original content - which is always left untouched.
	 * 
	 * @param checkedContent
	 *            The new value.
	 */
	public void setCheckedContent(String checkedContent) {
		this.checkedContent = checkedContent;
	};
}