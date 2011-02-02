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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Association object that associates an aspect to a reference feature within the reference layer. This is needed
 * because the different data types in the reference layers can be associated with multiple aspects.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "reference_aspect")
public class ReferenceAspect {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The specific aspect to associate to the reference feature. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aspect_id", nullable = false)
	private Aspect aspect;

	/** The reference feature object. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reference_id", nullable = false)
	private Reference reference;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceAspect() {
	};

	public ReferenceAspect(long id) {
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
	public Aspect getAspect() {
		return aspect;
	}

	/**
	 * Set the specific aspect to associate to the reference feature.
	 * 
	 * @param aspect
	 *            The new aspect value.
	 */
	public void setAspect(Aspect aspect) {
		this.aspect = aspect;
	}

	/**
	 * Get the reference feature object.
	 * 
	 * @return The reference feature object.
	 */
	public Reference getReference() {
		return reference;
	}

	/**
	 * Set the reference feature object.
	 * 
	 * @param reference
	 *            The new reference feature.
	 */
	public void setReference(Reference reference) {
		this.reference = reference;
	}
}