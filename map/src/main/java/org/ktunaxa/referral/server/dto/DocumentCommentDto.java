/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.dto;


/**
 * A comment that is associated with a certain document.
 * 
 * @author Pieter De Graef
 */
public class DocumentCommentDto extends CommentDto {

	private static final long serialVersionUID = 100L;

	/** The document to which this comment is associated. */
	private DocumentDto document;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public DocumentCommentDto() {
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
	public void setDocument(DocumentDto document) {
		this.document = document;
	}

	/**
	 * Get the document to which this comment is associated.
	 * 
	 * @return The document to which this comment is associated.
	 */
	public DocumentDto getDocument() {
		return document;
	}
}