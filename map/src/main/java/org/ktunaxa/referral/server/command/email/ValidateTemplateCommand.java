/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.command.email;

import java.io.IOException;
import java.util.Map;

import org.geomajas.command.Command;
import org.ktunaxa.referral.server.command.dto.ValidateTemplateRequest;
import org.ktunaxa.referral.server.command.dto.ValidateTemplateResponse;
import org.springframework.stereotype.Component;

import freemarker.template.TemplateException;

/**
 * Performs a test run with a {@link TemplateFiller}.
 * 
 * @author Emiel Ackermann
 */
@Component
public class ValidateTemplateCommand implements Command<ValidateTemplateRequest, ValidateTemplateResponse> {

	public ValidateTemplateResponse getEmptyCommandResponse() {
		return new ValidateTemplateResponse();
	}

	public void execute(ValidateTemplateRequest request, ValidateTemplateResponse response) throws Exception {
		TemplateFiller filler = new TemplateFiller();
		Map<String, String> variables = request.getTask().getVariables();
		try {	
			filler.fillStringWithData(request.getSubject(), variables);
			filler.fillStringWithData(request.getBody(), variables);
			response.setValid(true);
		} catch (IOException e) {
			response.setValid(false);
		} catch (TemplateException te) {
			response.setValid(false);
			String[] split = te.getFTLInstructionStack().split("\\$\\{", 2);
			split = split[1].split("\\}", 2);
			response.setCorruptPlaceholder("${" + split[0] + "}");
		}
	}
}

