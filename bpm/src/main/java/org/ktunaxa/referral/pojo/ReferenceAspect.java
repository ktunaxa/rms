package org.ktunaxa.referral.pojo;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class ReferenceAspect {

	private long id;

	private Aspect aspect;

	private Reference reference;

	public ReferenceAspect() {
	};

	/**
	 * Set the value of id
	 * 
	 * @param newVar
	 *            the new value of id
	 */
	public void setId(long newVar) {
		id = newVar;
	}

	/**
	 * Get the value of id
	 * 
	 * @return the value of id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set the value of aspect
	 * 
	 * @param newVar
	 *            the new value of aspect
	 */
	public void setAspect(Aspect newVar) {
		aspect = newVar;
	}

	/**
	 * Get the value of aspect
	 * 
	 * @return the value of aspect
	 */
	public Aspect getAspect() {
		return aspect;
	}

	/**
	 * Set the value of reference
	 * 
	 * @param newVar
	 *            the new value of reference
	 */
	public void setReference(Reference newVar) {
		reference = newVar;
	}

	/**
	 * Get the value of reference
	 * 
	 * @return the value of reference
	 */
	public Reference getReference() {
		return reference;
	}
}