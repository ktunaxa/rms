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
