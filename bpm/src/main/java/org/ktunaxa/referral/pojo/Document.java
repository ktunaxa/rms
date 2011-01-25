package org.ktunaxa.referral.pojo;

import java.util.Date;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class Document {

	private long id;

	private String title;

	private String description;

	private String documentId;

	private Date additionDate;

	private User addedBy;

	private boolean confidential;

	private boolean includeInReport;

	private Referral referral;

	public Document() {
	};

	/**
	 * Set the value of id
	 * 
	 * @param newVar
	 *            the new value of id
	 */
	public void setId(long newVar) {
		id = newVar;
	}

	/**
	 * Get the value of id
	 * 
	 * @return the value of id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set the value of title
	 * 
	 * @param newVar
	 *            the new value of title
	 */
	public void setTitle(String newVar) {
		title = newVar;
	}

	/**
	 * Get the value of title
	 * 
	 * @return the value of title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the value of description
	 * 
	 * @param newVar
	 *            the new value of description
	 */
	public void setDescription(String newVar) {
		description = newVar;
	}

	/**
	 * Get the value of description
	 * 
	 * @return the value of description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of documentId
	 * 
	 * @param newVar
	 *            the new value of documentId
	 */
	public void setDocumentId(String newVar) {
		documentId = newVar;
	}

	/**
	 * Get the value of documentId
	 * 
	 * @return the value of documentId
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * Set the value of additionDate
	 * 
	 * @param newVar
	 *            the new value of additionDate
	 */
	public void setAdditionDate(Date newVar) {
		additionDate = newVar;
	}

	/**
	 * Get the value of additionDate
	 * 
	 * @return the value of additionDate
	 */
	public Date getAdditionDate() {
		return additionDate;
	}

	/**
	 * Set the value of addedBy
	 * 
	 * @param newVar
	 *            the new value of addedBy
	 */
	public void setAddedBy(User newVar) {
		addedBy = newVar;
	}

	/**
	 * Get the value of addedBy
	 * 
	 * @return the value of addedBy
	 */
	public User getAddedBy() {
		return addedBy;
	}

	/**
	 * Set the value of confidential
	 * 
	 * @param newVar
	 *            the new value of confidential
	 */
	public void setConfidential(boolean newVar) {
		confidential = newVar;
	}

	/**
	 * Get the value of confidential
	 * 
	 * @return the value of confidential
	 */
	public boolean getConfidential() {
		return confidential;
	}

	/**
	 * Set the value of includeInReport
	 * 
	 * @param newVar
	 *            the new value of includeInReport
	 */
	public void setIncludeInReport(boolean newVar) {
		includeInReport = newVar;
	}

	/**
	 * Get the value of includeInReport
	 * 
	 * @return the value of includeInReport
	 */
	public boolean getIncludeInReport() {
		return includeInReport;
	}

	/**
	 * Set the value of referral
	 * 
	 * @param newVar
	 *            the new value of referral
	 */
	public void setReferral(Referral newVar) {
		referral = newVar;
	}

	/**
	 * Get the value of referral
	 * 
	 * @return the value of referral
	 */
	public Referral getReferral() {
		return referral;
	}
}