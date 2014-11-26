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

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Full text search object (filled with data from all layers).
 * 
 * @author Jan De Moerloose
 *
 */
@Entity
@Table(name = "fts_layers")
public class FtsLayers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** name of layer. */
	@Column(name = "layer_name")
	private String layerName;

	/** feature id. */
	@Column(name = "fid")
	private String fid;

	/** Text value that identifies this object and can be used for labeling purposes. */
	@Column(name = "description")
	private String label;

	/** DUMMY !!!! */
	@Formula("description")
	private String tsv;

	/** The actual geometry of this reference feature on the map. */
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(nullable = false, name = "geom")
	private Geometry geometry;

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The aspect's unique identifier.
	 * 
	 * @param id The new value for the identifier.
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
	 * @param geometry The new geometry.
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTsv() {
		return tsv;
	}

	public void setTsv(String tsv) {
		this.tsv = tsv;
	}

}