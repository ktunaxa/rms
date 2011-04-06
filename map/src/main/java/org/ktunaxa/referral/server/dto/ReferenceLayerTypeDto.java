/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;

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
public class ReferenceLayerTypeDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	private String description;
	
	private boolean baseLayer;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceLayerTypeDto() {
	};

	public ReferenceLayerTypeDto(long id) {
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
	 * Set the value of description.
	 * 
	 * @param description
	 *            The new value of description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the value of description.
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