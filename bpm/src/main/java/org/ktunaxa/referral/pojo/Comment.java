package org.ktunaxa.referral.pojo;

import java.util.Date;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class Comment {

	private long id;

	private String title;

	private String comment;

	private Date creationDate;

	private User createdBy;

	private boolean includeInReport;

	private User checkUser;

	private String checkComment;

	private Date checkDate;

	public Comment() {
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
	 * Set the value of comment
	 * 
	 * @param newVar
	 *            the new value of comment
	 */
	public void setComment(String newVar) {
		comment = newVar;
	}

	/**
	 * Get the value of comment
	 * 
	 * @return the value of comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set the value of creationDate
	 * 
	 * @param newVar
	 *            the new value of creationDate
	 */
	public void setCreationDate(Date newVar) {
		creationDate = newVar;
	}

	/**
	 * Get the value of creationDate
	 * 
	 * @return the value of creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Set the value of createdBy
	 * 
	 * @param newVar
	 *            the new value of createdBy
	 */
	public void setCreatedBy(User newVar) {
		createdBy = newVar;
	}

	/**
	 * Get the value of createdBy
	 * 
	 * @return the value of createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
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
	 * Set the value of checkUser
	 * 
	 * @param newVar
	 *            the new value of checkUser
	 */
	public void setCheckUser(User newVar) {
		checkUser = newVar;
	}

	/**
	 * Get the value of checkUser
	 * 
	 * @return the value of checkUser
	 */
	public User getCheckUser() {
		return checkUser;
	}

	/**
	 * Set the value of checkComment
	 * 
	 * @param newVar
	 *            the new value of checkComment
	 */
	public void setCheckComment(String newVar) {
		checkComment = newVar;
	}

	/**
	 * Get the value of checkComment
	 * 
	 * @return the value of checkComment
	 */
	public String getCheckComment() {
		return checkComment;
	}

	/**
	 * Set the value of checkDate
	 * 
	 * @param newVar
	 *            the new value of checkDate
	 */
	public void setCheckDate(Date newVar) {
		checkDate = newVar;
	}

	/**
	 * Get the value of checkDate
	 * 
	 * @return the value of checkDate
	 */
	public Date getCheckDate() {
		return checkDate;
	}
}