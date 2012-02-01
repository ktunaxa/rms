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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A key-value pair that stores an alpha-numerical attributes for a specific base reference feature.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "reference_base_attribute")
public class ReferenceBaseAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The associated reference feature. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reference_base_id", nullable = false)
	private ReferenceBase reference;

	/** The attribute's key. */
	@Column(nullable = false, name = "the_key")
	private String key;

	/** The attribute's textual value. */
	@Column(name = "the_value")
	private String value;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceBaseAttribute() {
	};

	public ReferenceBaseAttribute(long id) {
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
	public ReferenceBase getReference() {
		return reference;
	}

	/**
	 * Set the associated reference feature.
	 * 
	 * @param reference
	 *            The new reference feature.
	 */
	public void setReference(ReferenceBase reference) {
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