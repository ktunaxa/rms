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
 * A key-value pair that stores an alpha-numerical attributes for a specific reference feature.
 * 
 * @author Pieter De Graef
 */
public class ReferenceValueAttributeDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** The associated reference feature. */
	private ReferenceValueDto reference;

	/** The attribute's key. */
	private String key;

	/** The attribute's textual value. */
	private String value;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceValueAttributeDto() {
	};

	public ReferenceValueAttributeDto(long id) {
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
	 * Get the associated reference feature.
	 * 
	 * @return The associated reference feature.
	 */
	public ReferenceValueDto getReference() {
		return reference;
	}

	/**
	 * Set the associated reference feature.
	 * 
	 * @param reference
	 *            The new reference feature.
	 */
	public void setReference(ReferenceValueDto reference) {
		this.reference = reference;
	}

	/**
	 * Get the attribute's key.
	 * 
	 * @return The attribute's key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set the attribute's key.
	 * 
	 * @param key
	 *            The new key.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Get the attribute's textual value.
	 * 
	 * @return Get the attribute's textual value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the attribute's textual value.
	 * 
	 * @param value
	 *            The new value.
	 */
	public void setValue(String value) {
		this.value = value;
	}
}