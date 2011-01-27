package org.ktunaxa.referral.server.domain;

/**
 * A comment that is associated with a certain document.
 * 
 * @author Pieter De Graef
 */
public class DocumentComment extends Comment {

	/** The document to which this comment is associated. */
	private Document document;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public DocumentComment() {
	};

	public DocumentComment(long id) {
		super(id);
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