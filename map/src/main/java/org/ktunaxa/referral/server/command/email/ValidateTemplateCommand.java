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
import java.util.HashMap;
import java.util.Map;

import org.geomajas.command.Command;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.ktunaxa.referral.server.command.dto.ValidateTemplateRequest;
import org.ktunaxa.referral.server.command.dto.ValidateTemplateResponse;
import org.ktunaxa.referral.server.dto.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import freemarker.template.TemplateException;

/**
 * Performs a test run with a {@link TemplateFiller}.
 * 
 * @author Emiel Ackermann
 */
@Component
public class ValidateTemplateCommand implements Command<ValidateTemplateRequest, ValidateTemplateResponse> {

	private final Logger log = LoggerFactory.getLogger(ValidateTemplateCommand.class);

	public ValidateTemplateResponse getEmptyCommandResponse() {
		return new ValidateTemplateResponse();
	}

	public void execute(ValidateTemplateRequest request, ValidateTemplateResponse response) throws Exception {
		final TaskDto task = request.getTask();
		if (null == task) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "task");
		}
		final Map<String, String> attributes = request.getAttributes();
		if (null == attributes) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "attributes");
		}
		final String subject = request.getSubject();
		if (null == subject) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "subject");
		}
		final String body = request.getBody();
		if (null == body) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "body");
		}

		TemplateFiller filler = new TemplateFiller();
		Map<String, String> variables = new HashMap<String, String>();
		variables.putAll(task.getVariables());
		variables.putAll(attributes);
		try {	
			filler.fillStringWithData(subject, variables);
			filler.fillStringWithData(body, variables);
			response.setValid(true);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage(), e);
			response.setValid(false);
		} catch (TemplateException te) {
			log.debug(te.getMessage(), te);
			response.setValid(false);
			String[] split = te.getFTLInstructionStack().split("\\$\\{", 2);
			split = split[1].split("\\}", 2);
			response.setInvalidPlaceholder("${" + split[0] + "}");
		}
	}
}

