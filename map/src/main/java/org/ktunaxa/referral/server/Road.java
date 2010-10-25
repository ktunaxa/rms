package org.ktunaxa.referral.server;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;


/**
 * Roads object for hibernate layer model.
 * 
 * @author Jan De Moerloose
 */
@Entity
@Table(name = "roads")
public class Road {

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "gid")
	private Long gid;
	
	private Long id;

	private String type;
	
	private Float length;
	
	private Long use;
	
	private Short wid;
	
	private Date date;
	
	private String url;

	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(name = "the_geom")
	private Geometry geometry;
	
	public Long getGid() {
		return gid;
	}
	
	public void setGid(Long gid) {
		this.gid = gid;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public Float getLength() {
		return length;
	}

	
	public void setLength(Float length) {
		this.length = length;
	}

	
	public Long getUse() {
		return use;
	}

	
	public void setUse(Long use) {
		this.use = use;
	}

	
	public Short getWid() {
		return wid;
	}

	
	public void setWid(Short wid) {
		this.wid = wid;
	}

	
	public Date getDate() {
		return date;
	}

	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}

	
	public Geometry getGeometry() {
		return geometry;
	}

	
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
}
