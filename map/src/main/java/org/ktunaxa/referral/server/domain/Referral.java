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
import java.util.Date;

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
 * The central referral object.<br/>
 * TODO automatic sorting of all collections by date?
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "referral")
public class Referral {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** This referral's title. */
	@Column(nullable = false, name = "title")
	private String title;

	/** A short description to further clarify this referral. */
	@Column(nullable = false, name = "description")
	private String description;

	/** The user who created this referral instance. */
	@Column(name = "created_by")
	private String createdBy;

	/** The date at which this referral was created. */
	@Column(nullable = false, name = "creation_date")
	private Date creationDate;

	/** The geometry that represents this referral on the map. */
	@Type(type = "org.hibernatespatial.GeometryUserType")
	@Column(nullable = false, name = "geom")
	private Geometry geometry;

	/** What is the current status of this referral? (new, in progress, approved, denied) */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id", nullable = false)
	private ReferralStatus status;

	/** The full name of the person to contact concerning any and all updates in processing this referral. */
	@Column(nullable = false, name = "contact_name")
	private String contactName;

	/** The email address to mail to concerning any and all updates in processing this referral. */
	@Column(nullable = false, name = "contact_email")
	private String contactEmail;

	/** The full collection of deadlines associated with this referral. */
	@OneToMany(mappedBy = "referral", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Collection<Deadline> deadlines;

	/** The collection of all documents associated with this referral. */
	@OneToMany(mappedBy = "referral", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Collection<Document> documents;

	/** The collection of all comments made on this referral. */
	@OneToMany(mappedBy = "referral", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Collection<ReferralComment> comments;

	// private Collection<ReferralAspect> aspects;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public Referral() {
	};

	public Referral(long id) {
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
	 * @return This document's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set this document's title.
	 * 
	 * @param title
	 *            The new title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return A short description to further clarify this document.
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set a short description to further clarify this document.
	 * 
	 * @param description
	 *            The new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the user who created this referral instance.
	 * 
	 * @return The user who created this referral instance.
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set the user who created this referral instance.
	 * 
	 * @param createdBy
	 *            The new user.
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get the date at which this referral was created.
	 * 
	 * @return The date at which this referral was created.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Set the date at which this referral was created.
	 * 
	 * @param creationDate
	 *            The new value.
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Get the geometry that represents this referral on the map.
	 * 
	 * @return The geometry that represents this referral on the map.
	 */
	public Geometry getGeometry() {
		return geometry;
	}

	/**
	 * Set the geometry that represents this referral on the map.
	 * 
	 * @param geometry
	 *            The new geometry.
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	/**
	 * Get the current status of this referral (new, in progress, approved, denied).
	 * 
	 * @return the current status of this referral? (new, in progress, approved, denied)
	 */
	public ReferralStatus getStatus() {
		return status;
	}

	/**
	 * Set the current status of this referral? (new, in progress, approved, denied).
	 * 
	 * @param status
	 *            The new status.
	 */
	public void setStatus(ReferralStatus status) {
		this.status = status;
	}

	/**
	 * Get the full name of the person to contact concerning any and all updates in processing this referral.
	 * 
	 * @return The full name of the person to contact concerning any and all updates in processing this referral.
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Set the full name of the person to contact concerning any and all updates in processing this referral.
	 * 
	 * @param contactName
	 *            The new contact name.
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Get the email address to mail to concerning any and all updates in processing this referral.
	 * 
	 * @return The email address to mail to concerning any and all updates in processing this referral.
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * Set the email address to mail to concerning any and all updates in processing this referral.
	 * 
	 * @param contactEmail
	 *            The new contact email address.
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * Get the collection of all documents associated with this referral.
	 * 
	 * @return The collection of all documents associated with this referral.
	 */
	public Collection<Document> getDocuments() {
		return documents;
	}

	/**
	 * Set the collection of all documents associated with this referral.
	 * 
	 * @param documents
	 *            The new collection of associated documents.
	 */
	public void setDocuments(Collection<Document> documents) {
		this.documents = documents;
	}

	/**
	 * Get the full collection of deadlines associated with this referral.
	 * 
	 * @return The full collection of deadlines associated with this referral.
	 */
	public Collection<Deadline> getDeadlines() {
		return deadlines;
	}

	/**
	 * Set the full collection of deadlines associated with this referral.
	 * 
	 * @param deadlines
	 *            The new collection of deadlines.
	 */
	public void setDeadlines(Collection<Deadline> deadlines) {
		this.deadlines = deadlines;
	}

	/**
	 * Get the collection of all comments made on this referral.
	 * 
	 * @return The collection of all comments made on this referral.
	 */
	public Collection<ReferralComment> getComments() {
		return comments;
	}

	/**
	 * Set the collection of all comments made on this referral.
	 * 
	 * @param comments
	 *            The new collection of comments.
	 */
	public void setComments(Collection<ReferralComment> comments) {
		this.comments = comments;
	}
}