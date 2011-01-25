package org.ktunaxa.referral.pojo;

/**
 * ...
 * 
 * @author Pieter De Graef
 */
public class Template {

	private long id;

	private String title;

	private String description;

	private String template;

	public Template() {
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
	 * Set the value of template
	 * 
	 * @param newVar
	 *            the new value of template
	 */
	public void setTemplate(String newVar) {
		template = newVar;
	}

	/**
	 * Get the value of template
	 * 
	 * @return the value of template
	 */
	public String getTemplate() {
		return template;
	}
}