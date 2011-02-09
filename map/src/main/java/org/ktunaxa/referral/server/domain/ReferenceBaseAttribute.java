/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
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