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
@Table(name = "reference_value")
public class ReferenceBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The name of the basic data type (roads, wild life, provincial parks, ...). */
	@Column(nullable = false, name = "layer_name")
	private String layerName;

	/** The category of base layers this object belongs to. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id", nullable = false)
	private ReferenceBaseType type;

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
	 * Get the name of the basic data type (roads, wild life, provincial parks, ...).
	 * 
	 * @return The name of the basic data type (roads, wild life, provincial parks, ...).
	 */
	public String getLayerName() {
		return layerName;
	}

	/**
	 * Set the name of the basic data type (roads, wild life, provincial parks, ...).
	 * 
	 * @param layerName
	 *            The new layer type name.
	 */
	public void setLayerName(String layerName) {
		this.layerName = layerName;
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
	 * Get the category of base layers this object belongs to.
	 * 
	 * @return The category of base layers this object belongs to.
	 */
	public ReferenceBaseType getType() {
		return type;
	}

	/**
	 * Set the category of base layers this object belongs to.
	 * 
	 * @param type
	 *            The new category.
	 */
	public void setType(ReferenceBaseType type) {
		this.type = type;
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
}