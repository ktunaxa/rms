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