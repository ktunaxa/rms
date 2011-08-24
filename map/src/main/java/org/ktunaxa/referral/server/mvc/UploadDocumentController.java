/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
