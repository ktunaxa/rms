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

package org.ktunaxa.referral.server.command;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.geomajas.command.Command;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.SuccessCommandResponse;
import org.geomajas.geometry.Crs;
import org.geomajas.global.ExceptionCode;
import org.geomajas.global.GeomajasException;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.InternalFeature;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.DateAttribute;
import org.geomajas.layer.feature.attribute.LongAttribute;
import org.geomajas.layer.feature.attribute.OneToManyAttribute;
import org.geomajas.layer.feature.attribute.PrimitiveAttribute;
import org.geomajas.security.SecurityContext;
import org.geomajas.service.DispatcherUrlService;
import org.geomajas.service.FilterService;
import org.geomajas.service.GeoService;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.CmisUtil;
import org.ktunaxa.referral.client.form.FinalReportClickHandler;
import org.ktunaxa.referral.client.referral.ReferralUtil;
import org.ktunaxa.referral.server.command.dto.FinishFinalReportTaskRequest;
import org.ktunaxa.referral.server.command.dto.FinishFinalReportTaskResponse;
import org.ktunaxa.referral.server.command.dto.FinishTaskRequest;
import org.ktunaxa.referral.server.command.dto.SendEmailRequest;
import org.ktunaxa.referral.server.service.CmisService;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command to finish the final report task. This will create the report, save it as attachment in the CMS and send the
 * final report e-mail to the proponent, including the final report and other documents which need to be included.
 *
 * @author Joachim Van der Auwera
 */
