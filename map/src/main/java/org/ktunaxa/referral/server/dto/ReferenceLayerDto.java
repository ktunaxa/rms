/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;

/**
 * Identification of a layer in the Ktunaxa RMS. Contains meta-data for that layer.
 * 
 * @author Pieter De Graef
 */
public class ReferenceLayerDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** The code of the layer - business key. */
	private long code;

	/** The name of the layer - used in the GUI. */
	private String name;

	/** The type/category that this layer belongs to. Some types are base layers other types represent value aspects. */
	private ReferenceLayerTypeDto type;

	/** The minimum scale at which this layer is still visible. */
	private String scaleMin;

	/** The maximum scale at which this layer is still visible. */
	private String scaleMax;

	/** Should this layer be visible by default or not? */
	private boolean visibleByDefault;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceLayerDto() {
	};

	public ReferenceLayerDto(long id) {
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
	 * The layer's unique code.
	 * 
	 * @param id
	 *            The new value for the code.
	 */
	public void setCode(long code) {
		this.code = code;
	}

	/**
	 * Get the value of the code.
	 * 
	 * @return the value of the code.
	 */
	public long getCode() {
		return code;
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
	public ReferenceLayerTypeDto getType() {
		return type;
	}

	/**
	 * Set the type/category that this layer belongs to. Some types are base layers other types represent value aspects.
	 * 
	 * @param type
	 *            The new layer type.
	 */
	public void setType(ReferenceLayerTypeDto type) {
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