package org.ktunaxa.referral.server.domain;

/**
 * Association object that associates an aspect to a reference feature within the reference layer. This is needed
 * because the different data types in the reference layers can be associated with multiple aspects.
 * 
 * @author Pieter De Graef
 */
public class ReferenceAspect {

	private long id;

	/** The specific aspect to associate to the reference feature. */
	private Aspect aspect;

	/** The reference feature object. */
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