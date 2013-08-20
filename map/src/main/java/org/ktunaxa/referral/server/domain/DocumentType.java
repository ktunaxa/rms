/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ktunaxa.referral.server.service.KtunaxaConstant;

/**
 * Definition of type of documents that can be attached to a referral.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "document_type")
public class DocumentType {

	public static final DocumentType DEFAULT;

	public static final DocumentType KLRA_RESPONSE;
	
	static {
		DEFAULT = new DocumentType(KtunaxaConstant.DOCUMENT_TYPE_DEFAULT_ID,
				"External - Initial Referral Notification", "External document describing the referral notification.");
		KLRA_RESPONSE = new DocumentType(KtunaxaConstant.DOCUMENT_TYPE_KLRA_RESPONSE_ID, "KLRA Response",
				"The official response to the referral by the KLRA.");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** What type of document being stored in system - Internal response, external response, etc... */
	@Column(nullable = false, name = "title")
	private String title;

	/** Extra clarification for this type of document, just in case the title alone is not clear enough. */
	@Column(nullable = false, name = "description")
	private String description;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public DocumentType() {
	};

	public DocumentType(long id) {
		this.id = id;
	};

	public DocumentType(long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	};
	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The type's unique identifier.
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
	 * Get the type of document being stored in system - Internal response, external response, etc...
	 * 
	 * @return What type of document being stored in system - Internal response, external response, etc...
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the type of document being stored in system - Internal response, external response, etc...
	 * 
	 * @param title
	 *            The new document type.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set extra clarification for this type of document, just in case the title alone is not clear enough.
	 * 
	 * @param description
	 *            The new value of description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Ask extra clarification for this type of document, just in case the title alone is not clear enough.
	 * 
	 * @return Extra clarification for this type of document, just in case the title alone is not clear enough.
	 */
	public String getDescription() {
		return description;
	}
}