/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import java.io.IOException;
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
	
	public TemplateFiller(TaskDto task, 
			org.ktunaxa.referral.server.domain.Template domain) throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		Template titleTpl = cfg.getTemplate(domain.getTitle());
		Template messageTpl = cfg.getTemplate(domain.getStringContent());
		StringWriter baos = new StringWriter();
		Map<String, String> variables = task.getVariables();
		titleTpl.process(variables, baos);
		filledSubject = baos.toString();
		messageTpl.process(variables, baos);
		filledMessage = baos.toString();
	}

	public String getFilledSubject() {
		return filledSubject;
	}

	public String getFilledMessage() {
		return filledMessage;
	}
}
