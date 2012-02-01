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
package org.ktunaxa.referral.server.mvc;

import org.apache.chemistry.opencmis.client.api.Document;
import org.ktunaxa.referral.server.service.CmisService;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for uploading documents to the CMIS service.
 * 
 * @author Pieter De Graef
 * @author Jan De Moerloose
 */
@Controller("/upload/referral/document/**")
public class UploadDocumentController {

	@Autowired
	private CmisService cmisService;

	private final Logger log = LoggerFactory.getLogger(UploadDocumentController.class);

	@RequestMapping(value = "/upload/referral/document", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam(KtunaxaConstant.FORM_ID) String formId,
			@RequestParam(KtunaxaConstant.FORM_REFERRAL) String referralId,
			@RequestParam("file") MultipartFile file, Model model) {

		UploadResponse response = new UploadResponse();
		response.addObject(KtunaxaConstant.FORM_ID, formId);
		try {
			String year = "20" + referralId.substring(8, 10);
			String originalFilename = file.getOriginalFilename();
			Document document = cmisService.create(originalFilename, file.getContentType(),
					file.getInputStream(), year, referralId);
			response.addObject(KtunaxaConstant.FORM_DOCUMENT_TITLE, originalFilename);
			response.addObject(KtunaxaConstant.FORM_DOCUMENT_ID, document.getId());
			response.addObject(KtunaxaConstant.FORM_DOCUMENT_DISPLAY_URL, cmisService.getDisplayUrl(document));
			response.addObject(KtunaxaConstant.FORM_DOCUMENT_DOWNLOAD_URL, cmisService.getDownloadUrl(document));
			model.addAttribute(UploadView.RESPONSE, response);
		} catch (Exception e) {
			log.error("Could not upload document", e);
			response.setException(e);
		}
		model.addAttribute(UploadView.RESPONSE, response);
		return UploadView.NAME;
	}

}
