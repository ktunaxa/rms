package org.ktunaxa.referral.pojo;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class DocumentComment extends Comment {

	private Document document;

	public DocumentComment() {
	};

	/**
	 * Set the value of document
	 * 
	 * @param newVar
	 *            the new value of document
	 */
	public void setDocument(Document newVar) {
		document = newVar;
	}

	/**
	 * Get the value of document
	 * 
	 * @return the value of document
	 */
	public Document getDocument() {
		return document;
	}
}