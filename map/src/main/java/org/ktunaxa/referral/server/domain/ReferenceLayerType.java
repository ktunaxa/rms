/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * <li>Value Aspect - Terrestrial</li>
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
	 *            The new value of description (administrative, TRIM, terrestrial, cultural, ...).
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the name of the category for this type (administrative, TRIM, terrestrial, cultural, ...).
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