package org.ktunaxa.referral.server.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A comment that is associated with a certain document.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "document_comment")
public class DocumentComment extends Comment {

	/** The document to which this comment is associated. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_id", nullable = false)
	private Document document;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public DocumentComment() {
	};

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * Set document to which this comment is associated.
	 * 
	 * @param document
	 *            the new value of document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * Get the document to which this comment is associated.
	 * 
	 * @return The document to which this comment is associated.
	 */
	public Document getDocument() {
		return document;
	}
}