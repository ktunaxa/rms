/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;
import java.util.Collection;

import org.geomajas.geometry.Geometry;

/**
 * A feature of the reference layer with it's specific collection of key-value pairs and aspects.
 * 
 * @author Pieter De Graef
 */
public class ReferenceBaseDto implements Serializable {

	private static final long serialVersionUID = 100L;

	private long id;

	/** The layer that this object belongs to. */
	private ReferenceLayerDto layer;

	/** Specific code that can be used for style differentiation. */
	private String styleCode;

	/** Text value that identifies this object and can be used for labeling purposes. */
	private String label;

	/** The actual geometry of this reference feature on the map. */
	private Geometry geometry;

	/** The collection of key-value pairs that make up this reference feature. */
	private Collection<ReferenceBaseAttributeDto> attributes;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceBaseDto() {
	};

	public ReferenceBaseDto(long id) {
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
	 * Return the actual geometry of this reference feature on the map.
	 * 
	 * @return The actual geometry of this reference feature on the map.
	 */
	public Geometry getGeometry() {
		return geometry;
	}

	/**
	 * Set the actual geometry of this reference feature on the map.
	 * 
	 * @param geometry
	 *            The new geometry.
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	/**
	 * Get the collection of key-value pairs that make up this reference feature.
	 * 
	 * @return The collection of key-value pairs that make up this reference feature.
	 */
	public Collection<ReferenceBaseAttributeDto> getAttributes() {
		return attributes;
	}

	/**
	 * Set the collection of key-value pairs that make up this reference feature.
	 * 
	 * @param attributes
	 *            The new collection of key-value pairs.
	 */
	public void setAttributes(Collection<ReferenceBaseAttributeDto> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Get the specific code that can be used for style differentiation.
	 * 
	 * @return Specific code that can be used for style differentiation.
	 */
	public String getStyleCode() {
		return styleCode;
	}

	/**
	 * Set a specific code that can be used for style differentiation.
	 * 
	 * @param styleCode
	 *            The new style code.
	 */
	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	/**
	 * Get a text value that identifies this object and can be used for labeling purposes.
	 * 
	 * @return Text value that identifies this object and can be used for labeling purposes.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set a text value that identifies this object and can be used for labeling purposes.
	 * 
	 * @param label
	 *            The new label.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Get the layer that this object belongs to.
	 * 
	 * @return The layer that this object belongs to.
	 */
	public ReferenceLayerDto getLayer() {
		return layer;
	}

	/**
	 * Set the layer that this object belongs to.
	 * 
	 * @param layer
	 *            The layer for this object.
	 */
	public void setLayer(ReferenceLayerDto layer) {
		this.layer = layer;
	}
}