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
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * A template from which documents can be created. These templates will be used in multiple processes
 * (e-mails, reports,...)
 * 
 * @author Pieter De Graef
 */
@Entity
@Table(name = "template")
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/** This document's title. */
	@Column(nullable = false, name = "title")
	private String title;
	
	/** The subject of an email. */
	@Column(nullable = true, name = "subject")
	private String subject;

	/** A short description to further clarify this document. */
	@Column(nullable = false, name = "description")
	private String description;

	/** The sender for the email. */
	@Column(nullable = false, name = "mail_sender")
	private String mailSender;

	/** The XML contents of the template. */
	@Column(nullable = true, name = "content")
	private byte[] content;

	/** The XML contents of the template. */
	@Column(nullable = true, name = "string_content")
	@Lob
	private String stringContent;

	/** The mime-type for the actual template. */
	@Column(nullable = false, name = "mime_type")
	private String mimeType;

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	public Template() {
	}

	public Template(long id) {
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
	 * Get this document's title.
	 * 
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
	 * Get the sender e-mail address.
	 *
	 * @return sender
	 */
	public String getMailSender() {
		return mailSender;
	}

	/**
	 * Set the sender e-mail address.
	 *
	 * @param mailSender sender
	 */
	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Return a short description to further clarify this document.
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
	 * Get the binary contents of the template.
	 * <p/>
	 * Whether string or binary content should be used depends on the mime type of the template.
	 *
	 * @return The binary contents of the template.
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Set the binary contents of the template.
	 * <p/>
	 * Whether string or binary content should be used depends on the mime type of the template.
	 *
	 * @param content
	 *            The new value.
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * Get the string content of the template.
	 * <p/>
	 * Whether string or binary content should be used depends on the mime type of the template.
	 *
	 * @return string content
	 */
	public String getStringContent() {
		return stringContent;
	}

	/**
	 * Set the string content of the template.
	 * <p/>
	 * Whether string or binary content should be used depends on the mime type of the template.
	 *
	 * @param stringContent string content
	 */
	public void setStringContent(String stringContent) {
		this.stringContent = stringContent;
	}

	/**
	 * Get the mime-type for the actual template.
	 * 
	 * @return The mime-type for the actual template.
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Set the mime-type for the actual template.
	 * 
	 * @param mimeType
	 *            The new mime-type.
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}
}