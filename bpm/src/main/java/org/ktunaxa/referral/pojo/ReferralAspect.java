package org.ktunaxa.referral.pojo;

import java.util.Date;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class ReferralAspect {

	private long id;

	private Referral referral;

	private Aspect aspect;

	private boolean checked;

	private User checkUser;

	private String checkComment;

	private Date checkDate;

	public ReferralAspect() {
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

	/**
	 * Set the value of aspect
	 * 
	 * @param newVar
	 *            the new value of aspect
	 */
	public void setAspect(Aspect newVar) {
		aspect = newVar;
	}

	/**
	 * Get the value of aspect
	 * 
	 * @return the value of aspect
	 */
	public Aspect getAspect() {
		return aspect;
	}

	/**
	 * Set the value of checked
	 * 
	 * @param newVar
	 *            the new value of checked
	 */
	public void setChecked(boolean newVar) {
		checked = newVar;
	}

	/**
	 * Get the value of checked
	 * 
	 * @return the value of checked
	 */
	public boolean getChecked() {
		return checked;
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