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
 * Identification of a layer in the Ktunaxa RMS. Contains meta-data for that layer.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "reference_layer")
public class ReferenceLayer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The name of the layer - used in the GUI. */
	@Column(nullable = false, name = "name")
	private String name;

	/** The type/category that this layer belongs to. Some types are base layers other types represent value aspects. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id", nullable = false)
	private ReferenceLayerType type;

	/** The minimum scale at which this layer is still visible. */
	@Column(nullable = false, name = "scale_min")
	private String scaleMin;

	/** The maximum scale at which this layer is still visible. */
	@Column(nullable = false, name = "scale_max")
	private String scaleMax;

	/** Should this layer be visible by default or not? */
	@Column(nullable = false, name = "visible_by_default")
	private boolean visibleByDefault;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceLayer() {
	};

	public ReferenceLayer(long id) {
		this.id = id;
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The layer's unique identifier.
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
	 * Get the name of the layer - used in the GUI.
	 * 
	 * @return The name of the layer - used in the GUI.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the layer - used in the GUI.
	 * 
	 * @param name
	 *            The new layer name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the type/category that this layer belongs to. Some types are base layers other types represent value aspects.
	 * 
	 * @return The type/category that this layer belongs to. Some types are base layers other types represent value
	 *         aspects.
	 */
	public ReferenceLayerType getType() {
		return type;
	}

	/**
	 * Set the type/category that this layer belongs to. Some types are base layers other types represent value aspects.
	 * 
	 * @param type
	 *            The new layer type.
	 */
	public void setType(ReferenceLayerType type) {
		this.type = type;
	}

	/**
	 * Get the minimum scale at which this layer is still visible.
	 * 
	 * @return The minimum scale at which this layer is still visible.
	 */
	public String getScaleMin() {
		return scaleMin;
	}

	/**
	 * Set the minimum scale at which this layer is still visible.
	 * 
	 * @param scaleMin
	 *            The new value.
	 */
	public void setScaleMin(String scaleMin) {
		this.scaleMin = scaleMin;
	}

	/**
	 * Get the maximum scale at which this layer is still visible.
	 * 
	 * @return The maximum scale at which this layer is still visible.
	 */
	public String getScaleMax() {
		return scaleMax;
	}

	/**
	 * Set the maximum scale at which this layer is still visible.
	 * 
	 * @param scaleMax
	 *            The new value.
	 */
	public void setScaleMax(String scaleMax) {
		this.scaleMax = scaleMax;
	}

	/**
	 * Get whether or not this layer should be visible by default or not?
	 * 
	 * @return Should this layer be visible by default or not?
	 */
	public boolean isVisibleByDefault() {
		return visibleByDefault;
	}

	/**
	 * Determine whether or not this layer should be visible by default.
	 * 
	 * @param visibleByDefault
	 *            True or false.
	 */
	public void setVisibleByDefault(boolean visibleByDefault) {
		this.visibleByDefault = visibleByDefault;
	};
}