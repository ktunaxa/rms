/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Definition of a category for base layers. Possible aspects are:
 * <ul>
 * <li>Base - Adminitrative</li>
 * <li>Base - Mining</li>
 * <li>Base - Neatlines</li>
 * <li>Base - NTS</li>
 * <li>Base - TRIM</li>
 * <li>Value Aspect - Aquatic</li>
 * <li>Value Aspect - Archaelogical</li>
 * <li>Value Aspect - Cultural</li>
 * <li>Value Aspect - Ecology</li>
 * <li>Value Aspect - Treaty</li>
 * </ul>
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "reference_layer_type")
public class ReferenceLayerType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, name = "description")
	private String description;

	@Column(nullable = false, name = "base_layer")
	private boolean baseLayer;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceLayerType() {
	};

	public ReferenceLayerType(long id) {
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
	 * Set the name for this category of layers.
	 * 
	 * @param description
	 *            The new value of description (administrative, TRIM, ecology, cultural, ...).
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the name of the category for this type (administrative, TRIM, ecology, cultural, ...).
	 * 
	 * @return The value of description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * See if layers of this type base layers or not?
	 * 
	 * @return Are layers of this type base layers or not?
	 */
	public boolean isBaseLayer() {
		return baseLayer;
	}

	/**
	 * Determine if layers of this type are base layers or not?
	 * 
	 * @param baseLayer
	 *            true or false.
	 */
	public void setBaseLayer(boolean baseLayer) {
		this.baseLayer = baseLayer;
	}
}