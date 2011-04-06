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
public class DocumentComment extends AbstractComment {

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