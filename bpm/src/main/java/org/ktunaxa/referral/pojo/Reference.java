package org.ktunaxa.referral.pojo;

import java.util.Date;

import com.vividsolutions.jts.geom.Geometry;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class Reference {

	private long id;

	private Geometry geometry;

	private Date updated;

	public Reference() {
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
	 * Set the value of geometry
	 * 
	 * @param newVar
	 *            the new value of geometry
	 */
	public void setGeometry(Geometry newVar) {
		geometry = newVar;
	}

	/**
	 * Get the value of geometry
	 * 
	 * @return the value of geometry
	 */
	public Geometry getGeometry() {
		return geometry;
	}

	/**
	 * Set the value of updated
	 * 
	 * @param newVar
	 *            the new value of updated
	 */
	public void setUpdated(Date newVar) {
		updated = newVar;
	}

	/**
	 * Get the value of updated
	 * 
	 * @return the value of updated
	 */
	public Date getUpdated() {
		return updated;
	}
}