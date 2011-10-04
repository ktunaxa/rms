/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.ktunaxa.referral.server.dto.TaskDto;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Fills {@link Template} variables containing ${} placeholders with 
 * {@link TaskDto} variables using the FreeMarker package.
 * 
 * @author Emiel Ackermann
 *
 */

public class TemplateFiller {
	
	private String filledSubject;
	private String filledMessage;
	
	public TemplateFiller() {
	}
	
	public TemplateFiller(TaskDto task, 
			org.ktunaxa.referral.server.domain.Template domain) throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		Template titleTpl = new Template("SubjectTemplate", new StringReader(domain.getSubject()), cfg);
		Template messageTpl = new Template("ContentTemplate", new StringReader(domain.getStringContent()), cfg);
		Map<String, String> variables = task.getVariables();
		StringWriter titleWriter = new StringWriter();
		titleTpl.process(variables, titleWriter);
		filledSubject = titleWriter.toString();
		StringWriter messageWriter = new StringWriter();
		messageTpl.process(variables, messageWriter);
		filledMessage = messageWriter.toString();
	}

	public String getFilledSubject() {
		return filledSubject;
	}

	public String getFilledMessage() {
		return filledMessage;
	}
	
	/**
	 * Fills the given String containing ${} placeholders with the given Map,
	 * as long as the keys of the map coincide with the placeholders.
	 * @param template
	 * @param variables
	 * @return
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public String fillStringWithData(String template, Map<String, String> variables) 
													throws IOException, TemplateException {
		String filled = "";
		Configuration cfg = new Configuration();
		Template t = new Template(template, new StringReader(template), cfg);
		StringWriter baos = new StringWriter();
		t.process(variables, baos);
		filled = baos.toString();
		return filled;
	}
}
