package org.ktunaxa.referral.server.domain;

/**
 * A template from which documents can be created. These templates will be used in multiple processes (e-mails, reports,
 * ...)
 * 
 * @author Pieter De Graef
 */
public class Template {

	private long id;

	/** This document's title. */
	private String title;

	/** A short description to further clarify this document. */
	private String description;

	/** The XML contents of the template. */
	private String template;

	// ------------------------------------------------------------------------
	// Getters and setters:
	// ------------------------------------------------------------------------

	public Template() {
	};

	public Template(long id) {
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
	 * Get the XML contents of the template.
	 * 
	 * @return The XML contents of the template.
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * Set the XML contents of the template.
	 * 
	 * @param template
	 *            The new value.
	 */
	public void setTemplate(String template) {
		this.template = template;
	}
}