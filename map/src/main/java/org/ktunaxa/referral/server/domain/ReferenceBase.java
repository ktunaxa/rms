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

package org.ktunaxa.referral.server.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

/**
 * A feature of the reference layer with it's specific collection of key-value pairs and aspects.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "reference_base")
public class ReferenceBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The layer that this object belongs to. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "layer_id", nullable = false)
	private ReferenceLayer layer;

	/** Specific code that can be used for style differentiation. */
	@Column(name = "style_code")
	private String styleCode;

	/** Text value that identifies this object and can be used for labeling purposes. */
	@Column(name = "label")
	private String label;

	/** The actual geometry of this reference feature on the map. */
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(nullable = false, name = "geom")
	private Geometry geometry;

	/** The collection of key-value pairs that make up this reference feature. */
	@OneToMany(mappedBy = "reference", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Collection<ReferenceBaseAttribute> attributes;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferenceBase() {
	};

	public ReferenceBase(long id) {
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
	public Collection<ReferenceBaseAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * Set the collection of key-value pairs that make up this reference feature.
	 * 
	 * @param attributes
	 *            The new collection of key-value pairs.
	 */
	public void setAttributes(Collection<ReferenceBaseAttribute> attributes) {
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
	public ReferenceLayer getLayer() {
		return layer;
	}

	/**
	 * Set the layer that this object belongs to.
	 * 
	 * @param layer
	 *            The layer for this object.
	 */
	public void setLayer(ReferenceLayer layer) {
		this.layer = layer;
	}
}