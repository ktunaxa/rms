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
 * Disposition type object for referral. Allowed values:
 * 
 * <ul>
 * <li>Destruction (D)</li>
 * <li>Permanent Retention - Archives (P)</li>
 * <li>Selective Retention - Archives (SR).</li>
 * </ul>
 * 
 * @author Jan De Moerloose
 */
@Entity
@Table(name = "referral_disposition_type")
public class ReferralDispositionType {

	public static final ReferralDispositionType DEFAULT;
	static {
		DEFAULT = new ReferralDispositionType(1, "D", "Destruction");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, name = "code")
	private String code;

	@Column(nullable = false, name = "description")
	private String description;

	public ReferralDispositionType(long id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}

	public ReferralDispositionType() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
