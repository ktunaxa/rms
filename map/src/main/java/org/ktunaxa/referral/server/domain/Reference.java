package org.ktunaxa.referral.server.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "reference")
public class Reference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The name of the basic data type (roads, wild life, provincial parks, ...). */
	@Column(nullable = false, name = "type_name")
	private String typeName;

	/** The actual geometry of this reference feature on the map. */
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(nullable = false, name = "geom")
	private Geometry geometry;

	/** The collection of aspects this reference feature is associated with. */
	@OneToMany(mappedBy = "reference", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private Collection<ReferenceAspect> aspects;

	/** The collection of key-value pairs that make up this reference feature. */
	@OneToMany(mappedBy = "reference", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Collection<ReferenceKeyValue> attributes;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public Reference() {
	};

	public Reference(long id) {
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
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Set the name of the basic data type (roads, wild life, provincial parks, ...).
	 * 
	 * @param typeName
	 *            The new type name.
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * Get the collection of aspects this reference feature is associated with.
	 * 
	 * @return The collection of aspects this reference feature is associated with.
	 */
	public Collection<ReferenceAspect> getAspects() {
		return aspects;
	}

	/**
	 * Set the collection of aspects this reference feature is associated with.
	 * 
	 * @param aspects
	 *            The new collection of aspects.
	 */
	public void setAspects(Collection<ReferenceAspect> aspects) {
		this.aspects = aspects;
	}

	/**
	 * Get the collection of key-value pairs that make up this reference feature.
	 * 
	 * @return The collection of key-value pairs that make up this reference feature.
	 */
	public Collection<ReferenceKeyValue> getAttributes() {
		return attributes;
	}

	/**
	 * Set the collection of key-value pairs that make up this reference feature.
	 * 
	 * @param attributes
	 *            The new collection of key-value pairs.
	 */
	public void setAttributes(Collection<ReferenceKeyValue> attributes) {
		this.attributes = attributes;
	}
}