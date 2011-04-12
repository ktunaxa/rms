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
 * Definition of an application type for referrals. Possibilities are:
 * <ul>
 * <li>New</li>
 * <li>Renewal</li>
 * <li>Amendment</li>
 * <li>Replacement</li>
 * </ul>
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "referral_application_type")
public class ReferralApplicationType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** Description for the type of activity occurring in the land: forestry, mining, etc... */
	@Column(nullable = false, name = "title")
	private String title;

	/** Extra clarification for this type of document, just in case the title alone is not clear enough. */
	@Column(nullable = false, name = "description")
	private String description;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralApplicationType() {
	};

	public ReferralApplicationType(long id) {
		this.id = id;
	};

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * The application type's unique identifier.
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
	 * Get the description for the type of activity occurring in the land: forestry, mining, etc...
	 * 
	 * @return Description for the type of activity occurring in the land: forestry, mining, etc...
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the description for the type of activity occurring in the land: forestry, mining, etc...
	 * 
	 * @param title
	 *            The new document type.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set extra clarification for this type of document, just in case the title alone is not clear enough.
	 * 
	 * @param description
	 *            The new value of description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Ask extra clarification for this type of document, just in case the title alone is not clear enough.
	 * 
	 * @return Extra clarification for this type of document, just in case the title alone is not clear enough.
	 */
	public String getDescription() {
		return description;
	}
}