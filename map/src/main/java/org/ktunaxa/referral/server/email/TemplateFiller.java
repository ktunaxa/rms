/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.email;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Fills {@link Template} variables containing ${} placeholders with 
 * {@link TaskDto} variables using the FreeMarker package. <br /><br />
 * 
 * @author Emiel Ackermann
 *
 */

public class TemplateFiller {
	
	private final Logger log = LoggerFactory.getLogger(TemplateFiller.class);
	/**
	 * Fills the given String containing ${} placeholders with the given Map,
	 * as long as the keys of the map coincide with the placeholders.
	 * @param template
	 * @param variables
	 * @return
	 */
	@SuppressWarnings("finally")
	public String fillStringWithData(String template, Map<String, String> variables) {
		String filled = "";
		try {
			Configuration cfg = new Configuration();
			Template messageTpl = cfg.getTemplate(template);
			StringWriter baos = new StringWriter();
			messageTpl.process(variables, baos);
			filled = baos.toString();
		} catch (IOException e) {
			log.error("IOException occured while using FreeMarker.", e);
		} catch (TemplateException e) {
			log.error("Exception occured during FreeMarker template processing", e);
		} finally {
			return filled;
		}
	}
}
