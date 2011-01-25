package org.ktunaxa.referral.pojo;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class ReferenceKeyValue {

	private long id;

	private Reference reference;

	private String key;

	private String value;

	public ReferenceKeyValue() {
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

	/**
	 * Set the value of key
	 * 
	 * @param newVar
	 *            the new value of key
	 */
	public void setKey(String newVar) {
		key = newVar;
	}

	/**
	 * Get the value of key
	 * 
	 * @return the value of key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set the value of value
	 * 
	 * @param newVar
	 *            the new value of value
	 */
	public void setValue(String newVar) {
		value = newVar;
	}

	/**
	 * Get the value of value
	 * 
	 * @return the value of value
	 */
	public String getValue() {
		return value;
	}
}