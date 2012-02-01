/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.server.command.email;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
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
 */
public class TemplateFiller {
	
	private String filledSubject;
	private String filledMessage;

	/**
	 * No-arguments constructor, used to verify a template when the task is unknown.
	 */
	public TemplateFiller() {
	}

	/**
	 * Fill a template for a specific task and e-mail templates.
	 *
	 * @param task task
	 * @param attributes referral attributes
	 * @param domain template DTO
	 * @throws IOException IO Exception
	 * @throws TemplateException problem filling template
	 */
	public TemplateFiller(TaskDto task, Map<String, String> attributes,
			org.ktunaxa.referral.server.domain.Template domain) throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		Template titleTpl = new Template("SubjectTemplate", new StringReader(domain.getSubject()), cfg);
		Template messageTpl = new Template("ContentTemplate", new StringReader(domain.getStringContent()), cfg);
		Map<String, String> variables = new HashMap<String, String>();
		variables.putAll(task.getVariables());
		variables.putAll(attributes);
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
	 *
	 * @param template name of template to fill
	 * @param variables variables map
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