@Component
@Transactional(rollbackFor = { Exception.class })
public class FinishFinalReportTaskCommand
		implements Command<FinishFinalReportTaskRequest, FinishFinalReportTaskResponse> {

	private final Logger log = LoggerFactory.getLogger(FinishFinalReportTaskCommand.class);

	public static final String MIME_PDF = "application/pdf";

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private SecurityContext securityContext;

	@Autowired
	private VectorLayerService vectorLayerService;

	@Autowired
	private DispatcherUrlService dispatcherUrlService;

	@Autowired
	private CmisService cmisService;

	@Autowired
	private FilterService filterService;

	@Autowired
	private GeoService geoService;

	public FinishFinalReportTaskResponse getEmptyCommandResponse() {
		return new FinishFinalReportTaskResponse();
	}

	/**
	 * Get the URL of the server (being the address here, behind the firewall/router).
	 *
	 * @return address of application
	 */
	private String getServerDispatchUrl() {
		return dispatcherUrlService.getLocalDispatcherUrl();
	}


	public void execute(FinishFinalReportTaskRequest request, FinishFinalReportTaskResponse response) throws Exception {
		String referralId = request.getReferralId();
		if (null == referralId) {
			throw new GeomajasException(ExceptionCode.PARAMETER_MISSING, "referralId");
		}

		String token = securityContext.getToken();
		OneToManyAttribute newDocuments = null;
		if (!request.isSkipReportUpload()) {

			// build report and save as in referral
			String reportUrl = getServerDispatchUrl() + "reporting/f/" + KtunaxaConstant.LAYER_REFERRAL_SERVER_ID + "/"
					+ FinalReportClickHandler.REPORT_NAME + "." + FinalReportClickHandler.FORMAT + "?filter="
					+ URLEncoder.encode(ReferralUtil.createFilter(referralId), "UTF-8") + "&userToken="
					+ securityContext.getToken();
			log.debug("Report URL {}", reportUrl);

			// download report and upload as document in Alfresco
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(reportUrl);
			HttpResponse httpResponse;
			try {
				httpResponse = httpClient.execute(httpGet);
			} catch (Exception e1) {
				log.error("Could not create report.", e1);
				throw new KtunaxaException(KtunaxaException.CODE_REPORT_ERROR, "Could not create report.");			
			}
			SimpleDateFormat timeFormat = new SimpleDateFormat(KtunaxaBpmConstant.TIMESTAMP_FORMAT);
			String filename = referralId.replace('/', '_') + "-finalReport-" + timeFormat.format(new Date()) + ".pdf";
			Document cmisDocument;
			try {
				cmisDocument = cmisService.saveOrUpdate(filename, MIME_PDF, httpResponse.getEntity().getContent(),
						-1, ReferralUtil.getYear(referralId), referralId);
			} catch (Exception e) {
				log.error("Could not save report in Alfresco.", e);
				throw new KtunaxaException(KtunaxaException.CODE_ALFRESCO_ERROR, "Could not save report in Alfresco.");
			}
			log.debug("Uploaded in Alfresco");
			if (!httpGet.isAborted()) {
				httpGet.abort(); // assure low level resources are released
			}
			log.debug("Aborted input for safety");

			// add document in referral
			Crs crs = geoService.getCrs2(KtunaxaConstant.LAYER_CRS);
			List<InternalFeature> features = vectorLayerService.getFeatures(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID,
					crs,
					filterService.parseFilter(ReferralUtil.createFilter(referralId)), null,
					VectorLayerService.FEATURE_INCLUDE_ATTRIBUTES);
			InternalFeature orgReferral = features.get(0);
			log.debug("Got referral {}", referralId);
			InternalFeature referral = orgReferral.clone();
			Map<String, Attribute> attributes = referral.getAttributes();
			attributes.put(KtunaxaConstant.ATTRIBUTE_RESPONSE_DATE, new DateAttribute(new Date()));
			List<InternalFeature> newFeatures = new ArrayList<InternalFeature>();
			newFeatures.add(referral);
			OneToManyAttribute orgDocuments = (OneToManyAttribute) attributes.get(KtunaxaConstant.ATTRIBUTE_DOCUMENTS);
			List<AssociationValue> documents = new ArrayList<AssociationValue>(orgDocuments.getValue());
			AssociationValue finalReportDocument = new AssociationValue();
			finalReportDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_ADDED_BY,
					securityContext.getUserId());
			finalReportDocument.setDateAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_ADDITION_DATE, new Date());
			finalReportDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DESCRIPTION, "final report");
			finalReportDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DISPLAY_URL,
					cmisService.getDisplayUrl(cmisDocument));
			finalReportDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL,
					cmisService.getDownloadUrl(cmisDocument));
			finalReportDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_ID, filename);
			finalReportDocument.setStringAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TITLE, filename);
			finalReportDocument.setBooleanAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_INCLUDE_IN_REPORT, true);
			finalReportDocument.setBooleanAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_CONFIDENTIAL, false);
			finalReportDocument.setManyToOneAttribute(KtunaxaConstant.ATTRIBUTE_DOCUMENT_TYPE,
					new AssociationValue(new LongAttribute(1L), // official response
							new HashMap<String, PrimitiveAttribute<?>>()));
			documents.add(finalReportDocument);
			newDocuments = new OneToManyAttribute(documents);
			attributes.put(KtunaxaConstant.ATTRIBUTE_DOCUMENTS, newDocuments);
			log.debug("Going to add document in referral");
			vectorLayerService.saveOrUpdate(KtunaxaConstant.LAYER_REFERRAL_SERVER_ID, crs, features, newFeatures);
			log.debug("Document added in referral");
		}

		// send e-mail with final report
		if (request.isSendMail()) {
			if(!request.isSkipReportUpload()) {
				List<String> attachments = new ArrayList<String>();
				for (AssociationValue doc : newDocuments.getValue()) {
					log.debug("include " + doc.getAttributeValue(KtunaxaConstant.ATTRIBUTE_DOCUMENT_INCLUDE_IN_REPORT));
					if ((Boolean) doc.getAttributeValue(KtunaxaConstant.ATTRIBUTE_DOCUMENT_INCLUDE_IN_REPORT)) {
						String url = CmisUtil.addGuestAccess(
								(String) doc.getAttributeValue(KtunaxaConstant.ATTRIBUTE_DOCUMENT_DOWNLOAD_URL));
						log.debug("Add attachment {}", url);
						attachments.add(url);
					}
				}
				log.debug("e-mail attachments {}", attachments);
				request.setAttachmentUrls(attachments);
			}
			log.debug("Going to send final report e-mail.");
			CommandResponse emailResponse = commandDispatcher.execute(SendEmailRequest.COMMAND, request, token, null);
			if (emailResponse.isError() || !(emailResponse instanceof SuccessCommandResponse) ||
					!((SuccessCommandResponse) emailResponse).isSuccess()) {
				response.getErrors().addAll(emailResponse.getErrors());
				response.getErrorMessages().addAll(emailResponse.getErrorMessages());
				response.getExceptions().addAll(emailResponse.getExceptions());
				throw new KtunaxaException(KtunaxaException.CODE_MAIL_ERROR, "Could not send e-mail.");
			}
			log.debug("Final report e-mail sent.");
		}

		// finish task
		FinishTaskRequest finishTaskRequest = new FinishTaskRequest();
		finishTaskRequest.setTaskId(request.getTaskId());
		finishTaskRequest.setVariables(request.getVariables());
		CommandResponse finishResponse =
				commandDispatcher.execute(FinishTaskRequest.COMMAND, finishTaskRequest, token, null );
		if (finishResponse.isError()) {
			response.getErrors().addAll(finishResponse.getErrors());
			response.getErrorMessages().addAll(finishResponse.getErrorMessages());
			response.getExceptions().addAll(finishResponse.getExceptions());
			throw new GeomajasException(ExceptionCode.UNEXPECTED_PROBLEM, "Could not finish task.");
		}

		response.setSuccess(true);
	}
}
