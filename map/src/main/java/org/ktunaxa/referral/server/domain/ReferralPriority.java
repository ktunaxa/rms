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

/**
 * Definition of an priorities for referrals. Possibilities are:
 * <ul>
 * <li>High</li>
 * <li>Medium</li>
 * <li>Low</li>
 * </ul>
 * 
 * @author Joachim Van der Auwera
 */
@Entity
@Table(name = "referral_priority")
public class ReferralPriority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** Title for the priority. */
	@Column(nullable = false, name = "title")
	private String title;

	/** Description for the priority. Extra clarification in case the title is not clear enough. */
	@Column(nullable = false, name = "description")
	private String description;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public ReferralPriority() {
	}

	public ReferralPriority(long id) {
		this.id = id;
	}

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	/**
	 * Unique identifier for the priority.
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
	 * Get the title of the priority.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the priority.
	 *
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set the description for the priority.
	 * <p/>
	 * Extra clarification for this priority, in case the title alone is not clear enough.
	 *
	 * @param description description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set the description for the priority.
	 * <p/>
	 * Extra clarification for this priority, in case the title alone is not clear enough.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
}