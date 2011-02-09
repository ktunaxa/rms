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

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;

/**
 * Association object that associates an aspect to a reference feature within the reference layer. This is needed
 * because the different data types in the reference layers can be associated with multiple aspects.
 * 
 * @author Pieter De Graef
 */
public class ReferenceValueAspectDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** The specific aspect to associate to the reference feature. */
	private AspectDto aspect;

	/** The reference feature object. */
	private ReferenceValueDto reference;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceValueAspectDto() {
	};

	public ReferenceValueAspectDto(long id) {
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
	 * Get the specific aspect to associate to the reference feature.
	 * 
	 * @return The specific aspect to associate to the reference feature.
	 */
	public AspectDto getAspect() {
		return aspect;
	}

	/**
	 * Set the specific aspect to associate to the reference feature.
	 * 
	 * @param aspect
	 *            The new aspect value.
	 */
	public void setAspect(AspectDto aspect) {
		this.aspect = aspect;
	}

	/**
	 * Get the reference feature object.
	 * 
	 * @return The reference feature object.
	 */
	public ReferenceValueDto getReference() {
		return reference;
	}

	/**
	 * Set the reference feature object.
	 * 
	 * @param reference
	 *            The new reference feature.
	 */
	public void setReference(ReferenceValueDto reference) {
		this.reference = reference;
	}
}