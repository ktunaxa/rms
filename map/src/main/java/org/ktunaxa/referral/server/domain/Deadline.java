package org.ktunaxa.referral.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Deadlines (start, stop, ...) that can be associated with a certain referral.
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "deadline")
public class Deadline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** The date associated with the deadline. */
	@Column(nullable = false, name = "deadline_date")
	private Date date;

	/** The textual description for this deadline. */
	@Column(nullable = false, name = "description")
	private String description;

	/** The referral that this deadline is associated with. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referral_id", nullable = false)
	private Referral referral;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public Deadline() {
	};

	public Deadline(long id) {
		this.id = id;
	}

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
	 * @return Returns the date associated with the deadline.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the date associated with the deadline.
	 * 
	 * @param date
	 *            The new date.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Returns the textual description for this deadline.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the textual description for this deadline.
	 * 
	 * @param description
	 *            The new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The referral that this deadline is associated with.
	 */
	public Referral getReferral() {
		return referral;
	}

	/**
	 * Set the referral that this deadline is associated with.
	 * 
	 * @param referral
	 *            The new referral object.
	 */
	public void setReferral(Referral referral) {
		this.referral = referral;
	};
}