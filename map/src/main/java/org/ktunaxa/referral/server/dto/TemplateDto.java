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

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;

/**
 * A template from which documents can be created. These templates will be used in multiple processes (e-mails, reports,
 * ...)
 * 
 * @author Pieter De Graef
 */
public class TemplateDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** This document's title. */
	private String title;

	/** A short description to further clarify this document. */
	private String description;

	/** The XML contents of the template. */
	private byte[] content;

	/** The mime-type for the actual template. */
	private String mimeType;

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	public TemplateDto() {
	};

	public TemplateDto(long id) {
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
	 * Return a short description to further clarify this document.
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
	 * Get the XML contents of the template.
	 * 
	 * @return The XML contents of the template.
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Set the XML contents of the template.
	 * 
	 * @param content
	 *            The new value.
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * Get the mime-type for the actual template.
	 * 
	 * @return The mime-type for the actual template.
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Set the mime-type for the actual template.
	 * 
	 * @param mimeType
	 *            The new mime-type.
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}